package com.helix.policy;

/** Decision for policy-mediated requests */
parcelable Decision {
  int result;       // 0=allow,1=deny,2=prompt
  String reason;    // human-readable reason or policy ref
  String auditId;   // audit log correlation id
}
