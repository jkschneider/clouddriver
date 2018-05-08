package com.netflix.spinnaker.clouddriver.cloudfoundry.deploy.description

import com.fasterxml.jackson.annotation.JsonIgnore
import com.netflix.spinnaker.clouddriver.cloudfoundry.credentials.CloudFoundryCredentials

abstract class AbstractCloudFoundryDescription {
  @JsonIgnore
  CloudFoundryCredentials credentials

  String apiHost
  String organization
  String space
}
