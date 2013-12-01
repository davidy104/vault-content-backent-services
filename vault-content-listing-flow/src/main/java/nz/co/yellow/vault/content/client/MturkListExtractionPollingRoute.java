package nz.co.yellow.vault.content.client;

import nz.co.yellow.vault.content.client.mapping.EmailContentTransformer;
import nz.co.yellow.vault.content.client.mapping.SiliconeMturkFieldsTransformer;
import nz.co.yellow.vault.content.client.processor.EmailNoticeProcessor;
import nz.co.yellow.vault.content.client.processor.GeneralExceptionProcessor;
import nz.co.yellow.vault.content.client.processor.MturkFieldMergeOnlineDbProcessor;
import nz.co.yellow.vault.content.client.processor.MturkFieldPatchProcessor;
import nz.co.yellow.vault.content.client.processor.MturkFieldsPostProcessor;
import nz.co.yellow.vault.content.client.processor.MturkListMainDistributionProcessor;
import nz.co.yellow.vault.content.client.processor.MturkListResponseHandleProcessor;

import org.apache.camel.Exchange;
import org.apache.camel.component.gson.GsonDataFormat;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("mturkListExtractionPollingRoute")
public class MturkListExtractionPollingRoute
    extends SpringRouteBuilder {

  @Autowired
  private MturkListMainDistributionProcessor mturkListMainDistributionProcessor;

  @Autowired
  private GeneralExceptionProcessor generalExceptionProcessor;

  @Autowired
  private EmailNoticeProcessor emailNoticeProcessor;

  @Autowired
  private EmailContentTransformer emailContentTransformer;

  public final static String PROCESS_TYPE = "processType";

  public final static String REGULAR_PROCESS = "regular";

  public final static String RETRY_PROCESS = "retry";

  // @Value("${vault-content-backend.pollingRepeatInterval:30000}")
  // private Integer repeatInterval;
  //
  // @Value("${vault-content-backend.pollingRepeatCount:-1}")
  // private Integer repeatCount;

  @Value("${vault-content-backend.pollingSchedule:0+0/1+6-22+?+*+MON-SUN}")
  private String pollingSchedule;

  public final static String EMAIL_ENDPOINT_URI = "direct:startEmailNotice";

  public final static String EMAIL_ROUTE_ID = "emailProducer";

  public final static String REGULAR_ENDPOINT_URI = "direct:regular";

  public final static String MTURKFIELDS_PROCESS_ENDPOINT_URI = "direct:mturkFieldsProcess";

  @Autowired
  private SiliconeRestfulClientSupport siliconeRestfulClientSupport;

  @Autowired
  private GsonDataFormat siliconeListResponseJsonFormat;

  @Autowired
  private SiliconeMturkFieldsTransformer siliconeMturkFieldsTransformer;

  @Autowired
  private MturkFieldMergeOnlineDbProcessor mturkFieldMergeOnlineDbProcessor;

  @Autowired
  private MturkFieldPatchProcessor mturkFieldPatchProcessor;

  @Autowired
  private MturkFieldsPostProcessor mturkFieldsPostProcessor;

  @Autowired
  private MturkListResponseHandleProcessor mturkListResponseHandleProcessor;

  @Value("${vault-content-backend.silicone_httpConnectionTimeout:10000}")
  private Integer connectionTimeOut;

  public static final String MERGE_DB_ERROR = "mergeOnlineDbError";

  public static final String PATCH_ERROR = "patchError";

  public static final String ERROR_MESSAGE = "errorMessage";

  @Override
  public void configure()
      throws Exception {

    onException(Exception.class).to("log:errors")
        .process(generalExceptionProcessor).continued(true);

    from("quartz://mturkTimer?cron=" + pollingSchedule + "&stateful=true")
        .routeId("polling")
        .process(mturkListMainDistributionProcessor)
        .choice()
        .when(property(PROCESS_TYPE).isEqualTo(REGULAR_PROCESS))
        .to(REGULAR_ENDPOINT_URI)
        .when(property(PROCESS_TYPE).isEqualTo(RETRY_PROCESS))
        .to(MTURKFIELDS_PROCESS_ENDPOINT_URI)
        .otherwise()
        .to("log:undefined process Type?level=ERROR")
        .throwException(new Exception("undefined process Type"));

    from(REGULAR_ENDPOINT_URI)
        .setHeader(Exchange.HTTP_METHOD, constant("GET"))
        .setHeader(Exchange.HTTP_PATH,
            simple("/mturk/api/processed_listing/"))
        .setHeader(
            Exchange.HTTP_QUERY,
            simple("username=" + siliconeRestfulClientSupport.getUserName() + "&api_key=" + siliconeRestfulClientSupport.getApiKey()
                + "&offset=${property.offset}&limit=" + siliconeRestfulClientSupport.getExtractLimit()))
        .to(siliconeRestfulClientSupport.getRootUri() + "?httpClient.soTimeout="
            + connectionTimeOut + "")
        .to("log:output")
        .choice()
        .when(header(Exchange.HTTP_RESPONSE_CODE).isEqualTo(200))
        .unmarshal(siliconeListResponseJsonFormat)
        .to("log:output")
        .process(mturkListResponseHandleProcessor)
        .to(MTURKFIELDS_PROCESS_ENDPOINT_URI)
        .otherwise()
        .throwException(new Exception("get mturk fields failed"));

    from(MTURKFIELDS_PROCESS_ENDPOINT_URI)
        .to("direct:loopMturkFields")
        .process(mturkFieldsPostProcessor)
        .choice()
        .when(simple("${property.emailNotice}== true"))
        .to(EMAIL_ENDPOINT_URI);

    from("direct:loopMturkFields")
        .split(body(), new MturkFieldsAggregationStrategy())
        .choice()
        .when(property(PROCESS_TYPE).isEqualTo(REGULAR_PROCESS))
        .transform(siliconeMturkFieldsTransformer)
        .to("direct:doMturkFieldProcess")
        .otherwise()
        .to("direct:doMturkFieldProcess")
        .end();

    from("direct:doMturkFieldProcess")
        .process(mturkFieldMergeOnlineDbProcessor)
        .choice()
        .when(property(MERGE_DB_ERROR).isNotEqualTo(true))
        .process(mturkFieldPatchProcessor)
        .delay(2000);

    from(EMAIL_ENDPOINT_URI).routeId(EMAIL_ROUTE_ID)
        .to("velocity://noticeEmail.vm")
        .to("log:mail").process(emailNoticeProcessor);

  }
}
