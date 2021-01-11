package com.lti.dto;

public class ApprovalBidDto {
	long bidId;
	String cropType;
	String cropName;
	String bidderEmail;
	double currentBidAmount;
	public long getBidId() {
		return bidId;
	}
	public void setBidId(long bidId) {
		this.bidId = bidId;
	}
	public String getCropType() {
		return cropType;
	}
	public void setCropType(String cropType) {
		this.cropType = cropType;
	}
	public String getCropName() {
		return cropName;
	}
	public void setCropName(String cropName) {
		this.cropName = cropName;
	}
	public String getBidderEmail() {
		return bidderEmail;
	}
	public void setBidderEmail(String bidderEmail) {
		this.bidderEmail = bidderEmail;
	}
	public double getCurrentBidAmount() {
		return currentBidAmount;
	}
	public void setCurrentBidAmount(double currentBidAmount) {
		this.currentBidAmount = currentBidAmount;
	}
	
	
}
