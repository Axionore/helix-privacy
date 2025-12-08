package com.helix.messaging;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Placeholder UI for Helix Messenger.
 * Implement E2EE flows and domain separation in future work.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        int pad = dp(16);
        layout.setPadding(pad, pad, pad, pad);
        TextView tv = new TextView(this);
        tv.setText("Helix Messenger (stub)");
        layout.addView(tv);
        setContentView(layout);
    }

    private int dp(int v) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (v * density);
    }
}
