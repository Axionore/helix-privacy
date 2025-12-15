package com.helix.netprivacy;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Objects;

/**
 * Minimal blocklist fetch/verify placeholder.
 * Fetches a blocklist payload and compares to expected signature/hash.
 * Real implementation should use signature verification with a pinned key.
 */
final class BlocklistManager {
    static final String DEFAULT_SOURCE = "https://blocklists.example/helix-dns.txt";
    // Test pinned key (RSA 2048, example only; replace with production key)
    private static final String PINNED_PUBKEY_B64 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwMl4ZQ16tGRzVru5vZm5Xxn9jQgd6DmxhPxH4X8lN4mA5c8pr3ycXd7GuFAH4vODTDwWEOvVYpUfZyI0OrJD4F5LwWNNPdLZ1omFfco37p44nr7g4g1p1yk2Vkydf4WvF5A8lC0F6nCWhw1OlP5zjz3lpoDNyfS+mUOVMRWAc6gEoYJXg+1ts8y9x8gRJgvP34X1dMrOYvC9SKeqGJQtl0SP/FBxEM86YqX6qfJ4QVeQ2mKpY+2KPMLZ+UE7XrWQiUJnZ7rKVzIE7KxJ2O7aA6MZmRrOkeUxq13zsAN5mKDzdwJ7ClGa5VhqNg+XppDngbnhkwIDAQAB";
    private static final String CACHE_PATH = "/data/misc/helix/blocklists/helix-dns.txt";

    String currentVersionHash = "";

    String fetchAndVerify(String sourceUrl, String expectedHash, String signatureB64) throws Exception {
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
        if (!signatureB64.isEmpty() && !verifySignature(data, Base64.getDecoder().decode(signatureB64))) {
            throw new SecurityException("Blocklist signature verification failed");
        }
        currentVersionHash = hash;
        persist(data);
        applyFilters();
        return hash;
    }

    boolean verifySignature(byte[] data, byte[] signature) throws Exception {
        if (signature == null || signature.length == 0) return false;
        byte[] pub = Base64.getDecoder().decode(PINNED_PUBKEY_B64);
        PublicKey key = java.security.KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(pub));
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(key);
        sig.update(data);
        return sig.verify(signature);
    }

    private void persist(byte[] data) {
        try {
            java.nio.file.Files.createDirectories(java.nio.file.Paths.get("/data/misc/helix/blocklists"));
            java.nio.file.Files.write(java.nio.file.Paths.get(CACHE_PATH), data);
        } catch (Exception e) {
            // Best effort persistence; log and continue.
            android.util.Log.w("HelixBlocklist", "Persist blocklist failed", e);
        }
    }

    private void applyFilters() {
        // TODO: hook into DNS/HTTP filtering layer using the cached blocklist.
        android.util.Log.i("HelixBlocklist", "Applying DNS/HTTP filters from cache (stub)");
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
