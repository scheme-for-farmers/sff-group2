package com.lti.dto;

import java.util.List;

public class MarketPlaceDto {
	double basePrice;
	double currentBidAmount;
	String cropType;
	String cropName;
	List<Double> previousBids;
	public double getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}
	public double getCurrentBidAmount() {
		return currentBidAmount;
	}
	public void setCurrentBidAmount(double currentBidAmount) {
		this.currentBidAmount = currentBidAmount;
	}
	public List<Double> getPreviousBids() {
		return previousBids;
	}
	public void setPreviousBids(List<Double> previousBids) {
		this.previousBids = previousBids;
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
	
}
