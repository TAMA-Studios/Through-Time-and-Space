use jni::JNIEnv;
use jni::objects::{JClass, JString, JObjectArray, JObject};
use jni::sys::{jstring, jboolean, jobjectArray};

// ── CapitalizeFirstLetters ────────────────────────────────────────────────────
// Java: native String capitalizeFirstLetters(String text);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_GrammarNazi_capitalizeFirstLetters(
    mut env: JNIEnv,
    _class: JClass,
    input: JString,
) -> jstring {
    let text: String = env.get_string(&input).expect("bad string").into();

    let result = capitalize_first_letters(&text);

    env.new_string(result).expect("couldn't create string").into_raw()
}

pub fn capitalize_first_letters(text: &str) -> String {
    let mut result = String::with_capacity(text.len());
    let mut cap_next = true;

    for ch in text.chars() {
        if cap_next && ch != ' ' {
            result.extend(ch.to_uppercase());
            cap_next = false;
        } else {
            result.push(ch);
        }
        if ch == ' ' {
            cap_next = true;
        }
    }

    result
}

// ── CleanString (underscore → space + capitalize) ─────────────────────────────
// Java: native String cleanString(String text);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_GrammarNazi_cleanString(
    mut env: JNIEnv,
    _class: JClass,
    input: JString,
) -> jstring {
    let text: String = env.get_string(&input).expect("bad string").into();
    let result = capitalize_first_letters(&text.replace('_', " "));
    env.new_string(result).expect("couldn't create string").into_raw()
}

// ── ScoreToSpace ──────────────────────────────────────────────────────────────
// Java: native String scoreToSpace(String text);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_GrammarNazi_scoreToSpace(
    mut env: JNIEnv,
    _class: JClass,
    input: JString,
) -> jstring {
    let text: String = env.get_string(&input).expect("bad string").into();
    env.new_string(text.replace('_', " ")).expect("couldn't create string").into_raw()
}

// ── BlockPosToString ──────────────────────────────────────────────────────────
// Parses "BlockPos{x=1, y=2, z=3}" → "1 2 3" (same behaviour as your Java)
// Java: native String blockPosToString(String blockPosToString);
// (Pass blockPos.toString() from Java side,  avoids needing a full JObject mapping)
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_GrammarNazi_blockPosToString(
    mut env: JNIEnv,
    _class: JClass,
    input: JString,
) -> jstring {
    let text: String = env.get_string(&input).expect("bad string").into();

    // strip "BlockPos", braces, x= y= z= and colons, lowercase
    let result: String = text
        .to_lowercase()
        .replace("blockpos", "")
        .chars()
        .filter(|c| !matches!(c, '{' | '}' | ':' | 'x' | 'y' | 'z' | '='))
        .collect::<String>()
        // collapse double spaces left by stripping x= y= z=
        .split_whitespace()
        .collect::<Vec<_>>()
        .join(" ");

    env.new_string(result).expect("couldn't create string").into_raw()
}

// ── IDFromBlock ───────────────────────────────────────────────────────────────
// Mirrors your IDFromBlock: strips namespaces, braces, colons, lowercases,
// drops first 5 chars (the "Block" prefix left by toString)
// Java: native String idFromBlock(String blockToString);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_GrammarNazi_idFromBlock(
    mut env: JNIEnv,
    _class: JClass,
    input: JString,
) -> jstring {
    let text: String = env.get_string(&input).expect("bad string").into();

    let cleaned = text
        .to_lowercase()
        .replace("aseoha:", "")
        .replace("minecraft:", "")
        .replace(['{', '}', ':'], "");

    // drop leading "block" (5 chars)
    let result = if cleaned.len() > 5 { &cleaned[5..] } else { &cleaned };

    env.new_string(result).expect("couldn't create string").into_raw()
}

// ── FullIDFromBlock ───────────────────────────────────────────────────────────
// Like IDFromBlock but keeps the namespace
// Java: native String fullIdFromBlock(String blockToString);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_GrammarNazi_fullIdFromBlock(
    mut env: JNIEnv,
    _class: JClass,
    input: JString,
) -> jstring {
    let text: String = env.get_string(&input).expect("bad string").into();
    let cleaned = text
        .to_lowercase()
        .replace(['{', '}'], "");
    let result = if cleaned.len() > 5 { &cleaned[5..] } else { &cleaned };
    env.new_string(result).expect("couldn't create string").into_raw()
}

