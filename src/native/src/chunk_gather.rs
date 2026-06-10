use jni::JNIEnv;
use jni::objects::{JClass, JBooleanArray, JIntArray};
use jni::sys::{jboolean, jbooleanArray, jint, jintArray};

// -- Flood-fill reachability (BFS) ---------------------------------------------
//
// Java's ArrayDeque<int[]> allocates a new int[3] per visited cell — tens of
// thousands of small heap objects per gather. This replaces the entire BFS with
// a flat u8 array and an integer queue (indices only, zero allocation).
//
// solid: flat boolean array, row-major [x * sizeY * sizeZ + y * sizeZ + z]
// Returns: flat boolean array of reachable cells, same layout as solid.
//
// Java: native boolean[] floodFill(boolean[] solid, int sizeX, int sizeY, int sizeZ);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_boti_ChunkGatheringThread_floodFill(
    mut env: JNIEnv,
    _class: JClass,
    solid: JBooleanArray,
    size_x: jint,
    size_y: jint,
    size_z: jint,
) -> jbooleanArray {
    let sx = size_x as usize;
    let sy = size_y as usize;
    let sz = size_z as usize;
    let total = sx * sy * sz;

    let mut solid_buf = vec![0u8; total];
    if env.get_boolean_array_region(&solid, 0, &mut solid_buf).is_err() {
        return std::ptr::null_mut();
    }

    let mut reachable = vec![0u8; total];
    // Queue stores flat indices — no allocation per cell
    let mut queue: Vec<usize> = Vec::with_capacity(total / 4);

    let idx = |x: usize, y: usize, z: usize| x * sy * sz + y * sz + z;

    macro_rules! try_enqueue {
        ($x:expr, $y:expr, $z:expr) => {{
            let (x, y, z) = ($x, $y, $z);
            if x < sx && y < sy && z < sz {
                let i = idx(x, y, z);
                if solid_buf[i] == 0 && reachable[i] == 0 {
                    reachable[i] = 1;
                    queue.push(i);
                }
            }
        }};
    }

    // Seed from all 6 faces
    for y in 0..sy {
        for x in 0..sx {
            try_enqueue!(x, y, 0);
            try_enqueue!(x, y, sz - 1);
        }
        for z in 0..sz {
            try_enqueue!(0, y, z);
            try_enqueue!(sx - 1, y, z);
        }
    }
    for x in 0..sx {
        for z in 0..sz {
            try_enqueue!(x, 0, z);
            try_enqueue!(x, sy - 1, z);
        }
    }

    // BFS — indices only, no allocations
    let mut head = 0usize;
    while head < queue.len() {
        let i = queue[head];
        head += 1;

        let x = i / (sy * sz);
        let rem = i % (sy * sz);
        let y = rem / sz;
        let z = rem % sz;

        if x + 1 < sx { try_enqueue!(x + 1, y, z); }
        if x > 0      { try_enqueue!(x - 1, y, z); }
        if y + 1 < sy { try_enqueue!(x, y + 1, z); }
        if y > 0      { try_enqueue!(x, y - 1, z); }
        if z + 1 < sz { try_enqueue!(x, y, z + 1); }
        if z > 0      { try_enqueue!(x, y, z - 1); }
    }

    match env.new_boolean_array(total as i32) {
        Ok(arr) => {
            let _ = env.set_boolean_array_region(&arr, 0, &reachable);
            arr.into_raw()
        }
        Err(_) => std::ptr::null_mut(),
    }
}

