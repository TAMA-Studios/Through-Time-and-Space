use jni::JNIEnv;
use jni::objects::{JClass, JFloatArray};
use jni::sys::{jfloat, jfloatArray, jint, jintArray};

// Helper: copy a Java float[] into a Rust Vec<f32>
fn get_floats(env: &mut JNIEnv, arr: &JFloatArray) -> Option<Vec<f32>> {
    let len = env.get_array_length(arr).ok()? as usize;
    let mut buf = vec![0f32; len];
    env.get_float_array_region(arr, 0, &mut buf).ok()?;
    Some(buf)
}

// Helper: copy a Rust &[f32] into a new Java float[]
fn to_jfloat_array(env: &mut JNIEnv, data: &[f32]) -> jfloatArray {
    match env.new_float_array(data.len() as i32) {
        Ok(arr) => {
            let _ = env.set_float_array_region(&arr, 0, data);
            arr.into_raw()
        }
        Err(_) => std::ptr::null_mut(),
    }
}

// Helper: copy a Rust &[i32] into a new Java int[]
fn to_jint_array(env: &mut JNIEnv, data: &[i32]) -> jintArray {
    match env.new_int_array(data.len() as i32) {
        Ok(arr) => {
            let _ = env.set_int_array_region(&arr, 0, data);
            arr.into_raw()
        }
        Err(_) => std::ptr::null_mut(),
    }
}

// ── Frustum Culling ───────────────────────────────────────────────────────────
// aabbs: flat [minX,minY,minZ,maxX,maxY,maxZ, ...]
// frustumPlanes: 6 planes × 4 floats [nx,ny,nz,d]
// Returns indices of visible AABBs.
// Java: native int[] frustumCull(float[] aabbs, float[] frustumPlanes);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_RenderUtils_frustumCull(
    mut env: JNIEnv,
    _class: JClass,
    aabbs: JFloatArray,
    frustum_planes: JFloatArray,
) -> jintArray {
    let aabb_data  = match get_floats(&mut env, &aabbs)         { Some(v) => v, None => return std::ptr::null_mut() };
    let plane_data = match get_floats(&mut env, &frustum_planes) { Some(v) => v, None => return std::ptr::null_mut() };

    let count = aabb_data.len() / 6;
    let mut visible: Vec<i32> = Vec::with_capacity(count);

    for i in 0..count {
        let b = i * 6;
        let min_x = aabb_data[b];     let min_y = aabb_data[b+1]; let min_z = aabb_data[b+2];
        let max_x = aabb_data[b+3];   let max_y = aabb_data[b+4]; let max_z = aabb_data[b+5];

        let mut inside = true;
        for p in 0..6 {
            let pb = p * 4;
            let nx = plane_data[pb]; let ny = plane_data[pb+1];
            let nz = plane_data[pb+2]; let d = plane_data[pb+3];

            let px = if nx >= 0.0 { max_x } else { min_x };
            let py = if ny >= 0.0 { max_y } else { min_y };
            let pz = if nz >= 0.0 { max_z } else { min_z };

            if nx*px + ny*py + nz*pz + d < 0.0 {
                inside = false;
                break;
            }
        }

        if inside { visible.push(i as i32); }
    }

    to_jint_array(&mut env, &visible)
}

// ── Batch AABB Vertex Generation ──────────────────────────────────────────────
// Output per vertex: [x, y, z, nx, ny, nz],  24 vertices per AABB = 144 floats per box.
// Java unpacks and feeds directly to VertexConsumer.
// Java: native float[] buildAABBVertices(float[] aabbs);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_RenderUtils_buildAABBVertices(
    mut env: JNIEnv,
    _class: JClass,
    aabbs: JFloatArray,
) -> jfloatArray {
    let data = match get_floats(&mut env, &aabbs) { Some(v) => v, None => return std::ptr::null_mut() };

    let count = data.len() / 6;
    let mut out: Vec<f32> = Vec::with_capacity(count * 144);

    for i in 0..count {
        let b = i * 6;
        let x0 = data[b];   let y0 = data[b+1]; let z0 = data[b+2];
        let x1 = data[b+3]; let y1 = data[b+4]; let z1 = data[b+5];

        // Bottom face
        push_line(&mut out, x0,y0,z0, x1,y0,z0);
        push_line(&mut out, x1,y0,z0, x1,y0,z1);
        push_line(&mut out, x1,y0,z1, x0,y0,z1);
        push_line(&mut out, x0,y0,z1, x0,y0,z0);
        // Top face
        push_line(&mut out, x0,y1,z0, x1,y1,z0);
        push_line(&mut out, x1,y1,z0, x1,y1,z1);
        push_line(&mut out, x1,y1,z1, x0,y1,z1);
        push_line(&mut out, x0,y1,z1, x0,y1,z0);
        // Vertical edges
        push_line(&mut out, x0,y0,z0, x0,y1,z0);
        push_line(&mut out, x1,y0,z0, x1,y1,z0);
        push_line(&mut out, x1,y0,z1, x1,y1,z1);
        push_line(&mut out, x0,y0,z1, x0,y1,z1);
    }

    to_jfloat_array(&mut env, &out)
}

