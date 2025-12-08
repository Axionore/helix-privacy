# Bridge Message Schema (draft)

Envelope fields (all required unless noted):
- `request_id` (string): unique per message for audit correlation.
- `timestamp` (int64): ms since epoch; validate replay window.
- `domain` (string): source domain id/name.
- `action` (string): e.g., `SYNC_POLICY_VERSION`, `REMOTE_LOCK`, `TRANSFER_BLOB`, `AUDIT_PUSH`.
- `payload` (bytes): encrypted content; schema depends on action.
- `policy_version` (string): current policy version on sender.
- `signature`/`mac` (bytes): integrity/auth using device-bound keys.

Action payload sketches:
- `SYNC_POLICY_VERSION`: { `policy_version`, `device_state` }
- `REMOTE_LOCK`: { `target_domain`, `reason` }
- `TRANSFER_BLOB`: { `blob_type`, `size`, `meta`, `chunk_id`?, `total_chunks`? }
- `AUDIT_PUSH`: { `entries`: [ { `audit_id`, `event`, `ts`, `result` } ] }

Error handling:
- Include `error` field on responses with `code` and `message`.
- Drop channel on signature or provenance failure; require re-bootstrap.
