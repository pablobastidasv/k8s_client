package co.pablobastidasv.k8sclient.boudary;

import co.pablobastidasv.k8sclient.control.PodsInfoProvider;
import co.pablobastidasv.k8sclient.model.Pod;
import io.kubernetes.client.openapi.ApiException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("pods")
public class PodResource {

  private final PodsInfoProvider podsInfoProvider;

  public PodResource(PodsInfoProvider podsInfoProvider) {
    this.podsInfoProvider = podsInfoProvider;
  }

  @GetMapping
  public List<Pod> listPods() throws ApiException {
    return podsInfoProvider.listPods();
  }

}
