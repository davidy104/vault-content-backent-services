package nz.co.yellow.vault.content.client.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SiliconeListResponse {

  private SiliconeMeta meta;

  private List<SiliconeMturkFields> objects = new ArrayList<SiliconeMturkFields>();

  public SiliconeMeta getMeta() {
    return meta;
  }

  public void setMeta(SiliconeMeta meta) {
    this.meta = meta;
  }

  public List<SiliconeMturkFields> getObjects() {
    return objects;
  }

  public void setObjects(List<SiliconeMturkFields> objects) {
    this.objects = objects;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
