# Architecture

System view across phone, desktop, and bridge with the policy engine as the control plane.

## High-level layout
```
           +------------------+          +------------------+
           |  Helix Phone OS  | <------> | Helix Desktop OS |
           | (Android 14/15)  |   Bridge |  (Domainized)    |
           +------------------+          +------------------+
                    ^                           ^
                    |                           |
                Cloud-less, peer-verified policy sync
```

### Helix Phone OS (Android 14/15 base)
- AOSP 14/15, hardened SELinux, no superuser path; verified boot and rollback protection required.
- Domains: `personal`, `business`, `secure`; IPC and storage scoped per domain; crossing requires policy grant + user approval.
- Services:
  - Policy Engine (PE): consumes signed policy bundles, enforces per-domain defaults, exports minimal state to bridge.
  - Domain Services: app/domain registry, per-domain keystore binding, cross-domain broker with least-privilege intents.
  - Bridge Agent (phone side): maintains secure channel to desktop, handles provenance tags, rate-limited remote actions.
- Data:
  - Keys bound to hardware-backed keystore; per-domain storage contexts; audit log with cryptographic sealing.

### Helix Desktop OS
- Domainized desktop (Qubes/Tails-inspired): separate VMs/containers for `personal`, `business`, `secure`.
- Minimal trusted base (dom0-equivalent) runs UI compositor, update agent, and bridge agent.
- Policies mirrored from phone; desktop-side PE validates signatures and enforces domain routing and clipboard/USB rules.
- Bridge Agent (desktop side): terminates the secure channel, maps phone domains to desktop domains, logs actions.

### Helix Bridge (protocol + agents)
- Mutually authenticated channel (device-bound keys, pinning of peer certs, optional QR bootstrap).
- Message envelope: signed + encrypted; includes domain provenance, policy version, replay window, and audit token.
- Remote actions (e.g., wipe business domain, rotate keys) require policy allowance + user confirmation unless marked emergency.

## Data flows (examples)
- Personal app → Business app: blocked by default; allowed only via broker with explicit policy and user prompt.
- Business domain → Desktop business: allowed over bridge if policy permits; data tagged with provenance and logged on both ends.
- Secure domain ↔ any: default deny; only whitelisted channels (e.g., read-only doc viewer) with unidirectional flow.
- Updates: phone and desktop validate release signatures, verify rollback index, log to sealed audit store.

## Trust anchors and keys
- Device keys provisioned at enrollment; pinned peer certs for bridge.
- Policy bundles signed by fleet admin key; PE rejects unsigned/expired/rolled-back policies.
- Audit logs sealed with hardware-bound keys to detect tampering.

## Next steps
- Add component diagrams (source → /docs/architecture/diagrams).
- Define bridge message schemas and state machine.
- Document storage layouts per domain (phone and desktop).
