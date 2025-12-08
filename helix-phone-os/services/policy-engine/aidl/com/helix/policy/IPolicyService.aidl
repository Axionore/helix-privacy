package com.helix.policy;

import com.helix.policy.ActionRequest;
import com.helix.policy.Decision;
import com.helix.policy.DomainState;

interface IPolicyService {
  String getPolicyVersion();
  DomainState getDomainState(int domainId);
  Decision requestCrossDomainAction(in ActionRequest request);
}
