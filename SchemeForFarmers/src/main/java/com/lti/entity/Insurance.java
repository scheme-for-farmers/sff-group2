package com.lti.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Insurance {
	@Id
	@SequenceGenerator(name="seq_insurance",initialValue=5000,allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq_insurance")
	long insuranceId;
	double premiumAmount;
	double sumInsuredPerHectare;
	String insuranceCompanyName;
	
	@OneToOne
	@JoinColumn(name="cropId")
	Crop crop;
	
	@OneToMany(mappedBy="insurance",cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	List<ApplyInsurance> applyInsurance;

	public long getInsuranceId() {
		return insuranceId;
	}

	public void setInsuranceId(long insuranceId) {
		this.insuranceId = insuranceId;
	}

	
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
	
	public Crop getCrop() {
		return crop;
	}

	public void setCrop(Crop crop) {
		this.crop = crop;
	}

	public List<ApplyInsurance> getApplyInsurance() {
		return applyInsurance;
	}

	public void setApplyInsurance(List<ApplyInsurance> applyInsurance) {
		this.applyInsurance = applyInsurance;
	}
}
