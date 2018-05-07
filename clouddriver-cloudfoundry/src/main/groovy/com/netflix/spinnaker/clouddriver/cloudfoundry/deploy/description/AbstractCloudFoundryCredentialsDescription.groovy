package com.netflix.spinnaker.clouddriver.cloudfoundry.deploy.description

import com.fasterxml.jackson.annotation.JsonIgnore
import com.netflix.spinnaker.clouddriver.cloudfoundry.credentials.CloudFoundryCredentials

//TODO should this be called Abstract??
class AbstractCloudFoundryCredentialsDescription {
    @JsonIgnore
    CloudFoundryCredentials credentials

    String apiHost
}
