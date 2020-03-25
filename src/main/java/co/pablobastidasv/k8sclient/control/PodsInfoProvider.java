package co.pablobastidasv.k8sclient.control;

import co.pablobastidasv.k8sclient.model.Pod;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PodsInfoProvider {

  private final CoreV1Api coreApi;

  public PodsInfoProvider(CoreV1Api coreApi) {
    this.coreApi = coreApi;
  }

  public List<Pod> listPods() throws ApiException {
    return coreApi.listPodForAllNamespaces(
      null,
      null,
      null,
      "run=mf1",
      null,
      null,
      null,
      null,
      null
      ).getItems()
        .stream()
        .map(Pod::new)
        .collect(Collectors.toList());
  }

}
