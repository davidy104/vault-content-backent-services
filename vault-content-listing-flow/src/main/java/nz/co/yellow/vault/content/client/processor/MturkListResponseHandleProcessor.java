package nz.co.yellow.vault.content.client.processor;

import java.util.List;

import nz.co.yellow.vault.content.client.model.SiliconeListResponse;
import nz.co.yellow.vault.content.client.model.SiliconeMturkFields;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.quartz.impl.StdScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MturkListResponseHandleProcessor
    implements Processor {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(MturkListResponseHandleProcessor.class);

  @SuppressWarnings("unchecked")
  @Override
  public void process(Exchange exchange)
      throws Exception {
    Long nextOffset = new Long(0);
    Message inMsg = exchange.getIn();
    SiliconeListResponse siliconeListResponse = inMsg.getBody(SiliconeListResponse.class);
    LOGGER.debug("after unmarshal siliconeListResponse:{}", siliconeListResponse);
    List<SiliconeMturkFields> fieldList = siliconeListResponse.getObjects();
    String next = siliconeListResponse.getMeta().getNext();
    nextOffset = this.getNextIterationOffset(next);
    exchange.setProperty("mturkFieldsAvailable", true);

    if (fieldList == null || fieldList.size() == 0) {
      nextOffset = new Long(0);
      fieldList = java.util.Collections.EMPTY_LIST;
    }

    inMsg.setBody(fieldList);
    StdScheduler stdScheduler = (StdScheduler) exchange.getIn().getHeader("scheduler");
    stdScheduler.getContext().put("offset", nextOffset);

  }

  private Long getNextIterationOffset(String next) {
    LOGGER.debug("get next from meta:{}", next);
    String nextOffsetStr = null;
    Long result = new Long(0);
    if (!StringUtils.isEmpty(next)) {
      if (next.indexOf("offset=") != -1) {
        int offsetCharLen = "offset".length() + 1;
        nextOffsetStr = next.substring(next.indexOf("offset=")
            + offsetCharLen, next.length());
        LOGGER.debug("get next from nextstr:{}", nextOffsetStr);
        if (!StringUtils.isEmpty(nextOffsetStr)) {
          result = Long.valueOf(nextOffsetStr);
        }
      }
    }
    LOGGER.debug("get next offset:{}", result);
    return result;
  }
}
