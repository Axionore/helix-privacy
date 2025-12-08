package com.helix.netprivacy;

import android.util.Log;

/**
 * Stub for applying fail-closed firewall rules.
 * Real implementation should use iptables/ebpf to drop traffic when required path is down.
 */
final class FirewallEnforcer {
    private static final String TAG = "HelixFirewall";

    void enforceDropAll(boolean enable) {
        // TODO: apply iptables/ebpf rules to drop all egress until VPN/Tor is healthy.
        Log.i(TAG, "Fail-closed firewall " + (enable ? "enabled" : "disabled") + " (stub)");
    }
}
