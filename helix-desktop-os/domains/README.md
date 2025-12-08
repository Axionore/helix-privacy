# Domain Layout (desktop)

Defines desktop domain types and policy surface; intended to map 1:1 with phone domains via the bridge.

## Domain types
- `personal`: user-owned apps/data, default Internet egress, no access to business/secure secrets.
- `business`: managed apps/data, controlled Internet egress, audited clipboard/USB to personal, no access to secure.
- `secure`: highest isolation, no default Internet, only whitelisted peripherals, unidirectional view-only channels out.

## Isolation model
- Domains run as separate VMs/containers; minimal dom0 controls UI, updates, and bridge agent.
- Storage per domain; no shared writable mounts; clipboard/USB/printer routed via policy broker.
- Networking per domain with firewall profiles; DNS separated.

## Bridge mapping
- Phone domain ↔ matching desktop domain enforced by bridge provenance tags.
- Cross-domain copy/share requests go through policy broker with logging and user prompts.
- Remote admin actions (wipe, lock, rotate keys) land in dom0-equivalent and are applied per domain.

## To-do
- Define base images/templates per domain.
- Document backup/restore per domain.
- Add policy schemas for clipboard/USB/network rules.
