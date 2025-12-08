# Policy Engine (phone)

Core service that ingests signed policy bundles and programs domain enforcement.

## Responsibilities
- Verify policy bundle signatures, validity window, and version (rollback protection).
- Program per-domain defaults: allowed apps, network profiles, clipboard/USB rules, storage options.
- Expose a narrow binder/AIDL API for:
  - reading current policy version and provenance,
  - requesting cross-domain actions (with user prompt),
  - emitting audit events for decisions.
- Persist last-known-good policy in secure storage; refuse to run if boot state is unverified.

## Interfaces (draft)
- AIDL service `com.helix.policy.IPolicyService` with:
  - `getPolicyVersion()`
  - `requestCrossDomainAction(request)` → `Decision`
  - `getDomainState(domain)` → state summary
- Broadcasts for policy updates and emergency lock events.

## Storage and keys
- Use hardware-backed keystore; bind policy signing keys and audit sealing keys.
- Cache policy bundle with hash; include rollback counter tied to bootloader index.

## To do
- Define AIDL/parcelables for requests and decisions.
- Integrate with Domain Broker and Bridge Agent for cross-domain and remote actions.
