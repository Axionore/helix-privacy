# System Apps

Lightweight, policy-aware system apps shipped with Helix Phone OS.

## Planned apps
- **Helix Settings**: policy-aware settings surface; hides disallowed toggles; exposes policy version/state.
- **Approvals UI**: prompts for cross-domain actions and remote admin requests; records decisions.
- **Attestation/Audit Viewer**: shows device/boot/policy status and recent audit events.

## Guidelines
- Keep app permissions minimal; rely on Policy Engine and Domain Broker for enforcement.
- Use clear UX for domain context (color/iconography) to avoid confusion.
- Ensure all user decisions are logged with audit IDs.
