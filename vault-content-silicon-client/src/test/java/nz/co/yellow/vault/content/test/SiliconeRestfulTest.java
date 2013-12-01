package nz.co.yellow.vault.content.test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.fest.assertions.Assertions.assertThat;

import java.io.InputStream;

import javax.annotation.Resource;

import nz.co.yellow.vault.content.client.model.SiliconeListResponse;
import nz.co.yellow.vault.content.common.PatchMethod;
import nz.co.yellow.vault.content.common.SiliconeUtils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.junit.Test;
import org.springframework.core.env.Environment;

import com.google.gson.Gson;

public class SiliconeRestfulTest {

	private static final String ROOT_URI = "http://silicon-api-services-v1.dev.ytech.co.nz";

	private static final String USER_NAME = "silicon";

	private static final String API_KEY = "3b8418b3823eb26a51943f041d8af959717cd49c";

	private static final Integer LIMIT = 5;

	private Long offset = null;

	@Resource
	private Environment environment;

	@Test
	public void testProcessList() throws Exception {
		String output = callProcessListing();
		assertNotNull(output);
		assertThat(output).isNotNull();
	}

//	@Test
//	public void testProcessListAndConvert() throws Exception {
//		String output = callProcessListing();
//		assertNotNull(output);
//
//		SiliconeListResponse siliconeListResponse = this
//				.toSiliconeListResponse(output);
//
//		assertThat(siliconeListResponse).isNotNull();
//		assertThat(siliconeListResponse.getObjects()).hasSize(5);
//
//		assertEquals(LIMIT.intValue(), siliconeListResponse.getObjects().size());
//	}

	private String callProcessListing() throws Exception {
		String accessUri = "/mturk/api/processed_listing/";
		String output = null;
		final HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(5000);
		GetMethod getMethod = new GetMethod(ROOT_URI + accessUri);

		try {
			getMethod.addRequestHeader("Content-Type",
					"application/json;charset=utf-8");
			NameValuePair userNameParam = new NameValuePair("username",
					USER_NAME);
			NameValuePair apiKeyParam = new NameValuePair("api_key", API_KEY);
			offset = offset == null ? 0 : offset;
			NameValuePair offsetParam = new NameValuePair("offset",
					String.valueOf(offset));
			NameValuePair limitParam = new NameValuePair("limit",
					String.valueOf(5));
			getMethod.setQueryString(new NameValuePair[] { userNameParam,
					apiKeyParam, offsetParam, limitParam });
			httpClient.executeMethod(getMethod);
			InputStream inputstream = getMethod.getResponseBodyAsStream();
			output = SiliconeUtils.getStringFromInputStream(inputstream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
			}
		}
		return output;

	}

	@Test
	public void testPatch() throws Exception {
		final String uri = "/mturk/api/processed_listing/13/";

		final HttpClient httpClient = new HttpClient();
		final PatchMethod patchMethod = new PatchMethod(ROOT_URI + uri);
		String msg = null;

		try {
			String JSON_STRING = "{\"exported\": \"True\"}";
			StringRequestEntity requestEntity = new StringRequestEntity(
					JSON_STRING, "application/json", "UTF-8");
			patchMethod.addRequestHeader("Authorization", "ApiKey "+USER_NAME+":"+API_KEY);
			patchMethod.setRequestEntity(requestEntity);

			httpClient.executeMethod(patchMethod);
			msg = patchMethod.getResponseBodyAsString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (patchMethod != null) {
				patchMethod.releaseConnection();
			}
		}

		assertThat(msg).isNotNull();
		patchMethod.releaseConnection();

	}

	private SiliconeListResponse toSiliconeListResponse(String jsonStr) {
		SiliconeListResponse result = null;
		Gson gson = new Gson();
		result = gson.fromJson(jsonStr, SiliconeListResponse.class);
		return result;
	}

}
