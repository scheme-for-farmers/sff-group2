package com.lti.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
public class SellRequest {
	@Id
	@SequenceGenerator(name="seq_request",initialValue=10,allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq_request")
	long requestId;
	double quantity;
	String approve;
	String status;
	LocalDate soldDate;
	
	@ManyToOne
	@JoinColumn(name="cropId")
	Crop crop;
	
	@ManyToOne
	@JoinColumn(name="farmerId")
	Farmer farmer;
	
	@ManyToOne
	@JoinColumn(name="bidId")
	Bid bid;
	
	@OneToOne(mappedBy = "sellRequest",cascade = CascadeType.ALL)
	ApplyInsurance applyInsurance;

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String getApprove() {
		return approve;
	}

	public void setApprove(String approve) {
		this.approve = approve;
	}

	public Crop getCrop() {
		return crop;
	}

	public void setCrop(Crop crop) {
		this.crop = crop;
	}

	public Farmer getFarmer() {
		return farmer;
	}

	public void setFarmer(Farmer farmer) {
		this.farmer = farmer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@JsonIgnore
	public Bid getBid() {
		return bid;
	}

	public void setBid(Bid bid) {
		this.bid = bid;
	}

	public LocalDate getSoldDate() {
		return soldDate;
	}

	public void setSoldDate(LocalDate soldDate) {
		this.soldDate = soldDate;
	}

	public ApplyInsurance getApplyInsurance() {
		return applyInsurance;
	}

	public void setApplyInsurance(ApplyInsurance applyInsurance) {
		this.applyInsurance = applyInsurance;
	}
}
