# Helix Phone OS Architecture

Phone-focused view of the Helix stack on AOSP 14/15 with domain separation and the bridge agent.

## Components
- **Policy Engine (PE)**: consumes signed policy bundles, programs per-domain defaults (apps, networking, clipboard, USB), and exposes minimal state for the bridge.
- **Domain Services**: track domains (`personal`, `business`, `secure`), bind app IDs and storage contexts, and broker cross-domain intents under policy and user approval.
- **Bridge Agent (phone)**: maintains the mutually authenticated channel to the desktop bridge, exchanges provenance-tagged messages, and handles remote actions (e.g., lock business domain).
- **System Apps**: Helix Settings, Attestation UI, Approvals UI; kept minimal and tied to policy surfaces.
- **Update Pipeline**: verified boot, AVB/rollback counters, signed OTAs; attest policy/bridge keys at boot.

## Data paths (phone)
- **Domain-local**: apps stay within domain storage and keystore; default Internet egress profile per domain.
- **Cross-domain**: default deny; requests go through the Domain Broker with policy check + user prompt.
- **Bridge**: policy-approved flows to desktop; provenance tags and audit IDs included; rate limited.
- **Updates**: signed images; OTA applied only if signature and rollback index validate; audit logged.

## Boot and trust
- Hardware-backed keystore; device-bound keys for policy/bridge auth.
- Verified boot with rollback protection; PE refuses to run if boot state is bad.
- Policy bundles signed by fleet key; cached last-known-good bundle in secure storage.

## To do
- Finalize binder/HIDL/AIDL surfaces for PE, Domain Broker, and Bridge Agent.
- Define storage layout per domain (data, media, keys).
- Add diagrams once interfaces are fixed (see `/helix-docs/architecture` for global view).
