package nz.co.yellow.vault.content.client.processor;

import java.util.List;

import nz.co.yellow.vault.content.data.SiliconeMturkModel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.quartz.impl.StdScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MturkFieldsPostProcessor
    implements Processor {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(MturkFieldsPostProcessor.class);

  @SuppressWarnings("unchecked")
  @Override
  public void process(Exchange exchange)
      throws Exception {
    LOGGER.debug("MturkFieldsPostProcessor process start:{}");
    StdScheduler stdScheduler = (StdScheduler) exchange.getIn().getHeader(
        "scheduler");

    if (exchange.getProperty("emailNoticeIdListSb") != null) {
      exchange.setProperty("emailNotice", true);
      String emailNoticeIds = exchange.getProperty("emailNoticeIdListSb", StringBuilder.class).toString();
      exchange.getIn().setBody(emailNoticeIds);
      // exchange.setProperty("emailNoticeMturkListIds", emailNoticeIds);
      LOGGER.debug("get emailNoticeMturkListIds:{}", emailNoticeIds);
    }

    if (exchange.getProperty("retryMturkFieldsList") != null) {
      List<SiliconeMturkModel> retryMturkFieldsList = (List<SiliconeMturkModel>) exchange.getProperty("retryMturkFieldsList");
      stdScheduler.getContext().put("failFieldIdList", retryMturkFieldsList);
    }

    LOGGER.debug("MturkFieldsPostProcessor process end:{}");
  }
}
