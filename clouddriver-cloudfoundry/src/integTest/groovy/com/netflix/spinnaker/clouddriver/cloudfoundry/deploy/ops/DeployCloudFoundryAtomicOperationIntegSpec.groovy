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

import com.netflix.spinnaker.clouddriver.cloudfoundry.deploy.description.DeployCloudFoundryAtomicOperationDescription
import com.netflix.spinnaker.clouddriver.cloudfoundry.utils.DefaultCloudFoundryCLIOperatorFactory
import com.netflix.spinnaker.clouddriver.data.task.InMemoryTaskRepository
import com.netflix.spinnaker.clouddriver.data.task.TaskRepository
import com.netflix.spinnaker.clouddriver.deploy.DeploymentResult

class DeployCloudFoundryAtomicOperationIntegSpec extends AbstractCloudFoundryIntegSpec {
  def 'deploying an application in Cloud Foundry should return always return a deploymentResult object'() {
    setup:
    TaskRepository taskRepo = new InMemoryTaskRepository()
    TaskRepository.threadLocalTask.set(taskRepo.create(ScaleCloudFoundryAtomicOperation.PHASE, "Starting"))

    when:
    def desc = new DeployCloudFoundryAtomicOperationDescription(credentials: credentials, apiHost: apiHost, organization: organization, space: space, serverGroupName: "hello")
    def op = new DeployCloudFoundryAtomicOperation(desc)
    op.cloudFoundryCLIOperatorFactory = new DefaultCloudFoundryCLIOperatorFactory()
    def result = op.operate([])
    then:
    result instanceof DeploymentResult
  }



}
