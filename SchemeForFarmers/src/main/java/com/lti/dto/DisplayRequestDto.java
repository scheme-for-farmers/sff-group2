package com.lti.dto;

public class DisplayRequestDto {
	long requestId;
	String cropType;
	String cropName;
	String farmerEmail;
	String bidderEmail;
	double quantity;
	double currentBidAmount;
	public long getRequestId() {
		return requestId;
	}
	public void setRequestId(long requestId) {
		this.requestId = requestId;
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
	
	public String getFarmerEmail() {
		return farmerEmail;
	}
	public void setFarmerEmail(String farmerEmail) {
		this.farmerEmail = farmerEmail;
	}
	public String getBidderEmail() {
		return bidderEmail;
	}
	public void setBidderEmail(String bidderEmail) {
		this.bidderEmail = bidderEmail;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public double getCurrentBidAmount() {
		return currentBidAmount;
	}
	public void setCurrentBidAmount(double currentBidAmount) {
		this.currentBidAmount = currentBidAmount;
	}
	
}
