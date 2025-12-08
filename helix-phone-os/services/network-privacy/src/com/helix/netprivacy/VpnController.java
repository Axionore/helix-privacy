package com.helix.netprivacy;

import android.util.Log;

/**
 * Stub controller for Helix VPN daemon lifecycle and health.
 */
final class VpnController {
    private static final String TAG = "HelixVpnController";

    boolean ensure() {
        // TODO: start/monitor VPN daemon, configure routing per domain.
        Log.i(TAG, "Ensuring VPN daemon (stub)");
        return true;
    }
}
