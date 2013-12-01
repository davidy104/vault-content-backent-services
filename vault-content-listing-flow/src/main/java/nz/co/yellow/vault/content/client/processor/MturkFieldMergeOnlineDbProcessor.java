package nz.co.yellow.vault.content.client.processor;

import nz.co.yellow.vault.content.client.MturkListExtractionPollingRoute;
import nz.co.yellow.vault.content.data.SiliconeMturkModel;
import nz.co.yellow.vault.content.ds.SiliconeMturkFieldsDS;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MturkFieldMergeOnlineDbProcessor
    implements Processor {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(MturkFieldMergeOnlineDbProcessor.class);

  @Autowired
  private SiliconeMturkFieldsDS siliconeMturkFieldsDs;

  @Override
  public void process(Exchange exchange)
      throws Exception {
    LOGGER.debug("MturkFieldMergeOnlineDbProcessor process start:{}");
    SiliconeMturkModel siliconeMturkModel = exchange.getIn().getBody(SiliconeMturkModel.class);
    boolean mergeDbError = false;
    String resultMsg = null;
    resultMsg = siliconeMturkFieldsDs
        .createOrMergeSiliconeMturkFields(siliconeMturkModel);
    LOGGER.debug("get result msg:{}", resultMsg);

    if (StringUtils.isEmpty(resultMsg) || resultMsg.startsWith("Error")) {
      LOGGER.debug("MturkFieldMergeOnlineDbProcessor merge error:{}", resultMsg);
      mergeDbError = true;
    }
    exchange.setProperty(MturkListExtractionPollingRoute.MERGE_DB_ERROR, mergeDbError);
    LOGGER.debug("MturkFieldMergeOnlineDbProcessor process end:{}");
  }

}
