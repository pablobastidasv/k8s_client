package co.pablobastidasv.k8sclient.control;

import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.openapi.models.V1PodStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PodsInfoProviderTest {

  @Mock
  private CoreV1Api coreApi;

  @InjectMocks
  private PodsInfoProvider podsInfoProvider;

  @Test
  void listPods() throws Exception{
    // Given a k8s response of 3 pods
    var podList = podList("Running", "Succeeded", "Pending");
    when(coreApi.listPodForAllNamespaces(
        null,
        null,
        null,
        "run=mf1",
        null,
        null,
        null,
        null,
        null
    )).thenReturn(podList);

    // When the provider is requested to give the list of pods
    var pods = podsInfoProvider.listPods();

    // The list of post must be of 3 and the names must correspond with the k8s api response
    assertEquals(3, pods.size());
  }

  private V1PodList podList(String... podsName) {
    assert podsName.length > 0;
    var items = new ArrayList<V1Pod>();
    for (String name : podsName) {
      var pod = new PodBuilder().withName(name).build();
      items.add(pod);
    }

    var podList = new V1PodList();
    podList.setItems(items);
    return podList;
  }

  private static class PodBuilder {
    private final V1Pod pod = new V1Pod();
    private String phase = null;
    private final V1ObjectMeta metadata = new V1ObjectMeta();

    public PodBuilder withPhase(String phase) {
      this.phase = phase;
      return this;
    }

    public PodBuilder withName(String phase) {
      metadata.setName(phase);
      return this;
    }

    public V1Pod build() {
      pod.setMetadata(metadata);
      var status = Optional.ofNullable(phase)
          .map(phase -> {
            var podStatus = new V1PodStatus();
            podStatus.setPhase(phase);
            return podStatus;
          })
          .orElseGet(V1PodStatus::new);
      pod.setStatus(status);
      return pod;
    }
  }
}
