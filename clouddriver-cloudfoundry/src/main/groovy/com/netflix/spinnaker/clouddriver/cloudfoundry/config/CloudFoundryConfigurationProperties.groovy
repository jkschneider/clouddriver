package com.netflix.spinnaker.clouddriver.cloudfoundry.config

import groovy.transform.ToString
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ToString(includeNames = true)
@ConfigurationProperties('cloudfoundry')
class CloudFoundryConfigurationProperties {
  List<ManagedAccount> accounts = []

  @ToString(includeNames = true, excludes = "password")
  static class ManagedAccount {
    String name
    String api
    String org
    String space
    String user
    String password
  }
}
