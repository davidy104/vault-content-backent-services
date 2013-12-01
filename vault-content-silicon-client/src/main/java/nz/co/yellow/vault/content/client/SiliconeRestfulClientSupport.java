package nz.co.yellow.vault.content.client;

import java.io.InputStream;

import nz.co.yellow.vault.content.common.PatchMethod;
import nz.co.yellow.vault.content.common.SiliconeUtils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Restful client for Silicone API
 *
 * @author david
 *
 */
@Component("siliconeRestfulClientSupport")
public class SiliconeRestfulClientSupport {

  @Value("${silicon-api-services.host}")
  private String rootUri;

  @Value("${silicon-api-services.username:silicon}")
  private String userName;

  @Value("${silicon-api-services.apiKey}")
  private String apiKey;

  @Value("${vault-content-backend.extractListLimit:20}")
  private Integer extractLimit;

  @Value("${vault-content-backend.silicone_httpConnectionTimeout:10000}")
  private Integer connectionTimeOut;

  @Value("${vault-content-backend.silicone_httpReceiveTimeout:10000}")
  private Integer receiveTimeOut;

  private static final Logger LOGGER = LoggerFactory
      .getLogger(SiliconeRestfulClientSupport.class);

  private static final String CHARSET = "UTF-8";

  /**
   * extract mturk fields list from Silicone API
   *
   * @param offset
   * @return
   * @throws Exception
   */
  public String getSiliconeList(Long offset)
      throws Exception {
    String output = null;
    Integer statusCode = null;
    String accessUri = "/mturk/api/processed_listing/";
    // String format = MediaType.APPLICATION_JSON;

    final HttpClient httpClient = new HttpClient();
    HttpConnectionManagerParams managerParams = httpClient
        .getHttpConnectionManager().getParams();

    managerParams.setConnectionTimeout(connectionTimeOut);
    LOGGER.debug("getSiliconeList start:{}");
    LOGGER.debug("getSiliconeList rootUrl:{}", rootUri);
    LOGGER.debug("getSiliconeList apiKey:{}", apiKey);
    GetMethod getMethod = new GetMethod(rootUri + accessUri);
    getMethod.addRequestHeader("Content-Type",
        "application/json;charset=utf-8");

    NameValuePair userNameParam = new NameValuePair("username", userName);
    NameValuePair apiKeyParam = new NameValuePair("api_key", apiKey);

    offset = offset == null ? 0 : offset;
    NameValuePair offsetParam = new NameValuePair("offset",
        String.valueOf(offset));

    NameValuePair limitParam = new NameValuePair("limit",
        String.valueOf(extractLimit));

    getMethod.setQueryString(new NameValuePair[] { userNameParam,
        apiKeyParam, offsetParam, limitParam });

    try {
      statusCode = httpClient.executeMethod(getMethod);
      LOGGER.debug("statusCode: " + statusCode);
      if (statusCode != HttpStatus.SC_OK) {
        throw new Exception("get mturk fields error, response status["
            + statusCode + "]");
      }
      InputStream inputstream = getMethod.getResponseBodyAsStream();
      output = SiliconeUtils.getStringFromInputStream(inputstream);
    }
    catch (Exception e) {
      LOGGER.debug("get methond [" + accessUri + "] execution errors", e);
      throw new Exception("get methond [" + accessUri
          + "] execution errors", e);
    }
    finally {
      // Release the connection.
      LOGGER.debug("release connection for get.");
      getMethod.releaseConnection();
    }

    // output = client.get(String.class);
    LOGGER.debug("output: " + output);
    return output;
  }

  /**
   * Patch status back to Silicone
   *
   * @param resourceUri
   * @return
   * @throws Exception
   */
  public void patchMturkFields(String resourceUri)
      throws Exception {
    LOGGER.debug("start patch:{}", resourceUri);
    String jsonRequest = "{\"exported\": \"True\"}";
    String errorMsg = null;
    StringRequestEntity requestEntity = null;
    final HttpClient httpClient = new HttpClient();
    final PatchMethod patchMethod = new PatchMethod(rootUri + resourceUri);
    patchMethod.addRequestHeader("Authorization", "ApiKey " + userName
        + ":" + apiKey);
    // resourceUri = resourceUri + "?username=" + userName + "&api_key="
    // + apiKey;

    try {
      if (connectionTimeOut != null) {
        HttpConnectionManagerParams managerParams = httpClient
            .getHttpConnectionManager().getParams();
        managerParams.setConnectionTimeout(connectionTimeOut);
      }

      requestEntity = new StringRequestEntity(jsonRequest,
          "application/json", CHARSET);

      LOGGER.debug("requestContent: " + requestEntity.getContent());
      patchMethod.setRequestEntity(requestEntity);

      int statusCode = httpClient.executeMethod(patchMethod);
      LOGGER.debug("statusCode: " + statusCode);
      if (statusCode != HttpStatus.SC_ACCEPTED) {
        // fail
        errorMsg = patchMethod.getResponseBodyAsString();
        LOGGER.debug("errormsg: " + errorMsg);
        throw new Exception(errorMsg);
      }
    }
    catch (Exception e) {
      LOGGER.debug(
          "patch methond [" + resourceUri + "] execution errors", e);
      errorMsg = errorMsg == null ? "patch methond [" + resourceUri
          + "] execution errors" : "patch methond [" + resourceUri
          + "] execution errors, reasons:(" + errorMsg + ")";
      new Exception(errorMsg, e);
    }
    finally {
      // Release the connection.
      LOGGER.debug("release connection for patch.");
      patchMethod.releaseConnection();
    }

  }

  public String getRootUri() {
    return rootUri;
  }

  public void setRootUri(String rootUri) {
    this.rootUri = rootUri;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public Integer getExtractLimit() {
    return extractLimit;
  }

  public void setExtractLimit(Integer extractLimit) {
    this.extractLimit = extractLimit;
  }

  public Integer getConnectionTimeOut() {
    return connectionTimeOut;
  }

}
