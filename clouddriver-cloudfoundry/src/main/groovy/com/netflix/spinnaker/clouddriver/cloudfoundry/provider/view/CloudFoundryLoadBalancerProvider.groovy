package com.netflix.spinnaker.clouddriver.cloudfoundry.provider.view

import com.netflix.spinnaker.clouddriver.cloudfoundry.CloudFoundryCloudProvider
import com.netflix.spinnaker.clouddriver.cloudfoundry.model.CloudFoundryLoadBalancer
import com.netflix.spinnaker.clouddriver.model.LoadBalancerProvider
import org.springframework.stereotype.Component

@Component
class CloudFoundryLoadBalancerProvider implements LoadBalancerProvider<CloudFoundryLoadBalancer> {
  final String cloudProvider = CloudFoundryCloudProvider.ID
  private final CloudFoundryCloudProvider cloudFoundryCloudProvider

  @Override
  List<Item> list() {
    return null
  }

  @Override
  Item get(String name) {
    return null
  }

  @Override
  List<Details> byAccountAndRegionAndName(String account, String region, String name) {
    return null
  }

  @Override
  Set<CloudFoundryLoadBalancer> getApplicationLoadBalancers(String application) {
    return null
  }
}
