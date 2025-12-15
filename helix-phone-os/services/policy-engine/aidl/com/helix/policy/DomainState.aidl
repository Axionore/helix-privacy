package com.helix.policy;

/** Snapshot of domain configuration */
parcelable DomainState {
  int domain;            // domain id
  String policyVersion;  // applied policy version
  String[] allowedApps;  // package names permitted in this domain
  boolean networkAllowed;
  int egressMode;        // 0=Direct,1=VPN,2=Tor
}
