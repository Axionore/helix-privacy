# Domain Broker

Mediates cross-domain interactions (intents, clipboard, USB, files) under policy and user approval.

## Responsibilities
- Enforce default-deny for cross-domain requests.
- Check with Policy Engine for allow/deny/confirm outcome.
- Show system-level prompts for user confirmation when required.
- Tag data with provenance before sending to another domain or over the bridge.

## Interfaces (draft)
- Binder/AIDL `com.helix.domainbroker.IBroker`:
  - `requestShare(srcDomain, dstDomain, payloadMeta)` → `Decision`
  - `registerListener(listener)` for audit/notifications.
- Clipboard/USB hooks to intercept transfers and consult policy.

## Notes
- Keep broker surface minimal; all decisions logged via audit channel.
- Rate limit requests to avoid spam from malicious apps.
