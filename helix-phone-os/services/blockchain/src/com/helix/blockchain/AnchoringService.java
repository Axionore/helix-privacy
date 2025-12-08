package com.helix.blockchain;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Skeleton anchoring service; real ledger client to be added.
 */
public class AnchoringService extends Service {
    private static final String TAG = "HelixAnchoring";

    @Override
    public IBinder onBind(Intent intent) {
        return null; // TODO: expose binder once AIDL defined
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Anchoring service started (stub)");
        return START_STICKY;
    }
}
