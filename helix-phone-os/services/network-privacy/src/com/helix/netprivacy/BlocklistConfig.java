package com.helix.netprivacy;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Loads blocklist source configuration from JSON.
 */
final class BlocklistConfig {
    private static final String TAG = "HelixBlocklistConfig";
    private static final String CONFIG_PATH = "/system/etc/helix/blocklists/sources.json";

    static class Source {
        final String name;
        final String url;
        final String expectedHash;
        final String signatureB64;

        Source(String name, String url, String expectedHash, String signatureB64) {
            this.name = name;
            this.url = url;
            this.expectedHash = expectedHash;
            this.signatureB64 = signatureB64;
        }
    }

    Source load() {
        try {
            byte[] data = Files.readAllBytes(Paths.get(CONFIG_PATH));
            JSONObject root = new JSONObject(new String(data));
            JSONArray sources = root.getJSONArray("sources");
            if (sources.length() == 0) return null;
            JSONObject s = sources.getJSONObject(0);
            return new Source(
                    s.optString("name", "default"),
                    s.getString("url"),
                    s.optString("expected_hash", ""),
                    s.optString("signature_b64", "")
            );
        } catch (Exception e) {
            Log.w(TAG, "Failed to load blocklist config, using defaults", e);
            return new Source("default", BlocklistManager.DEFAULT_SOURCE, "", "");
        }
    }
}
