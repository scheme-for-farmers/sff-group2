package com.lti.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class ApplyInsurance {
	@Id
	@SequenceGenerator(name="seq_insuranceApplied",initialValue=6000,allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq_insuranceApplied")
	long policyNo;
	double premiumAmount;
	double area;
	double totalsumInsured;
	String approve;
	String cropName;
	String insuranceStatus;
	String causeOfClaim;
	LocalDate dateOfLoss;
	
	@ManyToOne
	@JoinColumn(name="insuranceId")
	Insurance insurance;
	
	@OneToOne
	@JoinColumn(name="requestId")
	SellRequest sellRequest;

	public long getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(long policyNo) {
		this.policyNo = policyNo;
	}

	public double getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(double premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public double getTotalsumInsured() {
		return totalsumInsured;
	}

	public void setTotalsumInsured(double totalsumInsured) {
		this.totalsumInsured = totalsumInsured;
	}

	public String getApprove() {
		return approve;
	}

	public void setApprove(String approve) {
		this.approve = approve;
	}

	public Insurance getInsurance() {
		return insurance;
	}

	public void setInsurance(Insurance insurance) {
		this.insurance = insurance;
	}
	public SellRequest getSellRequest() {
		return sellRequest;
	}

	public void setSellRequest(SellRequest sellRequest) {
		this.sellRequest = sellRequest;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	public String getInsuranceStatus() {
		return insuranceStatus;
	}

	public void setInsuranceStatus(String insuranceStatus) {
		this.insuranceStatus = insuranceStatus;
	}

	public String getCauseOfClaim() {
		return causeOfClaim;
	}

	public void setCauseOfClaim(String causeOfClaim) {
		this.causeOfClaim = causeOfClaim;
	}

	public LocalDate getDateOfLoss() {
		return dateOfLoss;
	}

	public void setDateOfLoss(LocalDate dateOfLoss) {
		this.dateOfLoss = dateOfLoss;
	}

}
