package com.helix.netprivacy;

/** Egress mode for a domain */
parcelable EgressMode {
  int mode; // 0=DIRECT, 1=VPN, 2=TOR
  String reason;
}
