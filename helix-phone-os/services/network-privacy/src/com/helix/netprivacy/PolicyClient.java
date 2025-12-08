package com.helix.netprivacy;

import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

import com.helix.policy.IPolicyService;

/**
 * Lightweight client to fetch required egress mode per domain from Policy Engine.
 */
final class PolicyClient {
    private static final String TAG = "HelixPolicyClient";
    private static final String SERVICE_NAME = "helix_policy";

    private IPolicyService policy;

    int getRequiredMode(int domainId, int defaultMode) {
        try {
            ensure();
            if (policy == null) return defaultMode;
            // Reuse DomainState to derive network requirement once implemented; default to VPN.
            return defaultMode;
        } catch (Exception e) {
            Log.w(TAG, "Policy query failed, defaulting", e);
            return defaultMode;
        }
    }

    private void ensure() {
        if (policy != null) return;
        IBinder b = ServiceManager.getService(SERVICE_NAME);
        if (b != null) {
            policy = IPolicyService.Stub.asInterface(b);
        }
    }
}
