package nz.co.yellow.vault.content.data;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SiliconeMturkModel implements Serializable {

	private Integer yellowListId;

	private String email = "";

	private String fax = "";

	private String freeNumber = "";

	private String mobileNumber = "";

	private String packing = "";

	private String website = "";

	private String secondaryNumber = "";

	private Integer since = 0;

	private String resourceUri;

	private String associations = "";

	private String description = "";

	private String document = "";

	private String exported = "";

	private String hours = "";

	private String images = "";

	private String keywords = "";

	private String logo = "";

	private String postalAddress = "";

	private String video = "";

	public Integer getYellowListId() {
		return yellowListId;
	}

	public void setYellowListId(Integer yellowListId) {
		this.yellowListId = yellowListId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
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

	public String getPacking() {
		return packing;
	}

	public void setPacking(String packing) {
		this.packing = packing;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getSecondaryNumber() {
		return secondaryNumber;
	}

	public void setSecondaryNumber(String secondaryNumber) {
		this.secondaryNumber = secondaryNumber;
	}

	public Integer getSince() {
		return since;
	}

	public void setSince(Integer since) {
		this.since = since;
	}

	public String getResourceUri() {
		return resourceUri;
	}

	public void setResourceUri(String resourceUri) {
		this.resourceUri = resourceUri;
	}

	public String getAssociations() {
		return associations;
	}

	public void setAssociations(String associations) {
		this.associations = associations;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getExported() {
		return exported;
	}

	public void setExported(String exported) {
		this.exported = exported;
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

	public String getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	@Override
	public String toString() {
		return "SiliconeMturkModel [yellowListId=" + yellowListId + ", email="
				+ email + ", fax=" + fax + ", freeNumber=" + freeNumber
				+ ", mobileNumber=" + mobileNumber + ", packing=" + packing
				+ ", website=" + website + ", secondaryNumber="
				+ secondaryNumber + ", since=" + since + ", resourceUri="
				+ resourceUri + ", associations=" + associations
				+ ", description=" + description + ", document=" + document
				+ ", exported=" + exported + ", hours=" + hours + ", images="
				+ images + ", keywords=" + keywords + ", logo=" + logo
				+ ", postalAddress=" + postalAddress + ", video=" + video + "]";
	}

}
