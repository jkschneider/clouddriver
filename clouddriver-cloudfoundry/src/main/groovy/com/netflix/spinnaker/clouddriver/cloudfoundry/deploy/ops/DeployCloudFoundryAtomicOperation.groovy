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
import com.netflix.spinnaker.clouddriver.cloudfoundry.utils.CloudFoundryCLIOperatorFactory
import com.netflix.spinnaker.clouddriver.data.task.Task
import com.netflix.spinnaker.clouddriver.data.task.TaskRepository
import com.netflix.spinnaker.clouddriver.deploy.DeploymentResult
import com.netflix.spinnaker.clouddriver.orchestration.AtomicOperation
import org.cloudfoundry.operations.CloudFoundryOperations
import org.cloudfoundry.operations.applications.ApplicationDetail
import org.cloudfoundry.operations.applications.GetApplicationRequest
import org.springframework.beans.factory.annotation.Autowired

class DeployCloudFoundryAtomicOperation implements AtomicOperation<DeploymentResult> {
  private static final String BASE_PHASE = "DEPLOY"

  @Autowired
  CloudFoundryCLIOperatorFactory cloudFoundryCLIOperatorFactory

  private final DeployCloudFoundryAtomicOperationDescription description

  DeployCloudFoundryAtomicOperation(DeployCloudFoundryAtomicOperationDescription description) {
    this.description = description
  }


  private static Task getTask() {
    TaskRepository.threadLocalTask.get()
  }

  @Override
  DeploymentResult operate(List priorOutputs) {
    task.updateStatus BASE_PHASE, "Initializing Deploy"

    def cloudFoundryCLIoperator = cloudFoundryCLIOperatorFactory.createCloudFoundryCLIOperator(description)

    task.updateStatus BASE_PHASE, "Creating application ${description.serverGroupName}"

    this.createApplication(description, cloudFoundryCLIoperator)

    new DeploymentResult()
  }

  private void createApplication(DeployCloudFoundryAtomicOperationDescription description, CloudFoundryOperations cloudFoundryCLIoperator) {
    ApplicationDetail application

    try {
      def getApplicationRequest = GetApplicationRequest
        .builder()
        .name(description.serverGroupName)
        .build()

      application = cloudFoundryCLIoperator
        .applications()
        .get(getApplicationRequest)
        .block()

    } catch (IllegalArgumentException e) {
      task.updateStatus BASE_PHASE, "${description.serverGroupName} is unavailable."
    } catch (Exception e) {
      def message = "Error while checking for existing application '${description.serverGroupName}'. Error message: '${e.message}'"
      throw new RuntimeException(message, e)
    }

  }
}
