package nz.co.yellow.vault.content.config;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import nz.co.yellow.vault.content.client.MturkListExtractionPollingRoute;
import nz.co.yellow.vault.content.client.model.SiliconeListResponse;

import org.apache.camel.CamelContext;
import org.apache.camel.ThreadPoolRejectedPolicy;
import org.apache.camel.component.gson.GsonDataFormat;
import org.apache.camel.spi.ThreadPoolProfile;
import org.apache.camel.spring.CamelBeanPostProcessor;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.FieldNamingPolicy;

@Configuration
public class SiliconClientConfig {

  @Inject
  private ApplicationContext context;

  @Inject
  private MturkListExtractionPollingRoute mturkListExtractionPollingRoute;

  @Bean
  public GsonDataFormat siliconeListResponseJsonFormat() {
    GsonDataFormat siliconeListResponseJsonFormat = new GsonDataFormat();
    siliconeListResponseJsonFormat.setUnmarshalType(SiliconeListResponse.class);
    siliconeListResponseJsonFormat
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

    siliconeListResponseJsonFormat.setPrettyPrinting(true);
    return siliconeListResponseJsonFormat;
  }

  @Bean
  public CamelBeanPostProcessor camelBeanPostProcessor() {
    CamelBeanPostProcessor camelBeanPostProcessor = new CamelBeanPostProcessor();
    camelBeanPostProcessor.setApplicationContext(context);
    return camelBeanPostProcessor;
  }

  @Bean
  public CamelContext camelContext()
      throws Exception {
    SpringCamelContext camelContext = new SpringCamelContext(context);

    ThreadPoolProfile profile = new ThreadPoolProfile();
    profile.setId("genericThreadPool");
    profile.setKeepAliveTime(120L);
    profile.setPoolSize(2);
    profile.setMaxPoolSize(10);
    profile.setTimeUnit(TimeUnit.SECONDS);
    profile.setRejectedPolicy(ThreadPoolRejectedPolicy.Abort);
    camelContext.getExecutorServiceManager().registerThreadPoolProfile(
        profile);

    camelContext.addRoutes(mturkListExtractionPollingRoute);
    return camelContext;
  }

}
