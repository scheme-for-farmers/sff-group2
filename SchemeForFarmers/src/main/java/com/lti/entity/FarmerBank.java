package com.lti.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
public class FarmerBank {
	@Id
	@SequenceGenerator(name="seq_farmerBank",initialValue=20000,allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq_farmerBank")
	long farmerBankId;
	long accNo;
	String ifscCode;
	
	@OneToOne
	@JoinColumn(name="farmerId")
	Farmer farmer;

	public long getAccNo() {
		return accNo;
	}

	public void setAccNo(long accNo) {
		this.accNo = accNo;
	}

	
	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	@JsonIgnore
	public Farmer getFarmer() {
		return farmer;
	}

	public void setFarmer(Farmer farmer) {
		this.farmer = farmer;
	}

	public long getFarmerBankId() {
		return farmerBankId;
	}

	public void setFarmerBankId(long farmerBankId) {
		this.farmerBankId = farmerBankId;
	}
}
