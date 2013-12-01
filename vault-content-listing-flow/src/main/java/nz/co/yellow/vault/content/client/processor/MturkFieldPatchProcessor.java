package nz.co.yellow.vault.content.client.processor;

import nz.co.yellow.vault.content.client.MturkListExtractionPollingRoute;
import nz.co.yellow.vault.content.client.SiliconeRestfulClientSupport;
import nz.co.yellow.vault.content.data.SiliconeMturkModel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MturkFieldPatchProcessor
    implements Processor {
  private static final Logger LOGGER = LoggerFactory
      .getLogger(MturkFieldPatchProcessor.class);

  @Autowired
  private SiliconeRestfulClientSupport siliconeRestfulClientSupport;

  @Override
  public void process(Exchange exchange)
      throws Exception {
    LOGGER.debug("MturkFieldPatchProcessor process start:{}");
    SiliconeMturkModel siliconeMturkModel = exchange.getIn().getBody(SiliconeMturkModel.class);
    boolean patchError = false;
    String resourceUri = siliconeMturkModel.getResourceUri();
    LOGGER.info("URI{}: " + resourceUri);

    try {
      siliconeRestfulClientSupport.patchMturkFields(resourceUri);
    }
    catch (Exception e) {
      LOGGER.error("patch errors", e);
      patchError = true;
    }
    exchange.setProperty(MturkListExtractionPollingRoute.PATCH_ERROR, patchError);
  }
}
