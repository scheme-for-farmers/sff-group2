package com.lti.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class FarmerLand {
	@Id
	@SequenceGenerator(name="seq_farmerLand",initialValue=3000,allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq_farmerLand")
	long landId;
	String area;
	String address;
	String pincode;
	
	@OneToOne
	@JoinColumn(name="farmerId")
	Farmer farmer;

	public long getLandId() {
		return landId;
	}

	public void setLandId(long landId) {
		this.landId = landId;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	@JsonIgnore
	public Farmer getFarmer() {
		return farmer;
	}

	public void setFarmer(Farmer farmer) {
		this.farmer = farmer;
	}
	
	
}