// -- Exposed face detection + behind-portal culling ----------------------------
//
// Combines Phase 3's per-block logic into a single native call.
// For each solid block, checks if any neighbour is reachable (exposed),
// then applies behind-portal culling based on facing direction.
//
// Returns a flat int array of local indices [lx * sizeY * sizeZ + ly * sizeZ + lz]
// for blocks that should be emitted. Java iterates this and builds BotiBlockContainers.
//
// facing: 0=+Z, 1=-Z, 2=+X, 3=-X  (matches facingPosZ/facingNegZ/facingPosX/else)
// originX/Y/Z: the local coordinates of targetPos within the local array
//   (= targetPos - worldMin, used for behind-portal culling)
//
// Java: native int[] findExposedBlocks(boolean[] solid, boolean[] reachable,
//           int sizeX, int sizeY, int sizeZ,
//           int originX, int originY, int originZ, int facing);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_boti_ChunkGatheringThread_findExposedBlocks(
    mut env: JNIEnv,
    _class: JClass,
    solid: JBooleanArray,
    reachable: JBooleanArray,
    size_x: jint,
    size_y: jint,
    size_z: jint,
    origin_x: jint,
    origin_y: jint,
    origin_z: jint,
    facing: jint,
) -> jintArray {
    let sx = size_x as usize;
    let sy = size_y as usize;
    let sz = size_z as usize;
    let total = sx * sy * sz;

    let mut solid_buf = vec![0u8; total];
    let mut reach_buf = vec![0u8; total];

    if env.get_boolean_array_region(&solid, 0, &mut solid_buf).is_err() {
        return std::ptr::null_mut();
    }
    if env.get_boolean_array_region(&reachable, 0, &mut reach_buf).is_err() {
        return std::ptr::null_mut();
    }

    let ox = origin_x as i32;
    let oz = origin_z as i32;

    let idx = |x: usize, y: usize, z: usize| x * sy * sz + y * sz + z;

    let mut exposed_indices: Vec<i32> = Vec::with_capacity(total / 8);

    for lx in 0..sx {
        for ly in 0..sy {
            for lz in 0..sz {
                if solid_buf[idx(lx, ly, lz)] == 0 {
                    continue;
                }

                // Check if any face neighbour is reachable
                let mut exposed = false;
                'faces: for (ddx, ddy, ddz) in [
                    (1i32,0i32,0i32),(-1,0,0),(0,1,0),(0,-1,0),(0,0,1),(0,0,-1)
                ] {
                    let nx = lx as i32 + ddx;
                    let ny = ly as i32 + ddy;
                    let nz = lz as i32 + ddz;
                    if nx < 0 || nx >= sx as i32 || ny < 0 || ny >= sy as i32 || nz < 0 || nz >= sz as i32 {
                        // Out of bounds: treat as not exposed (matches Java logic)
                        exposed = false;
                        break 'faces;
                    }
                    if reach_buf[idx(nx as usize, ny as usize, nz as usize)] != 0 {
                        exposed = true;
                        break 'faces;
                    }
                }
                if !exposed {
                    continue;
                }

                // Behind-portal culling
                let rel_x = lx as i32 - ox;
                let rel_z = lz as i32 - oz;
                let is_behind = match facing {
                    0 => rel_z >= 0, // facingPosZ
                    1 => rel_z <= 0, // facingNegZ
                    2 => rel_x >= 0, // facingPosX
                    _ => rel_x <= 0, // facingNegX
                };
                if is_behind {
                    continue;
                }

                exposed_indices.push(idx(lx, ly, lz) as i32);
            }
        }
    }

    match env.new_int_array(exposed_indices.len() as i32) {
        Ok(arr) => {
            let _ = env.set_int_array_region(&arr, 0, &exposed_indices);
            arr.into_raw()
        }
        Err(_) => std::ptr::null_mut(),
    }
}

// -- Flat index → xyz ----------------------------------------------------------
// Convenience — Java can call this to unpack a flat index from findExposedBlocks
// back to lx,ly,lz. Returns int[3].
// Java: native int[] unpackIndex(int flatIndex, int sizeY, int sizeZ);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_boti_ChunkGatheringThread_unpackIndex(
    mut env: JNIEnv,
    _class: JClass,
    flat: jint,
    size_y: jint,
    size_z: jint,
) -> jintArray {
    let sy = size_y as usize;
    let sz = size_z as usize;
    let i = flat as usize;
    let x = (i / (sy * sz)) as i32;
    let rem = i % (sy * sz);
    let y = (rem / sz) as i32;
    let z = (rem % sz) as i32;

    match env.new_int_array(3) {
        Ok(arr) => {
            let _ = env.set_int_array_region(&arr, 0, &[x, y, z]);
            arr.into_raw()
        }
        Err(_) => std::ptr::null_mut(),
    }
}