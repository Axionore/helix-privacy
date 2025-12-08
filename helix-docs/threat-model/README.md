# Threat Model

Coverage for phone, desktop, and bridge using STRIDE framing.

## Assets
- User data (personal, business, secure domains), credentials, keys, policy bundles, audit logs, software supply chain (boot/release).

## Attacker profiles
- Device thief with physical access.
- Malicious app within a domain trying to escalate or exfiltrate.
- Network adversary intercepting bridge traffic.
- Admin/insider attempting policy tamper.
- Supply-chain attacker inserting malicious updates.

## Boundaries
- Per-domain isolation on phone (SELinux, per-user IDs, storage contexts).
- Desktop domain boundaries (VM/container isolation, minimal dom0).
- Bridge channel boundary (mutual auth, encryption, replay protection).
- Update/boot chain boundary (secure/verified boot, rollback counters).

## STRIDE snapshot (phone)
- Spoofing: device-bound keys + attestation; origin-tagged IPC; app identity checks.
- Tampering: dm-verity/AVB; per-domain storage isolation; sealed audit logs.
- Repudiation: signed audit events; synchronized clocks; append-only logs.
- Information disclosure: domain-scoped storage/keys; default-deny cross-domain; brokered sharing with user approval.
- Denial of service: rate limits on brokers/bridge; watchdogs; safe-mode recovery.
- Elevation of privilege: no superuser path; SELinux enforcing; minimal debug; permission minimization.

## STRIDE snapshot (desktop)
- Spoofing: per-domain identity + attested bridge peer; admin actions gated by policy.
- Tampering: read-only base images; measured boot where available; sealed audit; controlled clipboard/USB routing.
- Repudiation: signed audit per domain and dom0-equivalent.
- Information disclosure: domain networking and filesystem separation; policy-driven data egress paths.
- Denial of service: resource caps per domain; update rollback limits; recovery console.
- Elevation of privilege: minimal dom0 surface; signed updates; restricted admin tools.

## STRIDE snapshot (bridge)
- Spoofing: mutual TLS with pinned device certs; optional QR bootstrap.
- Tampering: signed envelopes; integrity-checked commands; monotonic counters to prevent replay.
- Repudiation: dual-sided audit with request/response IDs.
- Information disclosure: E2E encryption; per-domain provenance tagging; least-data exchange.
- Denial of service: rate limits; backoff; channel reset; offline-safe behavior.
- Elevation of privilege: command allowlist; user confirmation for sensitive actions unless emergency policy flag.

## Mitigations to implement/test
- Enforce hardware-backed keystore binding and rollback counters on phone and desktop.
- Require signed policy bundles; reject on version rollback or expired signatures.
- Enforce default-deny cross-domain rules; UI prompts for brokered flows.
- Seal audit logs and export hashes for external verification.
- Bridge channel: mTLS with pinned certs, replay window, request/response IDs.
- Supply chain: signed releases, reproducible build targets where feasible, SBOMs.

## Residual risks and notes
- Side channels across domains (CPU/cache/ML co-tenancy) need ongoing review and targeted mitigations.
- Physical extraction attacks depend on hardware security strength; document device matrix.
- Usability pressure may push for exceptions; ensure exceptions are explicit, logged, and reversible.
