#!/usr/bin/env bash
set -euo pipefail

if [ "$#" -ne 2 ]; then
  echo "Usage: $0 <jar-folder> <pack-zip>"
  exit 1
fi

FOLDER_PATH="$1"
OTHER_ZIP="$2"

if [ ! -d "$FOLDER_PATH" ]; then
  echo "Error: folder does not exist: $FOLDER_PATH"
  exit 1
fi

if [ ! -f "$OTHER_ZIP" ]; then
  echo "Error: optimized pack zip does not exist: $OTHER_ZIP"
  exit 1
fi

JAR_FILE="$(find "$FOLDER_PATH" -maxdepth 1 -type f -name "*-shadowed.jar" | head -n 1)"

if [ -z "$JAR_FILE" ]; then
  echo "Error: no *-shadowed.jar found in: $FOLDER_PATH"
  echo "Available jars:"
  find "$FOLDER_PATH" -maxdepth 1 -type f -name "*.jar" -print || true
  exit 1
fi

TEMP_DIR="$(mktemp -d)"
BACKUP_FILE="${JAR_FILE}.bak"

cleanup() {
  rm -rf "$TEMP_DIR"
}
trap cleanup EXIT

echo "Found jar: $JAR_FILE"
echo "Found optimized pack: $OTHER_ZIP"

echo "Backing up original jar to: $BACKUP_FILE"
cp "$JAR_FILE" "$BACKUP_FILE"

echo "Extracting jar..."
unzip -q "$JAR_FILE" -d "$TEMP_DIR"

echo "Merging optimized resources..."
unzip -qo "$OTHER_ZIP" -d "$TEMP_DIR"

echo "Removing old jar..."
rm -f "$JAR_FILE"

echo "Rebuilding jar..."
jar --create --file="$JAR_FILE" -C "$TEMP_DIR" .

echo "Merge complete."
echo "Backup file: $BACKUP_FILE"
echo "New jar file: $JAR_FILE"
