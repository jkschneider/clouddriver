package com.netflix.spinnaker.clouddriver.cloudfoundry.model

import com.netflix.spinnaker.clouddriver.cloudfoundry.CloudFoundryCloudProvider
import com.netflix.spinnaker.clouddriver.model.LoadBalancer
import com.netflix.spinnaker.clouddriver.model.LoadBalancerServerGroup
import groovy.transform.CompileStatic


@CompileStatic
class CloudFoundryLoadBalancer implements LoadBalancer {
  String name
  final String type = CloudFoundryCloudProvider.ID
  final String cloudProvider = CloudFoundryCloudProvider.ID
  String account
  Set<LoadBalancerServerGroup> serverGroups = new HashSet<>()
}
