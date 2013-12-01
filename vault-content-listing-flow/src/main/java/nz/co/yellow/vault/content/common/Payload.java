package nz.co.yellow.vault.content.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Payload {
  private Object body;

  private Map<String, Object> headers;

  private Map<String, Object> properties;

  public Object getBody() {
    return body;
  }

  public void setBody(Object body) {
    this.body = body;
  }

  public Map<String, Object> getHeaders() {
    return headers;
  }

  public void setHeaders(Map<String, Object> headers) {
    this.headers = headers;
  }

  public void addHeader(String headerKey, Object headerVal) {
    if (headers == null) {
      headers = new HashMap<String, Object>();
    }
    headers.put(headerKey, headerVal);
  }

  public void addProperty(String propKey, Object propVal) {
    if (properties == null) {
      properties = new HashMap<String, Object>();
    }
    properties.put(propKey, propVal);
  }

  public Map<String, Object> getProperties() {
    return properties;
  }

  public void setProperties(Map<String, Object> properties) {
    this.properties = properties;
  }

  public static Builder getBuilder(Object body) {
    return new Builder(body);
  }

  public static class Builder {

    private Payload built;

    public Builder(Object body) {
      built = new Payload();
      built.body = body;
    }

    public Payload build() {
      return built;
    }
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