// ── IDFromItem ────────────────────────────────────────────────────────────────
// Java: native String idFromItem(String itemToString, String modid);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_GrammarNazi_idFromItem(
    mut env: JNIEnv,
    _class: JClass,
    input: JString,
    modid: JString,
) -> jstring {
    let text: String = env.get_string(&input).expect("bad string").into();
    let mod_id: String = env.get_string(&modid).expect("bad modid").into();

    let result = text
        .to_lowercase()
        .replace(&mod_id, "")
        .replace(['{', '}', ':'], "");

    env.new_string(result).expect("couldn't create string").into_raw()
}

// ── Stitch ────────────────────────────────────────────────────────────────────
// Java: native String stitch(String[] strings);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_GrammarNazi_stitch(
    mut env: JNIEnv,
    _class: JClass,
    strings: JObjectArray,
) -> jstring {
    let result = join_string_array(&mut env, &strings, "");
    env.new_string(result).expect("couldn't create string").into_raw()
}

// ── StitchWithDelimiter ───────────────────────────────────────────────────────
// Java: native String stitchWithDelimiter(String delimiter, String[] strings);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_GrammarNazi_stitchWithDelimiter(
    mut env: JNIEnv,
    _class: JClass,
    delimiter: JString,
    strings: JObjectArray,
) -> jstring {
    let delim: String = env.get_string(&delimiter).expect("bad delimiter").into();
    let result = join_string_array(&mut env, &strings, &delim);
    env.new_string(result).expect("couldn't create string").into_raw()
}

// ── CleanItemString ───────────────────────────────────────────────────────────
// Java: native String cleanItemString(String itemToString, String modid);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_GrammarNazi_cleanItemString(
    mut env: JNIEnv,
    _class: JClass,
    input: JString,
    modid: JString,
) -> jstring {
    let text: String = env.get_string(&input).expect("bad string").into();
    let mod_id: String = env.get_string(&modid).expect("bad modid").into();

    let prefix = format!("minecraft:item@{}:", mod_id);
    let cleaned = text.replace(&prefix, "").replace('_', " ");
    let result = capitalize_first_letters(&cleaned);

    env.new_string(result).expect("couldn't create string").into_raw()
}

// ── ContainsIgnoreCase ────────────────────────────────────────────────────────
// Java: native boolean containsIgnoreCase(String haystack, String needle);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_GrammarNazi_containsIgnoreCase(
    mut env: JNIEnv,
    _class: JClass,
    haystack: JString,
    needle: JString,
) -> jboolean {
    let h: String = env.get_string(&haystack).expect("bad string").into();
    let n: String = env.get_string(&needle).expect("bad string").into();
    (h.to_lowercase().contains(&n.to_lowercase())) as jboolean
}

// ── TruncateWithEllipsis ──────────────────────────────────────────────────────
// Java: native String truncate(String text, int maxLen);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_GrammarNazi_truncate(
    mut env: JNIEnv,
    _class: JClass,
    input: JString,
    max_len: jni::sys::jint,
) -> jstring {
    let text: String = env.get_string(&input).expect("bad string").into();
    let max = max_len as usize;

    let result = if text.chars().count() > max {
        let truncated: String = text.chars().take(max.saturating_sub(3)).collect();
        format!("{}...", truncated)
    } else {
        text
    };

    env.new_string(result).expect("couldn't create string").into_raw()
}

// ── StripNamespace ────────────────────────────────────────────────────────────
// Strips "namespace:" prefix from a resource location string
// Java: native String stripNamespace(String resourceLocation);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_GrammarNazi_stripNamespace(
    mut env: JNIEnv,
    _class: JClass,
    input: JString,
) -> jstring {
    let text: String = env.get_string(&input).expect("bad string").into();
    let result = if let Some(pos) = text.find(':') {
        &text[pos + 1..]
    } else {
        &text
    };
    env.new_string(result).expect("couldn't create string").into_raw()
}

// ── internal helper ───────────────────────────────────────────────────────────
fn join_string_array(env: &mut JNIEnv, arr: &JObjectArray, delimiter: &str) -> String {
    let len = env.get_array_length(arr).unwrap_or(0);
    let mut parts: Vec<String> = Vec::with_capacity(len as usize);

    for i in 0..len {
        let obj: JObject = env.get_object_array_element(arr, i).unwrap();
        let jstr = JString::from(obj);
        let s: String = match env.get_string(&jstr) {
            Ok(js) => js.into(),
            Err(_) => String::new(),
        };
        parts.push(s);
    }

    parts.join(delimiter)
}