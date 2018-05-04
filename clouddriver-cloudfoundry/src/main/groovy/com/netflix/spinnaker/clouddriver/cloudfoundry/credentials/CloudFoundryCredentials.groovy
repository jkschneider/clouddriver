package com.netflix.spinnaker.clouddriver.cloudfoundry.credentials

import com.netflix.spinnaker.clouddriver.security.AccountCredentials
import com.netflix.spinnaker.fiat.model.resources.Permissions
import org.cloudfoundry.reactor.TokenProvider

class CloudFoundryCredentials implements AccountCredentials<TokenProvider> {
  private static final String CLOUD_PROVIDER = "cloudfoundry";

  final String name
  final String environment
  final String accountType
  final List<String> requiredGroupMembership = Collections.emptyList()

  CloudFoundryCredentials(String name, String environment, String accountType) {
    this.name = name
    this.environment = environment
    this.accountType = accountType
  }

  @Override
  TokenProvider getCredentials() {
    return null
  }

  @Override
  String getCloudProvider() {
    return CLOUD_PROVIDER
  }

  @Override
  Permissions getPermissions() {
    return super.getPermissions()
  }
}
