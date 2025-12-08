package com.helix.domainbroker;

/** Brokered share request between domains */
parcelable ShareRequest {
  int srcDomain;
  int dstDomain;
  String payloadMeta;     // description only, not bulk data
  String channel;         // e.g., "INTENT", "CLIPBOARD", "USB"
  String requestId;       // audit correlation
}
