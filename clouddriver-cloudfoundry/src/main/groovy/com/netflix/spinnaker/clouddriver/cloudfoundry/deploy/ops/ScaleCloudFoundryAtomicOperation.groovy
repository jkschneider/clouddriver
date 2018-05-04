package com.netflix.spinnaker.clouddriver.cloudfoundry.deploy.ops

import com.netflix.spinnaker.clouddriver.cloudfoundry.deploy.description.ScaleCloudFoundryDescription
import com.netflix.spinnaker.clouddriver.data.task.Task
import com.netflix.spinnaker.clouddriver.data.task.TaskRepository
import com.netflix.spinnaker.clouddriver.orchestration.AtomicOperation
import org.cloudfoundry.reactor.DefaultConnectionContext
import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient

class ScaleCloudFoundryAtomicOperation implements AtomicOperation<Void> {
  private static final String PHASE = "CF_SCALE"

  private final ScaleCloudFoundryDescription description

  private static Task getTask() {
    TaskRepository.threadLocalTask.get()
  }

  ScaleCloudFoundryAtomicOperation(ScaleCloudFoundryDescription description) {
    this.description = description
  }

  @Override
  Void operate(List priorOutputs) {
    def context = DefaultConnectionContext.builder()
      .apiHost(description.apiHost)
      .build()

    def client = ReactorCloudFoundryClient.builder()
      .connectionContext(context)
      .tokenProvider(description.credentials.credentials)
      .build()

    task.updateStatus PHASE, "Initializing resize of server group $description.serverGroupName in " +
      "$description.region..."

    def app = client.getApplication(description.serverGroupName)

    if (app.instances != description.targetSize) {
      task.updateStatus PHASE, "Changing ${description.serverGroupName} to ${description.targetSize} instances"
      client.updateApplicationInstances(description.serverGroupName, description.targetSize)
    }

    if (app.memory != description.memory) {
      task.updateStatus PHASE, "Changing ${description.serverGroupName} to ${description.memory}MB memory"
      client.updateApplicationMemory(description.serverGroupName, description.memory)
    }

    if (app.diskQuota != description.disk) {
      task.updateStatus PHASE, "Changing ${description.serverGroupName} to ${description.disk}MB disk limit"
      client.updateApplicationDiskQuota(description.serverGroupName, description.disk)
    }

    task.updateStatus PHASE, "Done resizing server group $description.serverGroupName in $description.region."
    null
  }
}
