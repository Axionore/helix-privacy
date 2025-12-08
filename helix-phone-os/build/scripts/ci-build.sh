#!/usr/bin/env bash
set -euo pipefail

# Minimal CI scaffold for Helix Phone OS.
# Expects repo workspace already synced with manifest.example.xml.

if [ -z "${ANDROID_BUILD_TOP:-}" ]; then
  echo "Run from an Android build environment (source build/envsetup.sh)" >&2
  exit 1
fi

TARGET="${TARGET:-helix_emulator-userdebug}"

echo "Lunching ${TARGET}"
lunch "${TARGET}"

echo "Starting build..."
m

echo "Build complete."
