# Policy Model (Phone)

Defines how domains are described, enforced, and synchronized with desktop via the bridge.

## Domains
- `personal`: user-owned apps/data; Internet allowed; no access to business/secure secrets.
- `business`: managed apps/data; controlled Internet; clipboard/USB gated; audit required.
- `secure`: high isolation; no default Internet; only whitelisted readers; unidirectional egress if any.

## Policy bundle (draft fields)
- `version`: monotonic, used for rollback detection.
- `issued_at` / `expires_at`: validity window.
- `signing_key_id`: fleet admin key identifier.
- `domains`: per-domain defaults:
  - `apps_allowed`, `apps_blocked`
  - `network`: allowed hosts/categories; VPN requirement; DNS profile; tracker blocklists; Tor requirement allowed on secure domain.
  - `storage`: backup allowed? external storage? encryption profile.
  - `clipboard` / `usb`: allow/deny/confirm rules and logging.
- `cross_domain_rules`: allowlist of source→dest actions with prompts/logging.
- `network_privacy`: global controls:
  - `vpn`: require Helix VPN for personal/business; disallow disabling if enforced.
  - `tor`: optional per-domain egress via Tor (likely secure domain only); allowlist for services that must bypass.
  - `tracker_blocklists`: versions and sources for DNS/HTTP blocking; policy controls updates and exceptions.
  - `telemetry_block`: deny known analytics endpoints; require explicit allowlist for any telemetry.
- `bridge`: allowed remote actions, rate limits, approval requirements.
- `auditing`: log destinations, retention, sealing parameters.

## Enforcement points
- Package manager hooks for install/launch per domain.
- SELinux policies and per-user IDs bound to domain contexts.
- Domain Broker mediating intents/clipboard/USB across domains.
- Bridge Agent enforcing domain provenance and request allowlist.
- System UI prompts tied to policy decisions for transparency.

## Sync and conflict handling
- Bridge exchanges only the minimal state: policy version, domain map, pending approvals.
- Desktop mirrors the policy but phone is source of truth for domain identity.
- If policy signature invalid or rollback detected: freeze high-risk actions and alert user/admin.
