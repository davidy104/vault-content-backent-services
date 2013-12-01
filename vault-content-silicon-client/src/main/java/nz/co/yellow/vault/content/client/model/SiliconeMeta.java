package nz.co.yellow.vault.content.client.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SiliconeMeta {

  private Integer limit = 20;

  private String next;

  private String previous;

  private Long offset;

  private Long totalCount;

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public String getNext() {
    return next;
  }

  public void setNext(String next) {
    this.next = next;
  }

  public Long getOffset() {
    return offset;
  }

  public void setOffset(Long offset) {
    this.offset = offset;
  }

  public Long getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(Long totalCount) {
    this.totalCount = totalCount;
  }

  public String getPrevious() {
    return previous;
  }

  public void setPrevious(String previous) {
    this.previous = previous;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
