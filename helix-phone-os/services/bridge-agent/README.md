# Bridge Agent (phone)

Phone-side agent that maintains the secure channel to Helix Desktop OS and exchanges policy-approved data.

## Responsibilities
- Establish mutual TLS with pinned device certs; support QR bootstrap for first-time pairing.
- Attach domain provenance tags and audit IDs to all messages.
- Enforce policy allowlist for remote actions (lock, wipe domain, rotate keys, pull audit summaries).
- Rate-limit and queue messages for offline-safe behavior.

## Message envelope (draft)
- `request_id`, `timestamp`
- `domain` (origin)
- `action` (e.g., `SYNC_POLICY_VERSION`, `REMOTE_LOCK`, `TRANSFER_BLOB`)
- `payload` (encrypted)
- `signature`/`mac`

## Failure handling
- If peer cert changes unexpectedly, drop channel and require re-bootstrap.
- Apply backoff on retries; fail closed on policy or signature errors.
- Log all actions to sealed audit log.
