package com.netflix.spinnaker.clouddriver.cloudfoundry.deploy.ops

import com.netflix.spinnaker.clouddriver.cloudfoundry.deploy.description.ScaleCloudFoundryDescription

class ScaleCloudFoundryAtomicOperationIntegSpec extends AbstractCloudFoundryIntegSpec {
  def 'scale an application in Cloud Foundry'() {
    when:
    def desc = new ScaleCloudFoundryDescription(credentials: credentials, apiHost: apiHost)
    def op = new ScaleCloudFoundryAtomicOperation(desc)

    then:
    op.operate([])
  }
}
