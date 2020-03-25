package co.pablobastidasv.k8sclient.model;

import io.kubernetes.client.openapi.models.V1Pod;

public class Pod {
  public final String name;
  public final String status;

  public Pod(String name, String status) {
    this.name = name;
    this.status = status;
  }

  public Pod(V1Pod pod) {
    name = pod.getMetadata().getName();
    status = pod.getStatus().getPhase();
  }
}
