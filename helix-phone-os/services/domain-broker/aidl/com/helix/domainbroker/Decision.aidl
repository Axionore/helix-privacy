package com.helix.domainbroker;

/** Decision result returned by the broker */
parcelable Decision {
  int result;     // 0=allow,1=deny,2=prompt
  String reason;
  String auditId;
}
