# Blockchain Use (anchoring only)

Scope for using blockchain/ledger in Helix Phone OS. Content is never stored on-chain; only hashed proofs.

## Use cases
- Messaging delivery receipts: anchor hashed, blinded receipts to provide verifiable delivery without revealing content.
- Audit/policy checkpoints: periodic hash of policy version + device attestation state.

## Policy controls
- Per-domain enable/disable; secure domain may opt out entirely.
- Choose endpoint: public chain vs consortium/enterprise chain.
- Batching: define max batch size/time to reduce fees and metadata leakage.

## Privacy constraints
- No PII or message content on-chain; use salted hashes.
- Anchor via dedicated service account; rotate keys; sign locally before submit.
- Offline queueing with retry; fail according to policy (block vs log-only).

## To do
- Select ledger client and chain(s).
- Define AIDL for blockchain anchoring service.
- Add inclusion proof verification for audit UI.
