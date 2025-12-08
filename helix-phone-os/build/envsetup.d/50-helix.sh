# shellcheck shell=sh

# Helpers for Helix Phone OS builds. Source via build/envsetup.sh.

export HELIX_POLICY_KEY="${HELIX_POLICY_KEY:-/path/to/test/policy.pem}"
export HELIX_BRIDGE_CERT="${HELIX_BRIDGE_CERT:-/path/to/test/bridge_cert.pem}"
export HELIX_SIGN_TARGET_FILES="${HELIX_SIGN_TARGET_FILES:-true}"

helix_print_config() {
  echo "HELIX_POLICY_KEY=${HELIX_POLICY_KEY}"
  echo "HELIX_BRIDGE_CERT=${HELIX_BRIDGE_CERT}"
  echo "HELIX_SIGN_TARGET_FILES=${HELIX_SIGN_TARGET_FILES}"
}

add_lunch_combo helix_emulator-userdebug
