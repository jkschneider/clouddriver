/*
 * Copyright 2018 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.spinnaker.clouddriver.cloudfoundry.utils

import com.netflix.spinnaker.clouddriver.cloudfoundry.credentials.CloudFoundryCredentials
import com.netflix.spinnaker.clouddriver.cloudfoundry.deploy.description.AbstractCloudFoundryCredentialsDescription
import org.cloudfoundry.operations.DefaultCloudFoundryOperations
import spock.lang.Specification

class DefaultCloudFoundryCLIOperatorFactorySpec extends Specification {
  def '#createCloudFoundryCLIOperator uses credentials passed in the description' (){
    given:
    def mockDescription = Mock(AbstractCloudFoundryCredentialsDescription)
    def credentials = new CloudFoundryCredentials("someFooName","somePassword")
    def cloudfoundryClientFactory = new DefaultCloudFoundryCLIOperatorFactory()
    mockDescription.apiHost >> "api.local.pcfdev.io"
    mockDescription.credentials >> credentials
    when:
    def result = cloudfoundryClientFactory.createCloudFoundryCLIOperator(mockDescription)
    then:
    result instanceof DefaultCloudFoundryOperations
  }
}
