package com.lti.dto;

import java.time.LocalDate;

public class SoldHistoryDto {
	double basePrice;
	double totalPrice;
	double bidAmount;
	LocalDate soldDate;
	String cropName;
	String cropType;
	double quantity;
	String bidderEmail;
	public double getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public double getBidAmount() {
		return bidAmount;
	}
	public void setBidAmount(double bidAmount) {
		this.bidAmount = bidAmount;
	}
	public LocalDate getSoldDate() {
		return soldDate;
	}
	public void setSoldDate(LocalDate soldDate) {
		this.soldDate = soldDate;
	}
	public String getCropName() {
		return cropName;
	}
	public void setCropName(String cropName) {
		this.cropName = cropName;
	}
	public String getCropType() {
		return cropType;
	}
	public void setCropType(String cropType) {
		this.cropType = cropType;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public String getBidderEmail() {
		return bidderEmail;
	}
	public void setBidderEmail(String bidderEmail) {
		this.bidderEmail = bidderEmail;
	}
	
	
}
