package com.netflix.spinnaker.clouddriver.artifacts.kubernetes;

import com.netflix.spinnaker.clouddriver.artifacts.config.ArtifactAccount;

public class KubernetesArtifactAccount implements ArtifactAccount {
  @Override
  public String getName() {
    return "kubernetes";
  }
}
