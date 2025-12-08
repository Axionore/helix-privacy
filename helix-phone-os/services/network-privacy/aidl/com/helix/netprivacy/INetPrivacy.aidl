package com.helix.netprivacy;

import com.helix.netprivacy.EgressMode;

interface INetPrivacy {
  EgressMode getDomainEgressMode(int domainId);
  String getBlocklistVersion();
  boolean isCompliant();
}
