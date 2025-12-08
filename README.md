# Helix Privacy Suite (working name)

This repo now tracks the plan for a modern fork of HelixOS. The goal is to ship a secure, privacy-first stack inspired by the original HelixOS (Apache-2.0) while keeping branding, notices, and licensing clear.

## Origin and license
- Derived from HelixOS by TheCTO Technological Entrepreneurship & Consulting LTD (Apache-2.0).
- Retain the Apache 2.0 license text in every repo; keep and extend the NOTICE file with upstream attribution.
- Do not reuse original logos or trademarks without permission.

## Target repo layout
- `helix-phone-os`: modern Android 14/15 build with Helix policies, no-superuser model, and strong app separation.
- `helix-desktop-os`: hardened desktop (Qubes/Tails-inspired) with separate domains for personal/business/secure tasks.
- `helix-bridge`: phone ↔ desktop integration, secure transport, and shared policy state.
- `helix-docs`: architecture, PRDs, threat models, compliance notes, and onboarding.
- (Optional) `helixos-legacy`: fork of the original AOSP 5-based tree for reference only.

## What to include in each new repo
- Copy the Apache 2.0 `LICENSE` and extend `NOTICE` with upstream HelixOS credit plus your copyright.
- README section stating the project is derived from HelixOS (Apache-2.0) and independently maintained.
- Keep upstream file headers when copying code; add your own copyright line when you modify a file heavily.

## Quick start checklist
- [ ] Create a new org (e.g., `helix-privacy`) and initialize the four repos above.
- [ ] Add `LICENSE` (Apache-2.0) and `NOTICE` from this repo to each new repo.
- [ ] Add a “Heritage/Credits” section in every README.
- [ ] Stand up `helix-docs` with architecture and threat model stubs before major code changes.
- [ ] Keep original HelixOS as `helixos-legacy` for reference; do not build new releases on it.

## Next steps (suggested)
- Bootstrap `helix-docs` first so architecture and policy are documented.
- Set up CI lint/build jobs for each repo; ensure license/notice checks.
- Add build instructions for AOSP 14/15 and desktop OS as they come online.
