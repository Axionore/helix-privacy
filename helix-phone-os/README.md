# Helix Phone OS

Modern Android 14/15-based fork inspired by HelixOS (Apache-2.0). Focus: no-superuser security model, strict app separation, and policy-driven UX suitable for enterprise and privacy-first users.

## Scope
- Hardened AOSP 14/15 base with Helix policy engine, domain-aware services, and locked-down debug surface.
- Clear business/personal/secure domains; minimal cross-domain sharing by default.
- Integration hooks for the bridge service to sync policy state with desktop.

## Getting started
- Copy `../LICENSE` and `../NOTICE` into this repo before first commit.
- Add a README section that this is derived from HelixOS by TheCTO (Apache-2.0) and independently maintained.
- Keep upstream file headers when importing code; add your copyright when heavily modifying files.

## Repository layout (planned)
- `build/` AOSP build scripts and manifests.
- `services/` Domain engine, policy service, bridge agent.
- `apps/` System apps aligned to the policy model.
- `docs/` Build instructions, threat model, compliance notes.
