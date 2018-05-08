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

package com.netflix.spinnaker.clouddriver.cloudfoundry

import spock.lang.Specification

class CloudFoundryCloudProviderSpec extends Specification {

  void "check basics"() {
    when:
    def provider = new CloudFoundryCloudProvider()

    then:
    provider.displayName == 'Cloud Foundry'
    provider.id == 'cloudfoundry'
    provider.operationAnnotationType == CloudFoundryOperation
  }

}
