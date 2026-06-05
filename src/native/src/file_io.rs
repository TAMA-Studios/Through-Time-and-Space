use jni::JNIEnv;
use jni::objects::{JClass, JString};
use jni::sys::{jboolean, jstring};

use std::fs::{self, File, OpenOptions};
use std::io::{BufRead, BufReader, BufWriter, Write};
use std::path::Path;

// ── WriteFile (create/overwrite) ──────────────────────────────────────────────
// Java: native boolean writeFile(String absolutePath, String content);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_FileHelperNative_writeFile(
    mut env: JNIEnv,
    _class: JClass,
    absolute_path: JString,
    content: JString,
) -> jboolean {
    let path: String = env.get_string(&absolute_path).expect("bad path").into();
    let content: String = env.get_string(&content).expect("bad content").into();

    match write_file(&path, &content) {
        Ok(_)  => 1,
        Err(_) => 0,
    }
}

// ── AppendFile ────────────────────────────────────────────────────────────────
// Java: native boolean appendFile(String absolutePath, String content);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_FileHelperNative_appendFile(
    mut env: JNIEnv,
    _class: JClass,
    absolute_path: JString,
    content: JString,
) -> jboolean {
    let path: String = env.get_string(&absolute_path).expect("bad path").into();
    let content: String = env.get_string(&content).expect("bad content").into();

    match append_file(&path, &content) {
        Ok(_)  => 1,
        Err(_) => 0,
    }
}

// ── ReadFile ──────────────────────────────────────────────────────────────────
// Returns null on failure (matches Java behaviour of returning null on missing file).
// Java: native String readFile(String absolutePath);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_FileHelperNative_readFile(
    mut env: JNIEnv,
    _class: JClass,
    absolute_path: JString,
) -> jstring {
    let path: String = env.get_string(&absolute_path).expect("bad path").into();

    match read_file(&path) {
        Ok(content) => env.new_string(content).expect("couldn't create string").into_raw(),
        Err(_)      => std::ptr::null_mut(), // null → Java gets null
    }
}

// ── FileExists ────────────────────────────────────────────────────────────────
// Java: native boolean fileExists(String absolutePath);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_FileHelperNative_fileExists(
    mut env: JNIEnv,
    _class: JClass,
    absolute_path: JString,
) -> jboolean {
    let path: String = env.get_string(&absolute_path).expect("bad path").into();
    Path::new(&path).exists() as jboolean
}

// ── DeleteFile ────────────────────────────────────────────────────────────────
// Java: native boolean deleteFile(String absolutePath);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_FileHelperNative_deleteFile(
    mut env: JNIEnv,
    _class: JClass,
    absolute_path: JString,
) -> jboolean {
    let path: String = env.get_string(&absolute_path).expect("bad path").into();
    fs::remove_file(&path).is_ok() as jboolean
}

// ── EnsureDirExists ───────────────────────────────────────────────────────────
// Creates directory and all parents. Returns false only on actual failure.
// Java: native boolean ensureDirExists(String absoluteDirPath);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_FileHelperNative_ensureDirExists(
    mut env: JNIEnv,
    _class: JClass,
    absolute_dir: JString,
) -> jboolean {
    let path: String = env.get_string(&absolute_dir).expect("bad path").into();
    fs::create_dir_all(&path).is_ok() as jboolean
}

// ── ReadLines ─────────────────────────────────────────────────────────────────
// Returns all lines joined by '\n', null on failure.
// Same result as readFile but uses a line-buffered reader — better for huge files
// since it doesn't need to hold the whole content + trimmed copy in memory.
// Java: native String readLines(String absolutePath);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_FileHelperNative_readLines(
    mut env: JNIEnv,
    _class: JClass,
    absolute_path: JString,
) -> jstring {
    let path: String = env.get_string(&absolute_path).expect("bad path").into();

    match read_lines_joined(&path) {
        Ok(content) => env.new_string(content).expect("couldn't create string").into_raw(),
        Err(_)      => std::ptr::null_mut(),
    }
}

// ── AppendLines ───────────────────────────────────────────────────────────────
// Appends multiple lines at once in a single buffered write — much faster than
// calling appendFile() in a loop from Java.
// Lines are joined with '\n'; pass them newline-separated from Java.
// Java: native boolean appendLines(String absolutePath, String lines);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_FileHelperNative_appendLines(
    mut env: JNIEnv,
    _class: JClass,
    absolute_path: JString,
    lines: JString,
) -> jboolean {
    let path: String    = env.get_string(&absolute_path).expect("bad path").into();
    let content: String = env.get_string(&lines).expect("bad lines").into();

    // Each line in `content` separated by \n; append each with a trailing newline
    match append_file(&path, &content) {
        Ok(_)  => 1,
        Err(_) => 0,
    }
}

