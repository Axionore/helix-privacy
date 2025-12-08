# Network Status UI (minimal)

System UI surface to indicate current egress path per domain (Direct/VPN/Tor) and blocklist status.

## Requirements
- Read-only display; no user override when policy-enforced.
- Shows per-domain mode (color/icon): Direct (grey), VPN (blue), Tor (purple/green), plus warning on fail-closed state.
- Display blocklist version/hash and last update time from NetPrivacy service.
- Tapping opens Helix Settings pane for more detail.

## Integration
- Bind to INetPrivacy to read mode and blocklist version.
- React to broadcasts for path changes (VPN down, Tor down, fail-closed).
- No analytics/telemetry; no third-party SDKs.
