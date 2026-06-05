use jni::JNIEnv;
use jni::objects::JClass;
use jni::sys::{jint, jfloat, jdouble, jlong};

// ── RoundToMultiple (int) ─────────────────────────────────────────────────────
// Java: native int roundToMultiple(int num, int multiple);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_MathUtilsNative_roundToMultipleInt(
    _env: JNIEnv, _class: JClass,
    num: jint, multiple: jint,
) -> jint {
    ((num as f32 / multiple as f32).round() as i32) * multiple
}

// ── RoundToMultiple (float) ───────────────────────────────────────────────────
// Java: native int roundToMultipleFloat(float num, int multiple);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_MathUtilsNative_roundToMultipleFloat(
    _env: JNIEnv, _class: JClass,
    num: jfloat, multiple: jint,
) -> jint {
    ((num / multiple as f32).round() as i32) * multiple
}

// ── RoundToMultiple (double) ──────────────────────────────────────────────────
// Java: native int roundToMultipleDouble(double num, int multiple);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_MathUtilsNative_roundToMultipleDouble(
    _env: JNIEnv, _class: JClass,
    num: jdouble, multiple: jint,
) -> jint {
    ((num / multiple as f64).round() as i32) * multiple
}

// ── RoundTo48 ─────────────────────────────────────────────────────────────────
// Java: native int roundTo48(int num);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_MathUtilsNative_roundTo48(
    _env: JNIEnv, _class: JClass,
    num: jint,
) -> jint {
    ((num as f32 / 48.0).round() as i32) * 48
}

// ── ReverseRoundTo48 ──────────────────────────────────────────────────────────
// Java: native int reverseRoundTo48(int num);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_MathUtilsNative_reverseRoundTo48(
    _env: JNIEnv, _class: JClass,
    num: jint,
) -> jint {
    reverse_round(num as f64 / 48.0) * 48
}

// ── ReverseRound ──────────────────────────────────────────────────────────────
// Rounds toward zero if decimal >= 0.5, away from zero otherwise.
// Java: native int reverseRound(double value);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_MathUtilsNative_reverseRound(
    _env: JNIEnv, _class: JClass,
    value: jdouble,
) -> jint {
    reverse_round(value)
}

pub fn reverse_round(value: f64) -> i32 {
    let int_part = value as i32;
    let decimal = (value - int_part as f64).abs();
    if decimal >= 0.5 {
        int_part // round toward zero
    } else if value >= 0.0 {
        int_part + 1 // round away from zero (positive)
    } else {
        int_part - 1 // round away from zero (negative)
    }
}

// ── AngleBetween ──────────────────────────────────────────────────────────────
// Angle (degrees) between two 3D vectors passed as x,y,z components.
// Java: native double angleBetween(double x1, double y1, double z1, double x2, double y2, double z2);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_MathUtilsNative_angleBetween(
    _env: JNIEnv, _class: JClass,
    x1: jdouble, y1: jdouble, z1: jdouble,
    x2: jdouble, y2: jdouble, z2: jdouble,
) -> jdouble {
    let dot = x1 * x2 + y1 * y2 + z1 * z2;
    let mag1 = (x1 * x1 + y1 * y1 + z1 * z1).sqrt();
    let mag2 = (x2 * x2 + y2 * y2 + z2 * z2).sqrt();
    let cos_angle = (dot / (mag1 * mag2)).clamp(-1.0, 1.0);
    cos_angle.acos().to_degrees()
}

// ── Clamp (float) ─────────────────────────────────────────────────────────────
// Java: native float clampFloat(float value, float min, float max);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_MathUtilsNative_clampFloat(
    _env: JNIEnv, _class: JClass,
    value: jfloat, min: jfloat, max: jfloat,
) -> jfloat {
    value.clamp(min, max)
}

// ── Clamp (long) ──────────────────────────────────────────────────────────────
// Java: native long clampLong(long value, long min, long max);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_MathUtilsNative_clampLong(
    _env: JNIEnv, _class: JClass,
    value: jlong, min: jlong, max: jlong,
) -> jlong {
    value.clamp(min, max)
}

// ── Clamp (double) ────────────────────────────────────────────────────────────
// Java: native double clampDouble(double value, double min, double max);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_MathUtilsNative_clampDouble(
    _env: JNIEnv, _class: JClass,
    value: jdouble, min: jdouble, max: jdouble,
) -> jdouble {
    value.clamp(min, max)
}

// ── GetSlopedRotation ─────────────────────────────────────────────────────────
// Java: native float getSlopedRotation(float slope, float desiredRot);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_MathUtilsNative_getSlopedRotation(
    _env: JNIEnv, _class: JClass,
    slope: jfloat, desired_rot: jfloat,
) -> jfloat {
    // Normalize to [0, 360)
    let rot = ((desired_rot % 360.0) + 360.0) % 360.0;

    match rot {
        r if r == 0.0   => slope,
        r if r == 180.0 => -slope,
        r => (slope as f64 * (r as f64).to_radians().cos()) as f32,
    }
}

// ── Lerp (float) ──────────────────────────────────────────────────────────────
// Java: native float lerp(float start, float end, float t);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_MathUtilsNative_lerp(
    _env: JNIEnv, _class: JClass,
    start: jfloat, end: jfloat, t: jfloat,
) -> jfloat {
    start + t * (end - start)
}

// ── PackedLight ───────────────────────────────────────────────────────────────
// Computes the packed light value from separate block/sky light values.
// (The actual level query stays in Java; this just does the bit-packing math.)
// Java: native int packLight(int blockLight, int skyLight);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_MathUtilsNative_packLight(
    _env: JNIEnv, _class: JClass,
    block_light: jint, sky_light: jint,
) -> jint {
    let b = block_light.clamp(0, 15);
    let s = sky_light.clamp(0, 15);
    (s << 20) | (b << 4)
}

// ── HeightModifier (pure numeric version) ─────────────────────────────────────
// Returns the height fraction for a block type tag passed from Java.
// blockType: 0=full, 1=slab_bottom, 2=snow(layers 1-8), 3=carpet, 4=air
// For snow, pass snowLayers (1-8). Ignored for other types.
// Java: native float heightModifier(int blockType, int snowLayers);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_MathUtilsNative_heightModifier(
    _env: JNIEnv, _class: JClass,
    block_type: jint, snow_layers: jint,
) -> jfloat {
    match block_type {
        4 => 1.0,           // AIR
        1 => 0.5,           // bottom slab
        2 => snow_layers as f32 * 0.125, // snow layers
        3 => 0.0625,        // carpet
        _ => 1.0,           // full block (or unknown)
    }
}

// ── DifferenceInHeight ────────────────────────────────────────────────────────
// Java: native float differenceInHeight(int fromType, int fromLayers, int toType, int toLayers);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_MathUtilsNative_differenceInHeight(
    _env: JNIEnv, _class: JClass,
    from_type: jint, from_layers: jint,
    to_type: jint, to_layers: jint,
) -> jfloat {
    height_modifier(to_type, to_layers) - height_modifier(from_type, from_layers)
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