// ── Distance Sorting ──────────────────────────────────────────────────────────
// Sorts N AABBs by distance from a point. reverse=1 → farthest first (transparency).
// Java: native int[] sortByDistance(float[] aabbs, float cx, float cy, float cz, int reverse);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_RenderUtils_sortByDistance(
    mut env: JNIEnv,
    _class: JClass,
    aabbs: JFloatArray,
    cx: jfloat, cy: jfloat, cz: jfloat,
    reverse: jint,
) -> jintArray {
    let data = match get_floats(&mut env, &aabbs) { Some(v) => v, None => return std::ptr::null_mut() };

    let count = data.len() / 6;
    let mut indexed: Vec<(i32, f32)> = (0..count as i32).map(|i| {
        let b = i as usize * 6;
        let mx = (data[b] + data[b+3]) * 0.5 - cx;
        let my = (data[b+1] + data[b+4]) * 0.5 - cy;
        let mz = (data[b+2] + data[b+5]) * 0.5 - cz;
        (i, mx*mx + my*my + mz*mz)
    }).collect();

    if reverse != 0 {
        indexed.sort_unstable_by(|a, b| b.1.partial_cmp(&a.1).unwrap_or(std::cmp::Ordering::Equal));
    } else {
        indexed.sort_unstable_by(|a, b| a.1.partial_cmp(&b.1).unwrap_or(std::cmp::Ordering::Equal));
    }

    let indices: Vec<i32> = indexed.into_iter().map(|(i, _)| i).collect();
    to_jint_array(&mut env, &indices)
}

// ── Color Packing ─────────────────────────────────────────────────────────────
// Java: native int packARGB(float a, float r, float g, float b);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_RenderUtils_packARGB(
    _env: JNIEnv,
    _class: JClass,
    a: jfloat, r: jfloat, g: jfloat, b: jfloat,
) -> jint {
    let ai = (a.clamp(0.0,1.0) * 255.0) as u32;
    let ri = (r.clamp(0.0,1.0) * 255.0) as u32;
    let gi = (g.clamp(0.0,1.0) * 255.0) as u32;
    let bi = (b.clamp(0.0,1.0) * 255.0) as u32;
    ((ai << 24) | (ri << 16) | (gi << 8) | bi) as i32
}

// ── Batch Point Transform ─────────────────────────────────────────────────────
// Transforms N xyz points by a 4×4 column-major matrix (same as JOML/GL).
// Java: native float[] transformPoints(float[] points, float[] matrix);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_RenderUtils_transformPoints(
    mut env: JNIEnv,
    _class: JClass,
    points: JFloatArray,
    matrix: JFloatArray,
) -> jfloatArray {
    let pts = match get_floats(&mut env, &points) { Some(v) => v, None => return std::ptr::null_mut() };
    let mx  = match get_floats(&mut env, &matrix)  { Some(v) => v, None => return std::ptr::null_mut() };
    if mx.len() < 16 { return std::ptr::null_mut(); }

    let count = pts.len() / 3;
    let mut out = Vec::with_capacity(pts.len());

    for i in 0..count {
        let x = pts[i*3]; let y = pts[i*3+1]; let z = pts[i*3+2];
        out.push(mx[0]*x + mx[4]*y + mx[8]*z  + mx[12]);
        out.push(mx[1]*x + mx[5]*y + mx[9]*z  + mx[13]);
        out.push(mx[2]*x + mx[6]*y + mx[10]*z + mx[14]);
    }

    to_jfloat_array(&mut env, &out)
}

