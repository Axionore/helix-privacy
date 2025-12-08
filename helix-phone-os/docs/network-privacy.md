# Network Privacy (VPN/Tor)

Plan for built-in privacy networking: Helix VPN and optional Tor routing with tracker blocking.

## Goals
- Enforce per-domain outbound policy: direct / Helix VPN / Tor.
- Block trackers/telemetry by default; allow exceptions only via signed policy.
- Provide minimal, transparent UI to show active path and allowlist state.

## Components
- **Network Privacy Service (NPS)**: controls routing per domain, manages VPN/Tor daemons, enforces blocklists.
- **Policy inputs**: `network_privacy` fields (VPN required, Tor allowed, blocklist versions).
- **UI**: small surface in Helix Settings to show active path (Direct/VPN/Tor) and blocklist status; no user override if policy-locked.

## Behavior
- Personal/business: default to Helix VPN; direct egress only if policy allows; block known trackers/telemetry domains.
- Secure: option to force Tor; no direct egress unless explicitly allowed; stricter blocklists.
- Bridge traffic: stays on mTLS bridge channel; not routed through Tor/VPN.
- Fail closed: if VPN/Tor not available when required, block egress and notify user/admin.

## Blocklists
- DNS + HTTP(S) blocklists; versions signed/hashed; updates controlled by policy.
- Local cache with rollback protection; audit when lists update or are overridden by policy.

## To do
- Define AIDL between NPS and Policy Engine for per-domain routing directives.
- Add blocklist update source and signature format.
- Implement health checks for VPN/Tor daemons and fail-closed logic.
