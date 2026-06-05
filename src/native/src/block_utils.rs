use jni::JNIEnv;
use jni::objects::JClass;
use jni::sys::{jint, jfloat, jlong, jdouble, jboolean, jstring};
use jni::objects::JString;

// ── GetPackedLight ────────────────────────────────────────────────────────────
// Pass the raw block/sky brightness values from Java — we do the bit math.
// Java: native int getPackedLight(int blockLight, int skyLight);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_world_BlockUtilsNative_getPackedLight(
    _env: JNIEnv, _class: JClass,
    block_light: jint, sky_light: jint,
) -> jint {
    let b = block_light.clamp(0, 15);
    let s = sky_light.clamp(0, 15);
    (s << 20) | (b << 4)
}

// ── GetRelativeBlockPos ───────────────────────────────────────────────────────
// Java: native String getRelativeBlockPos(int bx, int by, int bz, int ox, int oy, int oz);
// Returns "x,y,z"
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_world_BlockUtilsNative_getRelativeBlockPos(
    mut env: JNIEnv, _class: JClass,
    bx: jint, by: jint, bz: jint,
    ox: jint, oy: jint, oz: jint,
) -> jstring {
    env.new_string(format!("{},{},{}", bx + ox, by + oy, bz + oz))
       .expect("couldn't create string")
       .into_raw()
}

// ── FromChunkAndLocal ─────────────────────────────────────────────────────────
// Java: native String fromChunkAndLocal(int chunkX, int chunkZ, int localX, int localY, int localZ);
// Returns "x,y,z"
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_world_BlockUtilsNative_fromChunkAndLocal(
    mut env: JNIEnv, _class: JClass,
    chunk_x: jint, chunk_z: jint,
    local_x: jint, local_y: jint, local_z: jint,
) -> jstring {
    let world_x = (chunk_x << 4) + local_x;
    let world_z = (chunk_z << 4) + local_z;
    env.new_string(format!("{},{},{}", world_x, local_y, world_z))
       .expect("couldn't create string")
       .into_raw()
}

// ── GetReverseHeightModifier ──────────────────────────────────────────────────
// Just 1 - heightModifier. Kept here to mirror BlockUtils structure.
// blockType: 0=full, 1=slab_bottom, 2=snow(layers 1-8), 3=carpet, 4=air
// Java: native float getReverseHeightModifier(int blockType, int snowLayers);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_world_BlockUtilsNative_getReverseHeightModifier(
    _env: JNIEnv, _class: JClass,
    block_type: jint, snow_layers: jint,
) -> jfloat {
    1.0 - height_modifier(block_type, snow_layers)
}

// ── GetDifferenceInHeight ─────────────────────────────────────────────────────
// Java: native float getDifferenceInHeight(int fromType, int fromLayers, int toType, int toLayers);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_world_BlockUtilsNative_getDifferenceInHeight(
    _env: JNIEnv, _class: JClass,
    from_type: jint, from_layers: jint,
    to_type: jint, to_layers: jint,
) -> jfloat {
    height_modifier(to_type, to_layers) - height_modifier(from_type, from_layers)
}

// ── PackBlockPos ──────────────────────────────────────────────────────────────
// Same Minecraft BlockPos.asLong() encoding. Kept here alongside block logic.
// Java: native long packBlockPos(int x, int y, int z);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_world_BlockUtilsNative_packBlockPos(
    _env: JNIEnv, _class: JClass,
    x: jint, y: jint, z: jint,
) -> jlong {
    let x = x as i64;
    let y = y as i64;
    let z = z as i64;
    ((x & 0x3FFFFFF) << 38) | ((z & 0x3FFFFFF) << 12) | (y & 0xFFF)
}

// ── Distance ──────────────────────────────────────────────────────────────────
// Java: native double blockDistance(int x1, int y1, int z1, int x2, int y2, int z2);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_world_BlockUtilsNative_blockDistance(
    _env: JNIEnv, _class: JClass,
    x1: jint, y1: jint, z1: jint,
    x2: jint, y2: jint, z2: jint,
) -> jdouble {
    let dx = (x2 - x1) as f64;
    let dy = (y2 - y1) as f64;
    let dz = (z2 - z1) as f64;
    (dx*dx + dy*dy + dz*dz).sqrt()
}

// ── IsWithinRadius ────────────────────────────────────────────────────────────
// Java: native boolean isWithinRadius(int x1,int y1,int z1, int x2,int y2,int z2, double radius);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_world_BlockUtilsNative_isWithinRadius(
    _env: JNIEnv, _class: JClass,
    x1: jint, y1: jint, z1: jint,
    x2: jint, y2: jint, z2: jint,
    radius: jdouble,
) -> jboolean {
    let dx = (x2 - x1) as f64;
    let dy = (y2 - y1) as f64;
    let dz = (z2 - z1) as f64;
    (dx*dx + dy*dy + dz*dz <= radius * radius) as jboolean
}

// ── SameChunk ─────────────────────────────────────────────────────────────────
// Java: native boolean sameChunk(int x1, int z1, int x2, int z2);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_world_BlockUtilsNative_sameChunk(
    _env: JNIEnv, _class: JClass,
    x1: jint, z1: jint,
    x2: jint, z2: jint,
) -> jboolean {
    ((x1 >> 4) == (x2 >> 4) && (z1 >> 4) == (z2 >> 4)) as jboolean
}

// ── internal shared ───────────────────────────────────────────────────────────
fn height_modifier(block_type: i32, snow_layers: i32) -> f32 {
    match block_type {
        4 => 1.0,
        1 => 0.5,
        2 => snow_layers as f32 * 0.125,
        3 => 0.0625,
        _ => 1.0,
    }
}