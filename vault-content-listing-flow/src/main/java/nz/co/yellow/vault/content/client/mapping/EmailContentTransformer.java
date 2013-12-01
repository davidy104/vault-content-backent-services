package nz.co.yellow.vault.content.client.mapping;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("emailContentTransformer")
public class EmailContentTransformer
    implements Expression {
  private static final Logger LOGGER = LoggerFactory
      .getLogger(EmailContentTransformer.class);

  @SuppressWarnings("unchecked")
  @Override
  public <T> T evaluate(Exchange exchange, Class<T> type) {
    LOGGER.debug("email transform start:{}");
    String emailNoticeListId = exchange.getProperty("emailNoticeMturkListIds", String.class);
    LOGGER.debug("email transform end:{}", emailNoticeListId);
    return (T) emailNoticeListId;
  }

}
