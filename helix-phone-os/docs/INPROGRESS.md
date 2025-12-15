# In-Progress Items (Helix Phone OS)

Tracked tasks remaining for network privacy, policy, and app anchoring work.

- Replace sample RSA key/hash/signature in blocklist handling with production values; enforce signature verification and integrate DNS/HTTP filtering using the cached blocklist.
- Extend Policy Engine to populate `DomainState.egressMode` from policy bundles; use that in NetPrivacy routing instead of defaults.
- Implement actual VPN/Tor lifecycle, health checks, per-domain routing, and fail-closed firewall/iptables/eBPF enforcement.
- Enhance NetStatus UI with better visuals, per-domain state from real broadcasts, and removal of placeholder text.
- Build out Helix Messenger (E2EE, domain separation, no tracking) and the Blockchain Anchoring service (ledger client, batching, inclusion proofs).
