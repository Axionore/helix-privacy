# Helix Bridge

Secure link between Helix Phone OS and Helix Desktop OS. Responsible for policy-aware sync, remote actions, and tightly scoped data transfer. Derived from HelixOS ideas (Apache-2.0).

## Scope
- Transport that enforces domain boundaries and provenance checks.
- Policy exchange and state sync between phone and desktop.
- Minimal UI for approvals and auditing.

## Getting started
- Copy `../LICENSE` and `../NOTICE` into this repo before first commit.
- Include heritage/credits in README and keep Apache 2.0 headers on imported code.
- Avoid using legacy HelixOS branding assets.

## Repository layout (planned)
- `agents/` Phone and desktop agents.
- `protocol/` Message formats, schemas, signing/encryption strategy.
- `ui/` Approval and audit surface.
- `docs/` Integration guide and threat model.
