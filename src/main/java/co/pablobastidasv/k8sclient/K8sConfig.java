package co.pablobastidasv.k8sclient;

import co.pablobastidasv.k8sclient.model.properties.K8sConstants;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.util.ClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

import static io.kubernetes.client.openapi.Configuration.setDefaultApiClient;

@Configuration
public class K8sConfig {

  private static final Logger log = LoggerFactory.getLogger(K8sConfig.class);

  @Bean
  @ConditionalOnProperty(prefix = "k8s", name = "client", havingValue = K8sConstants.LOCAL)
  public ApiClient apiClientStandard() throws IOException {
    log.debug("Building ApiClient in 'local' mode.");
    return ClientBuilder.standard()
        .setBasePath("http://localhost:3000")
        .build();
  }

  @Bean
  @ConditionalOnMissingBean
  public ApiClient apiClientCluster() throws IOException {
    log.debug("Building ApiClient in 'cluster' mode.");
    return ClientBuilder.cluster()
        .build();
  }

  @Bean
  public CoreV1Api coreV1Api(ApiClient client) {
    log.debug("Building CoreV1Api.");
    setDefaultApiClient(client);
    return new CoreV1Api();
  }
}
