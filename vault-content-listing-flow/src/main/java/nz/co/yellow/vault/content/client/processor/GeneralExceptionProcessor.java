package nz.co.yellow.vault.content.client.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.quartz.impl.StdScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("generalExceptionProcessor")
public class GeneralExceptionProcessor
    implements Processor {
  private static final Logger LOGGER = LoggerFactory
      .getLogger(GeneralExceptionProcessor.class);

  @Override
  public void process(Exchange exchange)
      throws Exception {
    LOGGER.debug("generalExceptionProcessor start:{}");

    Exception exception = (Exception) exchange
        .getProperty(Exchange.EXCEPTION_CAUGHT);
    if (exception != null) {
      LOGGER.debug("exception handle for polling route start:{}",
          exception.getMessage());
      LOGGER.debug("exception:{}",
          exception);
    }

    StdScheduler stdScheduler = (StdScheduler) exchange.getIn().getHeader(
        "scheduler");
    if (stdScheduler != null) {
      if (stdScheduler.getContext().get("offset") == null) {
        LOGGER.debug("reset next offset as 0 in exceptin handle:{}");
        stdScheduler.getContext().put("offset", new Long(0));
      }
    }
    else {
      LOGGER.error("stdScheduler is null in exception handle");
    }

  }
}
