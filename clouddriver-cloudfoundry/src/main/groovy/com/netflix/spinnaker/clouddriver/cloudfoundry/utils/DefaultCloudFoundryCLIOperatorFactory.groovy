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

package com.netflix.spinnaker.clouddriver.cloudfoundry.utils

import com.netflix.spinnaker.clouddriver.cloudfoundry.deploy.description.AbstractCloudFoundryDescription
import org.cloudfoundry.operations.DefaultCloudFoundryOperations
import org.cloudfoundry.reactor.DefaultConnectionContext
import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient

class DefaultCloudFoundryCLIOperatorFactory implements CloudFoundryCLIOperatorFactory{
  DefaultCloudFoundryOperations createCloudFoundryCLIOperator(AbstractCloudFoundryDescription description){

    def context = DefaultConnectionContext.builder()
      .apiHost(description.apiHost)
      .skipSslValidation(true)
      .build()

    def client = ReactorCloudFoundryClient.builder()
      .connectionContext(context)
      .tokenProvider(description.credentials.credentials)
      .build()

    DefaultCloudFoundryOperations.builder()
      .cloudFoundryClient(client)
      .organization(description.organization)
      .space(description.organization)
      .build()
  }
}
