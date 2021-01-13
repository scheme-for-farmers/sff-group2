package com.lti.dto;

public class InsuranceDto {
	double premiumAmount;
	double sumInsuredPerHectare;
	String insuranceCompanyName;
	String cropType;
	String cropName;
	public double getPremiumAmount() {
		return premiumAmount;
	}
	public void setPremiumAmount(double premiumAmount) {
		this.premiumAmount = premiumAmount;
	}
	public double getSumInsuredPerHectare() {
		return sumInsuredPerHectare;
	}
	public void setSumInsuredPerHectare(double sumInsuredPerHectare) {
		this.sumInsuredPerHectare = sumInsuredPerHectare;
	}
	public String getInsuranceCompanyName() {
		return insuranceCompanyName;
	}
	public void setInsuranceCompanyName(String insuranceCompanyName) {
		this.insuranceCompanyName = insuranceCompanyName;
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
