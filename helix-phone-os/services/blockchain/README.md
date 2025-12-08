# Blockchain Anchoring Service

Provides optional anchoring of audit checkpoints and messaging delivery receipts to a ledger. No sensitive content is stored on-chain; only hashes and minimal metadata.

## Responsibilities
- Batch and publish hashed delivery receipts from Helix Messenger (optional per policy/domain).
- Anchor policy/audit checkpoints periodically (e.g., policy version, device attestation state hash).
- Verify chain inclusion proofs for local audit UI.
- Respect policy: allow/disable per domain; choose ledger endpoint (public vs consortium).

## Interfaces (draft)
- Binder/AIDL `com.helix.blockchain.IAnchoring` (to define):
  - `anchorReceipt(hash, domain, timestamp)`
  - `anchorCheckpoint(hash, label)`
  - `getStatus()` (last anchor, chain height, endpoint)

## Privacy/operational notes
- Never put PII or message content on-chain; only salted hashes.
- Use batching to reduce fees and metadata leakage.
- Support offline queueing; fail closed if policy requires anchoring but endpoint unreachable.

## To do
- Define AIDL and message formats.
- Choose ledger(s) and client library strategy.
- Add signature/nonce scheme to prevent replay and prove device origin.
