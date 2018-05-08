/*
 * Copyright 2018 Pivotal Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
