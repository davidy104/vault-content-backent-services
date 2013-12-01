package nz.co.yellow.vault.content.client.processor;

import java.util.List;

import nz.co.yellow.vault.content.client.MturkListExtractionPollingRoute;
import nz.co.yellow.vault.content.client.SiliconeRestfulClientSupport;
import nz.co.yellow.vault.content.data.SiliconeMturkModel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.quartz.impl.StdScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * main process for extracting mturk fields list
 *
 * @author david
 *
 */
@Component
public class MturkListMainDistributionProcessor
    implements Processor {

  @Autowired
  private SiliconeRestfulClientSupport siliconeRestfulClientSupport;

  public final static String CURRENT_ITERATION = "currentIteration";

  public final static String FAIL_FIELDID_LIST = "failFieldIdList";

  public final static String OFFSET = "offset";

  private Long currentIteration = new Long(0);

  private static final Logger LOGGER = LoggerFactory
      .getLogger(MturkListMainDistributionProcessor.class);

  @SuppressWarnings("unchecked")
  @Override
  public void process(Exchange exchange)
      throws Exception {

    Long offset = new Long(0);
    StdScheduler stdScheduler = null;
    stdScheduler = (StdScheduler) exchange.getIn().getHeader("scheduler");

    if (stdScheduler == null) {
      throw new Exception("StdScheduler is null.");
    }

    if (stdScheduler.getContext().get(CURRENT_ITERATION) != null) {
      currentIteration = (Long) stdScheduler.getContext().get(
          CURRENT_ITERATION);
    }
    LOGGER.debug("currentIteration: {}" + currentIteration);
    // set next iteration
    Long nextIteration = currentIteration + 1;
    LOGGER.debug("set next iteration as:{}", nextIteration);
    stdScheduler.getContext().put("currentIteration", nextIteration);

    exchange.setProperty(CURRENT_ITERATION, currentIteration);

    if (stdScheduler.getContext().get(FAIL_FIELDID_LIST) != null) {
      // retry process, let current route know, it is for retry process
      LOGGER.debug("retry process {}");
      exchange.setProperty(
          MturkListExtractionPollingRoute.PROCESS_TYPE,
          MturkListExtractionPollingRoute.RETRY_PROCESS);

      List<SiliconeMturkModel> retryMturkFieldsList = (List<SiliconeMturkModel>) stdScheduler.getContext().get(FAIL_FIELDID_LIST);
      exchange.getIn().setBody(retryMturkFieldsList);
      stdScheduler.getContext().remove(FAIL_FIELDID_LIST);
    }
    else {
      LOGGER.debug("regular process{}");
      exchange.setProperty(
          MturkListExtractionPollingRoute.PROCESS_TYPE,
          MturkListExtractionPollingRoute.REGULAR_PROCESS);

      if (stdScheduler.getContext().get(OFFSET) != null) {
        offset = (Long) stdScheduler.getContext().get(OFFSET);
        LOGGER.debug("get offset from statefulJob: {}" + offset);
      }

      exchange.setProperty("offset", offset);
    }
  }

}
