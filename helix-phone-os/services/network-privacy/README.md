# Network Privacy Service

Manages per-domain outbound routing (Direct / Helix VPN / Tor) and tracker blocking.

## Responsibilities
- Apply per-domain routing according to policy: force Helix VPN for personal/business, optionally Tor for secure.
- Maintain VPN/Tor daemon lifecycle; monitor health and fail closed when required paths are unavailable.
- Enforce DNS/HTTP blocklists for trackers/telemetry; update lists only from signed sources.
- Expose minimal binder API to report state to System UI; no user override when policy-locked.

## Interfaces (draft)
- Binder/AIDL `com.helix.netprivacy.INetPrivacy`:
  - `getDomainEgressMode(domainId)` → mode enum (DIRECT/VPN/TOR)
  - `getBlocklistVersion()` → string/hash
  - `isCompliant()` → bool + reason
- Broadcasts for path changes (e.g., VPN down → block traffic if required).

## Policy hooks
- Reads `network_privacy` from Policy Engine: VPN required, Tor allowed, tracker blocklist versions, exceptions.
- Reports compliance to Policy Engine for audit.

## To do
- Define egress mode enum AIDL and INetPrivacy interface.
- Add blocklist update mechanism with signature verification. (stub: `BlocklistManager`)
- Integrate with system firewall/iptables to enforce fail-closed semantics. (stub hooks in `NetPrivacyService`)
- Wire to Policy Engine (read required mode per domain) and expose compliance to System UI.
