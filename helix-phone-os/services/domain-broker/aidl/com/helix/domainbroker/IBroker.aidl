package com.helix.domainbroker;

import com.helix.domainbroker.ShareRequest;
import com.helix.domainbroker.Decision;

interface IBroker {
  Decision requestShare(in ShareRequest request);
}
