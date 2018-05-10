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

package com.netflix.spinnaker.clouddriver.cloudfoundry

import com.netflix.spinnaker.cats.provider.ProviderSynchronizerTypeWrapper
import com.netflix.spinnaker.clouddriver.cloudfoundry.config.CloudFoundryConfigurationProperties
import com.netflix.spinnaker.clouddriver.cloudfoundry.provider.CloudFoundryProvider
import com.netflix.spinnaker.clouddriver.cloudfoundry.security.CloudFoundryCredentialsInitializer
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@EnableConfigurationProperties
@EnableScheduling
@ConditionalOnProperty('cloudfoundry.enabled')
@ComponentScan(['com.netflix.spinnaker.clouddriver.cloudfoundry'])
class CloudFoundryConfiguration {

  @Bean
  CloudFoundryConfigurationProperties cloudFoundryConfigurationProperties (){
    new CloudFoundryConfigurationProperties()
  }

  @Bean
  CloudFoundrySynchronizerTypeWrapper cloudFoundrySynchronizerTypeWrapper() {
    new CloudFoundrySynchronizerTypeWrapper()
  }

  class CloudFoundrySynchronizerTypeWrapper implements ProviderSynchronizerTypeWrapper {
    @Override
    Class getSynchronizerType() {
      CloudFoundryProviderSynchronizer
    }
  }

  class CloudFoundryProviderSynchronizer {}

  @Bean
  CloudFoundryCredentialsInitializer cloudFoundryCredentialsInitializer() {
    new CloudFoundryCredentialsInitializer()
  }

  @Bean
  CloudFoundryProvider cloudFoundryProvider() {
     new CloudFoundryProvider([] as Set)
  }
}