// ── Sphere Vertex Generation ──────────────────────────────────────────────────
// Returns flat [x,y,z, x,y,z, ...] line segment pairs for a wireframe sphere.
// Java: native float[] buildSphereVertices(float cx, float cy, float cz, float radius, int stacks, int slices);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_RenderUtils_buildSphereVertices(
    mut env: JNIEnv,
    _class: JClass,
    cx: jfloat, cy: jfloat, cz: jfloat,
    radius: jfloat,
    stacks: jint, slices: jint,
) -> jfloatArray {
    let stacks = stacks.max(2) as usize;
    let slices = slices.max(3) as usize;
    let mut out: Vec<f32> = Vec::with_capacity(stacks * slices * 12);
    use std::f32::consts::PI;

    for i in 0..=stacks {
        let phi = PI * i as f32 / stacks as f32;
        let y = cy + radius * phi.cos();
        let r = radius * phi.sin();
        for j in 0..slices {
            let t0 = 2.0 * PI * j as f32 / slices as f32;
            let t1 = 2.0 * PI * (j+1) as f32 / slices as f32;
            out.extend_from_slice(&[cx+r*t0.cos(), y, cz+r*t0.sin(),
                                    cx+r*t1.cos(), y, cz+r*t1.sin()]);
        }
    }
    for j in 0..slices {
        let t = 2.0 * PI * j as f32 / slices as f32;
        let (ct, st) = (t.cos(), t.sin());
        for i in 0..stacks {
            let p0 = PI * i as f32 / stacks as f32;
            let p1 = PI * (i+1) as f32 / stacks as f32;
            out.extend_from_slice(&[cx+radius*p0.sin()*ct, cy+radius*p0.cos(), cz+radius*p0.sin()*st,
                                    cx+radius*p1.sin()*ct, cy+radius*p1.cos(), cz+radius*p1.sin()*st]);
        }
    }

    to_jfloat_array(&mut env, &out)
}

// ── Line Normal Batch ─────────────────────────────────────────────────────────
// Given line endpoint pairs [x0,y0,z0,x1,y1,z1, ...] returns [nx,ny,nz, ...].
// Java: native float[] computeLineNormals(float[] lines);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_RenderUtils_computeLineNormals(
    mut env: JNIEnv,
    _class: JClass,
    lines: JFloatArray,
) -> jfloatArray {
    let data = match get_floats(&mut env, &lines) { Some(v) => v, None => return std::ptr::null_mut() };

    let count = data.len() / 6;
    let mut out = Vec::with_capacity(count * 3);

    for i in 0..count {
        let b = i * 6;
        let dx = data[b+3]-data[b]; let dy = data[b+4]-data[b+1]; let dz = data[b+5]-data[b+2];
        let len = (dx*dx + dy*dy + dz*dz).sqrt();
        if len > 0.0 { out.extend_from_slice(&[dx/len, dy/len, dz/len]); }
        else         { out.extend_from_slice(&[0.0, 1.0, 0.0]); }
    }

    to_jfloat_array(&mut env, &out)
}

// ── AABB Inflate ──────────────────────────────────────────────────────────────
// Java: native float[] inflateAABBs(float[] aabbs, float amount);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_RenderUtils_inflateAABBs(
    mut env: JNIEnv,
    _class: JClass,
    aabbs: JFloatArray,
    amount: jfloat,
) -> jfloatArray {
    let data = match get_floats(&mut env, &aabbs) { Some(v) => v, None => return std::ptr::null_mut() };

    let mut out: Vec<f32> = Vec::with_capacity(data.len());
    for i in 0..data.len()/6 {
        let b = i * 6;
        out.extend_from_slice(&[
            data[b]-amount,   data[b+1]-amount, data[b+2]-amount,
            data[b+3]+amount, data[b+4]+amount, data[b+5]+amount,
        ]);
    }

    to_jfloat_array(&mut env, &out)
}

// ── internal ──────────────────────────────────────────────────────────────────

#[inline(always)]
fn push_line(out: &mut Vec<f32>, x0:f32,y0:f32,z0:f32, x1:f32,y1:f32,z1:f32) {
    let dx = x1-x0; let dy = y1-y0; let dz = z1-z0;
    let len = (dx*dx + dy*dy + dz*dz).sqrt();
    let (nx,ny,nz) = if len > 0.0 { (dx/len, dy/len, dz/len) } else { (0.0,1.0,0.0) };
    out.extend_from_slice(&[x0,y0,z0,nx,ny,nz, x1,y1,z1,nx,ny,nz]);
}