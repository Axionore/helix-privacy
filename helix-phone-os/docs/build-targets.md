# Build Targets (initial)

Planned device targets for Helix Phone OS on AOSP 14/15. Fill in device codenames and board details as they are validated.

## Candidate devices
- Pixel 7/7a/7 Pro (first-line bring-up).
- Pixel 8/8a/8 Pro (next-line bring-up).
- Emulator (x86_64) for CI smoke.

## Example lunch combos
- `helix_panther-user`
- `helix_panther-userdebug`
- `helix_shiba-user`
- `helix_shiba-userdebug`
- `helix_emulator-userdebug`

## Per-target notes
- AVB/rollback: ensure vbmeta keys replaced with Helix test/prod keys.
- Radio/firmware: follow vendor partitions from stock images; do not modify proprietary bits.
- Features: keep NFC, Bluetooth, and camera; strip unnecessary debug packages from user builds.

## Action items
- Add `device/<vendor>/<codename>` entries to manifest (see `build/manifest.example.xml`).
- Define lunch combos: `helix_<codename>-user`, `helix_<codename>-userdebug`.
- Document flashing steps per device (fastboot/adb).
