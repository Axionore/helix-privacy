package com.helix.netstatus;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
    private TextView statusPersonal;
    private TextView statusBusiness;
    private TextView statusSecure;
    private TextView blocklistView;
    private final Handler handler = new Handler(Looper.getMainLooper());

    private final BroadcastReceiver updates = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUi();
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
        statusPersonal = new TextView(this);
        statusBusiness = new TextView(this);
        statusSecure = new TextView(this);
        blocklistView = new TextView(this);
        layout.addView(statusPersonal);
        layout.addView(statusBusiness);
        layout.addView(statusSecure);
        layout.addView(blocklistView);
        setContentView(layout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent svc = new Intent();
        svc.setClassName("com.helix.netprivacy", "com.helix.netprivacy.NetPrivacyService");
        bindService(svc, conn, Context.BIND_AUTO_CREATE);
        registerReceiver(updates, new IntentFilter("com.helix.netprivacy.PATH_CHANGED"));
        handler.post(this::updateUi);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacksAndMessages(null);
        unregisterReceiver(updates);
        unbindService(conn);
    }

    private void updateUi() {
        if (netPrivacy == null) {
            statusPersonal.setText("Net privacy service not connected");
            statusBusiness.setText("");
            statusSecure.setText("");
            blocklistView.setText("");
            return;
        }
        try {
            setStatus(statusPersonal, 0, "Personal");
            setStatus(statusBusiness, 1, "Business");
            setStatus(statusSecure, 2, "Secure");
            blocklistView.setText("Blocklist: " + netPrivacy.getBlocklistVersion());
        } catch (Exception e) {
            statusPersonal.setText("Error reading status: " + e.getMessage());
        }
    }

    private void setStatus(TextView view, int domain, String label) throws Exception {
        EgressMode mode = netPrivacy.getDomainEgressMode(domain);
        String modeStr = mode.mode == 2 ? "Tor" : mode.mode == 1 ? "VPN" : "Direct";
        view.setText(label + " egress: " + modeStr + " (" + mode.reason + ")");
    }

    private int dp(int v) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (v * density);
    }
}
