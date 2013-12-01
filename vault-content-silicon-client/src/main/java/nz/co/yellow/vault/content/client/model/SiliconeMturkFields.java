package nz.co.yellow.vault.content.client.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

@SuppressWarnings("serial")
public class SiliconeMturkFields
    implements Serializable {

  private String yellowListingId;

  private String associations;

  private String description;

  private String document;

  private String email;

  private String exported;

  private String faxNumber;

  private String freeNumber;

  private String hours;

  private String images;

  private String keywords;

  private String logo;

  private String mobileNumber;

  private String parking;

  private String postalAddress;

  private String resourceUri;

  private String secondaryNumber;

  private String since;

  private String video;

  public String getExported() {
    return exported;
  }

  public void setExported(String exported) {
    this.exported = exported;
  }

  public String getAssociations() {
    return associations;
  }

  public void setAssociations(String associations) {
    this.associations = associations;
  }

  public String getDocument() {
    return document;
  }

  public void setDocument(String document) {
    this.document = document;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getHours() {
    return hours;
  }

  public void setHours(String hours) {
    this.hours = hours;
  }

  public String getImages() {
    return images;
  }

  public void setImages(String images) {
    this.images = images;
  }

  public String getKeywords() {
    return keywords;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public String getLogo() {
    return logo;
  }

  public void setLogo(String logo) {
    this.logo = logo;
  }

  public String getYellowListingId() {
    return yellowListingId;
  }

  public void setYellowListingId(String yellowListingId) {
    this.yellowListingId = yellowListingId;
  }

  public String getFaxNumber() {
    return faxNumber;
  }

  public void setFaxNumber(String faxNumber) {
    this.faxNumber = faxNumber;
  }

  public String getFreeNumber() {
    return freeNumber;
  }

  public void setFreeNumber(String freeNumber) {
    this.freeNumber = freeNumber;
  }

  public String getMobileNumber() {
    return mobileNumber;
  }

  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  public String getPostalAddress() {
    return postalAddress;
  }

  public void setPostalAddress(String postalAddress) {
    this.postalAddress = postalAddress;
  }

  public String getResourceUri() {
    return resourceUri;
  }

  public void setResourceUri(String resourceUri) {
    this.resourceUri = resourceUri;
  }

  public String getSecondaryNumber() {
    return secondaryNumber;
  }

  public void setSecondaryNumber(String secondaryNumber) {
    this.secondaryNumber = secondaryNumber;
  }

  public String getParking() {
    return parking;
  }

  public void setParking(String parking) {
    this.parking = parking;
  }

  public String getSince() {
    return since;
  }

  public void setSince(String since) {
    this.since = since;
  }

  public String getVideo() {
    return video;
  }

  public void setVideo(String video) {
    this.video = video;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
