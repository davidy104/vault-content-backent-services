package nz.co.yellow.vault.content.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

import nz.co.yellow.vault.content.client.model.SiliconeListResponse;

import com.google.gson.Gson;

/**
 *
 * @author david
 *
 */
public class SiliconeUtils {

	public static String getStringFromInputStream(InputStream is)
			throws Exception {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			throw new Exception("BufferedReader read error", e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					throw new Exception("BufferedReader close error", e);
				}
			}
		}

		return sb.toString();

	}

	public static SiliconeListResponse toSiliconeListResponse(String jsonStr)
			throws Exception {
		SiliconeListResponse result = null;
		try {
			Gson gson = new Gson();
			result = gson.fromJson(jsonStr, SiliconeListResponse.class);
		} catch (RuntimeException e) {
			throw new Exception("Gson parse error", e);
		}
		return result;
	}

	public static String getErrorStack(Throwable e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}

	public static String getExceptionInfo(Throwable e, int length) {
		String error = getErrorStack(e);
		error = error.substring(0, length);
		return error;
	}

}
