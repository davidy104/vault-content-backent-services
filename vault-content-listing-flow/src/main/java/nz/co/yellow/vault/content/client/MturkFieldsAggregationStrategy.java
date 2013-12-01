package nz.co.yellow.vault.content.client;

import java.util.ArrayList;
import java.util.List;

import nz.co.yellow.vault.content.data.SiliconeMturkModel;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MturkFieldsAggregationStrategy
    implements AggregationStrategy {
  private static final Logger LOGGER = LoggerFactory
      .getLogger(MturkFieldsAggregationStrategy.class);

  @SuppressWarnings("unchecked")
  @Override
  public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
    StringBuilder emailNoticeIdListSb = null;
    List<SiliconeMturkModel> retryList = null;
    LOGGER.debug("MturkFieldsAggregationStrategy start:{}");
    boolean mergeDbError = false;
    boolean patchError = false;

    if (newExchange.getProperty(MturkListExtractionPollingRoute.MERGE_DB_ERROR) != null) {
      mergeDbError = newExchange.getProperty(MturkListExtractionPollingRoute.MERGE_DB_ERROR, Boolean.class);
    }

    if (newExchange.getProperty(MturkListExtractionPollingRoute.PATCH_ERROR) != null) {
      patchError = newExchange.getProperty(MturkListExtractionPollingRoute.PATCH_ERROR, Boolean.class);
    }

    String currentProcessType = newExchange.getProperty(MturkListExtractionPollingRoute.PROCESS_TYPE, String.class);
    LOGGER.debug("currentProcessType:{}", currentProcessType);
    SiliconeMturkModel siliconeMturkModel = newExchange.getIn().getBody(SiliconeMturkModel.class);

    if (mergeDbError || currentProcessType.equals(MturkListExtractionPollingRoute.RETRY_PROCESS)) {
      LOGGER.debug("MturkFieldsAggregationStrategy get merge error from current element");
      if (oldExchange == null || oldExchange.getProperty("emailNoticeIdListSb") == null) {
        emailNoticeIdListSb = new StringBuilder("MergeToOnlineDB error: ");
      }
      else {
        emailNoticeIdListSb = oldExchange.getProperty("emailNoticeIdListSb", StringBuilder.class);
      }
      emailNoticeIdListSb.append(siliconeMturkModel.getYellowListId() + ",");
      LOGGER.debug("so far emailNoticeListIds:{}", emailNoticeIdListSb.toString());
      newExchange.setProperty("emailNoticeIdListSb", emailNoticeIdListSb);
    }
    else if (patchError && currentProcessType.equals(MturkListExtractionPollingRoute.REGULAR_PROCESS)) {
      LOGGER.debug("MturkFieldsAggregationStrategy get patch error from current element");
      if (oldExchange == null || oldExchange.getProperty("retryMturkFieldsList") == null) {
        retryList = new ArrayList<SiliconeMturkModel>();
      }
      else {
        retryList = oldExchange.getProperty("retryMturkFieldsList", List.class);
      }

      retryList.add(siliconeMturkModel);
      LOGGER.debug("so far, retryList size:{}", retryList.size());
      newExchange.setProperty("retryMturkFieldsList", retryList);
    }
    LOGGER.debug("MturkFieldsAggregationStrategy end:{}");
    return newExchange;
  }
}
