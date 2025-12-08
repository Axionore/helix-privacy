package com.helix.netprivacy;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Objects;

/**
 * Minimal blocklist fetch/verify placeholder.
 * Fetches a blocklist payload and compares to expected signature/hash.
 * Real implementation should use signature verification with a pinned key.
 */
final class BlocklistManager {
    static final String DEFAULT_SOURCE = "https://blocklists.example/helix-dns.txt";

    String currentVersionHash = "";

    String fetchAndVerify(String sourceUrl, String expectedHash) throws Exception {
        Objects.requireNonNull(sourceUrl, "sourceUrl");
        URL url = new URL(sourceUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        conn.setInstanceFollowRedirects(false);
        if (conn.getResponseCode() != 200) {
            throw new IllegalStateException("Blocklist fetch failed: " + conn.getResponseCode());
        }
        byte[] data;
        try (InputStream in = conn.getInputStream()) {
            data = in.readAllBytes();
        }
        String hash = sha256Hex(data);
        if (!expectedHash.isEmpty() && !hash.equalsIgnoreCase(expectedHash)) {
            throw new SecurityException("Blocklist hash mismatch");
        }
        currentVersionHash = hash;
        // TODO: persist blocklist to disk and notify iptables/dns layer.
        return hash;
    }

    private static String sha256Hex(byte[] data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(data);
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
