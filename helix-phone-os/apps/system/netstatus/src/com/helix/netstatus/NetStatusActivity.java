package com.helix.netstatus;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helix.netprivacy.EgressMode;
import com.helix.netprivacy.INetPrivacy;

/**
 * Minimal UI to show per-domain network path and blocklist status.
 */
public class NetStatusActivity extends Activity {
    private INetPrivacy netPrivacy;
    private TextView statusView;
    private TextView blocklistView;
    private final Handler handler = new Handler(Looper.getMainLooper());

    private final Runnable poll = new Runnable() {
        @Override
        public void run() {
            updateUi();
            handler.postDelayed(this, 5_000);
        }
    };

    private final ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            netPrivacy = INetPrivacy.Stub.asInterface(service);
            updateUi();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            netPrivacy = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        int pad = dp(16);
        layout.setPadding(pad, pad, pad, pad);
        statusView = new TextView(this);
        blocklistView = new TextView(this);
        layout.addView(statusView);
        layout.addView(blocklistView);
        setContentView(layout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent svc = new Intent();
        svc.setClassName("com.helix.netprivacy", "com.helix.netprivacy.NetPrivacyService");
        bindService(svc, conn, Context.BIND_AUTO_CREATE);
        handler.post(poll);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(poll);
        unbindService(conn);
    }

    private void updateUi() {
        if (netPrivacy == null) {
            statusView.setText("Net privacy service not connected");
            blocklistView.setText("");
            return;
        }
        try {
            EgressMode mode = netPrivacy.getDomainEgressMode(0); // domain 0 as placeholder
            String modeStr = mode.mode == 2 ? "Tor" : mode.mode == 1 ? "VPN" : "Direct";
            statusView.setText("Domain 0 egress: " + modeStr + " (" + mode.reason + ")");
            blocklistView.setText("Blocklist: " + netPrivacy.getBlocklistVersion());
        } catch (Exception e) {
            statusView.setText("Error reading status: " + e.getMessage());
        }
    }

    private int dp(int v) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (v * density);
    }
}
