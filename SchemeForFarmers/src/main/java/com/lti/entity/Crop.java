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
public class Crop {
	@Id
	@SequenceGenerator(name="seq_crop",initialValue=300,allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq_crop")
	long CropId;
	String CropName;
	String CropType;
	double basePrice;
	
	@OneToMany(mappedBy="crop",cascade=CascadeType.ALL)
	List<SellRequest> sellRequest;
	
	@OneToMany(mappedBy="crop",cascade=CascadeType.ALL)
	@JsonIgnore
	List<Bid> bid;
	
	@OneToOne(mappedBy="crop",cascade=CascadeType.ALL)
	@JsonIgnore
	Insurance insurance;
	
	@OneToOne(mappedBy="crop",cascade=CascadeType.ALL)
	@JsonIgnore
	ApplyInsurance applyInsurance;

	public long getCropId() {
		return CropId;
	}

	public void setCropId(long cropId) {
		CropId = cropId;
	}

	public String getCropName() {
		return CropName;
	}

	public void setCropName(String cropName) {
		CropName = cropName;
	}

	public String getCropType() {
		return CropType;
	}

	public void setCropType(String cropType) {
		CropType = cropType;
	}

	public double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}
	
	@JsonIgnore
	public List<SellRequest> getSellRequest() {
		return sellRequest;
	}

	public void setSellRequest(List<SellRequest> sellRequest) {
		this.sellRequest = sellRequest;
	}

	public List<Bid> getBid() {
		return bid;
	}

	public void setBid(List<Bid> bid) {
		this.bid = bid;
	}

	public Insurance getInsurance() {
		return insurance;
	}

	public void setInsurance(Insurance insurance) {
		this.insurance = insurance;
	}

	public ApplyInsurance getApplyInsurance() {
		return applyInsurance;
	}

	public void setApplyInsurance(ApplyInsurance applyInsurance) {
		this.applyInsurance = applyInsurance;
	}
	
	
	
}
