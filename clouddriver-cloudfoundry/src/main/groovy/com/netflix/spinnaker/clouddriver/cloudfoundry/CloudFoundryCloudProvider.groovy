package com.netflix.spinnaker.clouddriver.cloudfoundry

import com.netflix.spinnaker.clouddriver.core.CloudProvider
import org.springframework.stereotype.Component

import java.lang.annotation.Annotation

@Component
class CloudFoundryCloudProvider implements CloudProvider {
  static final String ID = 'cloudfoundry'
  final String id = ID
  final String displayName = "Cloud Foundry"
  final Class<Annotation> operationAnnotationType = CloudFoundryOperation
}
