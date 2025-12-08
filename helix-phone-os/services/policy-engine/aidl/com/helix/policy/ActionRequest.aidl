package com.helix.policy;

/** Cross-domain action request */
parcelable ActionRequest {
  int srcDomain;        // e.g., 0=personal,1=business,2=secure
  int dstDomain;        // destination domain
  String actionType;    // e.g., "INTENT_SHARE", "CLIPBOARD", "FILE"
  String payloadMeta;   // metadata only (no bulk data)
  boolean requiresUserPrompt;
  String requestId;     // for audit correlation
}
