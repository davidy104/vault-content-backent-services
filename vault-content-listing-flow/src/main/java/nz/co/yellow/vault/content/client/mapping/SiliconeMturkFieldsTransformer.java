package nz.co.yellow.vault.content.client.mapping;

import nz.co.yellow.vault.content.client.model.SiliconeMturkFields;
import nz.co.yellow.vault.content.data.SiliconeMturkModel;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SiliconeMturkFieldsTransformer
    implements Expression {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(SiliconeMturkFieldsTransformer.class);

  @SuppressWarnings("unchecked")
  @Override
  public <T> T evaluate(Exchange exchange, Class<T> type) {
    LOGGER.debug("SiliconeMturkFields to model convert start:{}");
    SiliconeMturkFields siliconeMturkFields = exchange.getIn().getBody(SiliconeMturkFields.class);
    SiliconeMturkModel siliconeMturkModel = new SiliconeMturkModel();
    siliconeMturkModel.setEmail(siliconeMturkFields.getEmail());
    siliconeMturkModel.setFax(siliconeMturkFields.getFaxNumber());
    siliconeMturkModel.setFreeNumber(siliconeMturkFields.getFreeNumber());
    siliconeMturkModel.setMobileNumber(siliconeMturkFields.getMobileNumber());
    siliconeMturkModel.setPacking(siliconeMturkFields.getParking());
    siliconeMturkModel
        .setResourceUri(siliconeMturkFields.getResourceUri());
    siliconeMturkModel.setSecondaryNumber(siliconeMturkFields.getSecondaryNumber());
    int since = 0;
    if (!StringUtils.isEmpty(siliconeMturkFields.getSince())) {
      since = Integer.valueOf(siliconeMturkFields.getSince());
    }

    String desc = siliconeMturkFields.getDescription();
    if (desc != null) {
      if (desc.length() > 4000) {
        LOGGER.debug(
            "yellowListId["
                + siliconeMturkFields.getYellowListingId()
                + "] description is more than 4000:{}",
            desc.length());
        LOGGER.debug("desc:{}", desc);
        desc = desc.substring(0, 3999);
      }
      siliconeMturkModel.setDescription(desc);
    }
    siliconeMturkModel.setSince(since);
    siliconeMturkModel.setYellowListId(Integer.valueOf(siliconeMturkFields
        .getYellowListingId()));
    LOGGER.debug("SiliconeMturkFields to model convert end:{}", siliconeMturkModel);
    return (T) siliconeMturkModel;
  }

}
