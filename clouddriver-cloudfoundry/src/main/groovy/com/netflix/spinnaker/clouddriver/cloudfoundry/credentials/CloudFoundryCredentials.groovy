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

package com.netflix.spinnaker.clouddriver.cloudfoundry.credentials

import com.netflix.spinnaker.clouddriver.security.AccountCredentials
import com.netflix.spinnaker.fiat.model.resources.Permissions
import org.cloudfoundry.reactor.TokenProvider
import org.cloudfoundry.reactor.tokenprovider.PasswordGrantTokenProvider

class CloudFoundryCredentials implements AccountCredentials<TokenProvider> {
  private static final String CLOUD_PROVIDER = "cloudfoundry"

  final String name
  final String environment
  final String accountType
  final List<String> requiredGroupMembership = Collections.emptyList()

  final String userName
  final String password

  CloudFoundryCredentials(String userName, String password, String name = 'main', String environment = 'prod',
                          String accountType = 'mainprod') {
    this.name = name
    this.environment = environment
    this.accountType = accountType
    this.userName = userName
    this.password = password
  }

  @Override
  TokenProvider getCredentials() {
    return PasswordGrantTokenProvider.builder()
      .username(userName)
      .password(password)
      .build()
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
