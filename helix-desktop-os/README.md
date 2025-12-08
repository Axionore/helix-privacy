# Helix Desktop OS

Hardened desktop OS inspired by Qubes/Tails patterns, derived from HelixOS concepts (Apache-2.0). Designed to pair with Helix Phone OS through the bridge for unified policy and data handling.

## Scope
- Domain-separated desktop (personal/business/secure) with strong isolation boundaries.
- Minimal trusted UI surface; audited update and attestation story.
- Bridge-aware agent to sync policies and allow controlled data flows with the phone.

## Getting started
- Copy `../LICENSE` and `../NOTICE` into this repo before first commit.
- Keep Apache 2.0 headers on imported files; add your copyright when you modify.
- Document heritage and credits in the README and NOTICE.

## Repository layout (planned)
- `os/` Base distro configs, installer assets, secure boot chain.
- `domains/` Templates and policies for each domain type.
- `bridge/` Desktop-side bridge agent and UI.
- `docs/` Architecture, threat model, operational guidance.
