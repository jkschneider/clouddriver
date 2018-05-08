package com.netflix.spinnaker.clouddriver.cloudfoundry.deploy.ops

import com.netflix.spinnaker.clouddriver.cloudfoundry.credentials.CloudFoundryCredentials
import spock.lang.Shared
import spock.lang.Specification

class AbstractCloudFoundryIntegSpec extends Specification {
  @Shared CloudFoundryCredentials credentials
  @Shared String apiHost
  @Shared String organization
  @Shared String space

  def setupSpec() {
    def props = new Properties()
    props.load(getClass().getResourceAsStream('/credentials.properties').with {
      assert it : 'add user, password, and apiHost to resources/credentials.properties'; it
    })

    credentials = new CloudFoundryCredentials(
      props.user.with { assert it : 'add user to resources/credentials.properties'; it } as String,
      props.password.with { assert it : 'add password to resources/credentials.properties'; it } as String
    )

    apiHost = props.apiHost.with { assert it : 'add apiHost to resources/credentials.properties'; it }
    organization = props.organization.with { assert it : 'add organization to resources/credentials.properties'; it }
    space = props.space.with { assert it : 'add space to resources/credentials.properties'; it }
  }
}
