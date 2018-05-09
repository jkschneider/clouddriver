package com.netflix.spinnaker.clouddriver.cloudfoundry.deploy.converters

import com.netflix.spinnaker.clouddriver.cloudfoundry.CloudFoundryOperation
import com.netflix.spinnaker.clouddriver.cloudfoundry.deploy.description.ScaleCloudFoundryDescription
import com.netflix.spinnaker.clouddriver.cloudfoundry.deploy.ops.ScaleCloudFoundryAtomicOperation
import com.netflix.spinnaker.clouddriver.orchestration.AtomicOperation
import com.netflix.spinnaker.clouddriver.orchestration.AtomicOperations
import com.netflix.spinnaker.clouddriver.security.AbstractAtomicOperationsCredentialsSupport
import org.springframework.stereotype.Component

@CloudFoundryOperation(AtomicOperations.RESIZE_SERVER_GROUP)
@Component
class ScaleCloudFoundryAtomicOperationConverter extends AbstractAtomicOperationsCredentialsSupport {

    @Override
    AtomicOperation convertOperation(Map input) {
      new ScaleCloudFoundryAtomicOperation(convertDescription(input))
    }

    @Override
    ScaleCloudFoundryDescription convertDescription(Map input) {
      def converted = objectMapper.convertValue(input, ScaleCloudFoundryDescription)
      converted.credentials = getCredentialsObject(input.credentials as String)
      converted
    }
}
