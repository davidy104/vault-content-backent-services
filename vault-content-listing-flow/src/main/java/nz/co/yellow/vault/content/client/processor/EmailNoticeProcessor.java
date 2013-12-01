package nz.co.yellow.vault.content.client.processor;

import nz.co.yellow.vault.content.common.email.SendEmailSupport;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * email notice for unrecoverable failed mturk fields
 *
 * @author david
 *
 */
@Component("emailNoticeProcessor")
public class EmailNoticeProcessor
    implements Processor {

  @Autowired
  private SendEmailSupport sendEmailSupport;

  private static final Logger LOGGER = LoggerFactory
      .getLogger(EmailNoticeProcessor.class);

  @Override
  public void process(Exchange exchange)
      throws Exception {
    LOGGER.debug("email notice start: {}");
    String emailContent = (String) exchange.getIn().getBody();
    LOGGER.debug("email content{}: " + emailContent);
    sendEmailSupport.sendSimpleEmail(emailContent);
    LOGGER.debug("email notice end: {}");
  }

}