// ── CopyFile ──────────────────────────────────────────────────────────────────
// Java: native boolean copyFile(String srcPath, String destPath);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_FileHelperNative_copyFile(
    mut env: JNIEnv,
    _class: JClass,
    src_path: JString,
    dest_path: JString,
) -> jboolean {
    let src: String  = env.get_string(&src_path).expect("bad src").into();
    let dest: String = env.get_string(&dest_path).expect("bad dest").into();

    // Ensure parent dir of dest exists
    if let Some(parent) = Path::new(&dest).parent() {
        let _ = fs::create_dir_all(parent);
    }

    fs::copy(&src, &dest).is_ok() as jboolean
}

// ── GetFileSize ───────────────────────────────────────────────────────────────
// Returns -1 if the file doesn't exist or can't be read.
// Java: native long getFileSize(String absolutePath);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_FileHelperNative_getFileSize(
    mut env: JNIEnv,
    _class: JClass,
    absolute_path: JString,
) -> jni::sys::jlong {
    let path: String = env.get_string(&absolute_path).expect("bad path").into();
    fs::metadata(&path).map(|m| m.len() as i64).unwrap_or(-1)
}

// ── ListFiles ─────────────────────────────────────────────────────────────────
// Returns filenames (not full paths) in a directory, newline-separated.
// Returns null if the directory doesn't exist or can't be read.
// Java: native String listFiles(String absoluteDirPath);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_FileHelperNative_listFiles(
    mut env: JNIEnv,
    _class: JClass,
    absolute_dir: JString,
) -> jstring {
    let path: String = env.get_string(&absolute_dir).expect("bad path").into();

    let result = fs::read_dir(&path).ok().map(|entries| {
        entries
            .filter_map(|e| e.ok())
            .filter(|e| e.path().is_file())
            .filter_map(|e| e.file_name().into_string().ok())
            .collect::<Vec<_>>()
            .join("\n")
    });

    match result {
        Some(s) => env.new_string(s).expect("couldn't create string").into_raw(),
        None    => std::ptr::null_mut(),
    }
}

// ── TruncateFile ─────────────────────────────────────────────────────────────
// Wipes the content of a file without deleting it.
// Java: native boolean truncateFile(String absolutePath);
#[no_mangle]
pub extern "system" fn Java_com_code_tama_triggerapi_helpers_FileHelperNative_truncateFile(
    mut env: JNIEnv,
    _class: JClass,
    absolute_path: JString,
) -> jboolean {
    let path: String = env.get_string(&absolute_path).expect("bad path").into();
    OpenOptions::new()
        .write(true)
        .truncate(true)
        .open(&path)
        .is_ok() as jboolean
}

// ── Internal helpers ──────────────────────────────────────────────────────────

fn ensure_parent(path: &str) -> std::io::Result<()> {
    if let Some(parent) = Path::new(path).parent() {
        fs::create_dir_all(parent)?;
    }
    Ok(())
}

fn write_file(path: &str, content: &str) -> std::io::Result<()> {
    ensure_parent(path)?;
    let file = File::create(path)?;
    let mut writer = BufWriter::with_capacity(64 * 1024, file); // 64 KB buffer
    writer.write_all(content.as_bytes())?;
    writer.write_all(b"\n")?;
    writer.flush()
}

fn append_file(path: &str, content: &str) -> std::io::Result<()> {
    ensure_parent(path)?;
    let file = OpenOptions::new().create(true).append(true).open(path)?;
    let mut writer = BufWriter::with_capacity(64 * 1024, file);
    writer.write_all(content.as_bytes())?;
    writer.write_all(b"\n")?;
    writer.flush()
}

fn read_file(path: &str) -> std::io::Result<String> {
    let content = fs::read_to_string(path)?;
    // Mirror Java behaviour: trim trailing whitespace
    Ok(content.trim_end().to_owned())
}

fn read_lines_joined(path: &str) -> std::io::Result<String> {
    let file = File::open(path)?;
    let reader = BufReader::with_capacity(64 * 1024, file);
    let lines: Vec<String> = reader.lines().collect::<std::io::Result<_>>()?;
    Ok(lines.join("\n"))
}