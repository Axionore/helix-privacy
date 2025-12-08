# Helix Phone OS Build (Android 14/15)

Scaffold for building the modern Helix Phone OS on top of AOSP 14/15.

## Prereqs
- Linux host with Android build deps (JDK 17, repo tool, Python 3, git, build-essential, etc.).
- 300GB free disk, 16GB+ RAM recommended.
- USB/fastboot tools installed and in PATH.

## Workflow (draft)
1) Initialize workspace with a Helix manifest (to be published):
   ```
   repo init -u https://github.com/<org>/helix-phone-os-manifest.git -b main
   repo sync -j$(nproc)
   ```
2) Source env and choose target:
   ```
   source build/envsetup.sh
   lunch helix_<device>-user
   ```
3) Build:
   ```
   m
   ```
4) Flash:
   ```
   fastboot flashall
   ```

## Custom bits to add here
- `manifest.xml`: pin AOSP 14/15 + Helix repos, prebuilts, and bridge agent.
- `envsetup.d/`: helpers for policy keys, bridge certs, and signing configs.
- `keys/`: placeholders for test keys (never commit production keys).
- `scripts/`: automation for CI builds, OTA packaging, and license/notice checks.

## Policy/bridge integration
- Policy bundle baked into system image for defaults; OTA-updatable via signed bundles.
- Bridge agent included as a system app/service with domain-aware privileges.

## Testing notes
- Enable `SANITIZE_TARGET=address` builds for security testing on userdebug images.
- Run CTS/VTS where applicable; maintain a reduced smoke suite for CI.
