# Helix Messenger (default)

Privacy-first system messaging app. Uses end-to-end encryption with minimal metadata and optional blockchain anchoring for delivery receipts.

## Principles
- E2EE by default; no server-side content storage beyond delivery queues.
- Minimal metadata: sender/receiver keys and timestamps only; padding for traffic shaping where possible.
- Tracker-free: no analytics SDKs, no third-party push beyond FCM/APNs equivalents; prefer self-hosted push when available.
- Optional ledger anchoring: store hashed delivery receipts on a blockchain for verifiable delivery/non-repudiation; never store message content.

## Features (planned)
- Domain-aware profiles (personal/business/secure) with separate keys.
- Contact verification (QR/short-auth string).
- Offline-friendly with deferred sync; bridge-aware so desktop can pair with matching domain keys.
- Media handling obeys domain policies (no cross-domain saves unless allowed).

## Blockchain use
- Publish hashed, blinded delivery receipts to a ledger (public or consortium per policy).
- Anchor audit checkpoints (policy version, device attest status) periodically.
- Opt-in per policy/domain; secure domain may disable anchoring entirely.

## To do
- Define key management per domain.
- Pick ledger network (public vs consortium) and transaction batching strategy.
- Add UI affordances for verification and ledger status.
