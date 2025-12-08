package com.helix.netprivacy;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.helix.netprivacy.EgressMode;
import com.helix.netprivacy.INetPrivacy;

/**
 * Skeleton network privacy service.
 * - Enforces per-domain egress mode (Direct/VPN/Tor).
 * - Intended to manage VPN/Tor daemons and blocklists.
 * - Fail-closed: if required path is unavailable, traffic should be blocked.
 */
public class NetPrivacyService extends Service {
    private static final String TAG = "HelixNetPrivacy";

    // Placeholder state; real implementation should be per-domain.
    private final BlocklistManager blocklists = new BlocklistManager();
    private boolean vpnHealthy = false;
    private boolean torHealthy = false;

    private final INetPrivacy.Stub binder = new INetPrivacy.Stub() {
        @Override
        public EgressMode getDomainEgressMode(int domainId) {
            // TODO: consult policy engine for required mode.
            EgressMode mode = new EgressMode();
            mode.mode = 1; // default to VPN
            mode.reason = "default VPN enforced";
            return mode;
        }

        @Override
        public String getBlocklistVersion() {
            return blocklists.currentVersionHash;
        }

        @Override
        public boolean isCompliant() {
            // Fail closed if VPN is required but not healthy; extend for Tor.
            return vpnHealthy || torHealthy;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "NetPrivacyService start requested");
        // TODO: start/monitor VPN and Tor daemons; hydrate blocklists.
        return START_STICKY;
    }

    // Stub hooks for daemon lifecycle and enforcement.
    private void ensureVpn() {
        // TODO: start/monitor Helix VPN daemon; set vpnHealthy accordingly.
    }

    private void ensureTor() {
        // TODO: start/monitor Tor daemon for secure domain; set torHealthy accordingly.
    }

    private void applyFirewallFailClosed() {
        // TODO: apply iptables/ebpf rules to drop traffic when required path is down.
    }
}
