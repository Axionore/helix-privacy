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
    private final FirewallEnforcer firewall = new FirewallEnforcer();
    private final PolicyClient policyClient = new PolicyClient();
    private final VpnController vpn = new VpnController();
    private final TorController tor = new TorController();
    private boolean vpnHealthy = false;
    private boolean torHealthy = false;

    private final INetPrivacy.Stub binder = new INetPrivacy.Stub() {
        @Override
        public EgressMode getDomainEgressMode(int domainId) {
            int required = policyClient.getRequiredMode(domainId, /*defaultMode=*/EgressModes.VPN);
            EgressMode mode = new EgressMode();
            mode.mode = required;
            mode.reason = required == EgressModes.TOR ? "Tor required" : "VPN required";
            return mode;
        }

        @Override
        public String getBlocklistVersion() {
            return blocklists.currentVersionHash;
        }

        @Override
        public boolean isCompliant() {
            // Fail closed if VPN/Tor is required but not healthy.
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
        hydrateBlocklists();
        ensurePaths();
        return START_STICKY;
    }

    // Stub hooks for daemon lifecycle and enforcement.
    private void ensurePaths() {
        vpnHealthy = vpn.ensure();
        torHealthy = tor.ensure();
        boolean compliant = vpnHealthy || torHealthy;
        firewall.enforceDropAll(!compliant);
    }

    private void hydrateBlocklists() {
        try {
            blocklists.fetchAndVerify(BlocklistManager.DEFAULT_SOURCE, ""); // TODO: expected hash + signature
        } catch (Exception e) {
            Log.w(TAG, "Blocklist fetch failed", e);
        }
    }
}
