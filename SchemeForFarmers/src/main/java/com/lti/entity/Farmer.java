package com.lti.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
public class Farmer {
	@Id
	@SequenceGenerator(name="seq_farmer",initialValue=200,allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq_farmer")
	long farmerId;
	String farmerName;
	String farmerContactNo;
	String farmerEmail;
	String farmerPassword;
	String farmerApprove;
	
	@OneToOne(mappedBy="farmer",cascade=CascadeType.ALL)
	FarmerAddress farmerAddress;
	
	@OneToOne(mappedBy="farmer",cascade=CascadeType.ALL)
	FarmerBank farmerBank;
	
	@OneToMany(mappedBy="farmer",cascade=CascadeType.ALL)
	List<SellRequest> sellRequest;
	
	@OneToOne(mappedBy="farmer",cascade=CascadeType.ALL)
	FarmerLand farmerLand;
	
	@OneToMany(mappedBy="farmer",cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	List<ApplyInsurance> applyInsurance;

	public long getFarmerId() {
		return farmerId;
	}

	public void setFarmerId(long farmerId) {
		this.farmerId = farmerId;
	}

	public String getFarmerName() {
		return farmerName;
	}

	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
	}

	public String getFarmerContactNo() {
		return farmerContactNo;
	}

	public void setFarmerContactNo(String farmerContactNo) {
		this.farmerContactNo = farmerContactNo;
	}

	public String getFarmerEmail() {
		return farmerEmail;
	}

	public void setFarmerEmail(String farmerEmail) {
		this.farmerEmail = farmerEmail;
	}

	public String getFarmerPassword() {
		return farmerPassword;
	}

	public void setFarmerPassword(String farmerPassword) {
		this.farmerPassword = farmerPassword;
	}

	public String getFarmerApprove() {
		return farmerApprove;
	}

	public void setFarmerApprove(String farmerApprove) {
		this.farmerApprove = farmerApprove;
	}

	public FarmerAddress getFarmerAddress() {
		return farmerAddress;
	}

	public void setFarmerAddress(FarmerAddress farmerAddress) {
		this.farmerAddress = farmerAddress;
	}

	public FarmerBank getFarmerBank() {
		return farmerBank;
	}

	public void setFarmerBank(FarmerBank farmerBank) {
		this.farmerBank = farmerBank;
	}
	
	@JsonIgnore
	public List<SellRequest> getSellRequest() {
		return sellRequest;
	}

	public void setSellRequest(List<SellRequest> sellRequest) {
		this.sellRequest = sellRequest;
	}

	public FarmerLand getFarmerLand() {
		return farmerLand;
	}

	public void setFarmerLand(FarmerLand farmerLand) {
		this.farmerLand = farmerLand;
	}

	public List<ApplyInsurance> getApplyInsurance() {
		return applyInsurance;
	}

	public void setApplyInsurance(List<ApplyInsurance> applyInsurance) {
		this.applyInsurance = applyInsurance;
	}
	
}
