package com.lti.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class SoldCrops {
	@Id
	@SequenceGenerator(name="seq_sold",initialValue=900,allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq_sold")
	long soldId;
	double bidAmount;
	LocalDate bidDate;
	long cropId;
	double quantity;
	String farmerEmail;
	//double totalAmount;
	public long getSoldId() {
		return soldId;
	}
	public void setSoldId(long soldId) {
		this.soldId = soldId;
	}
	public double getBidAmount() {
		return bidAmount;
	}
	public void setBidAmount(double bidAmount) {
		this.bidAmount = bidAmount;
	}
	public LocalDate getBidDate() {
		return bidDate;
	}
	public void setBidDate(LocalDate bidDate) {
		this.bidDate = bidDate;
	}
	public long getCropId() {
		return cropId;
	}
	public void setCropId(long cropId) {
		this.cropId = cropId;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public String getFarmerEmail() {
		return farmerEmail;
	}
	public void setFarmerEmail(String farmerEmail) {
		this.farmerEmail = farmerEmail;
	}
	/*public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}*/
	
	
	//soldHistoryRepository.add(bid.getBidAmount(),bid.getBidDate(),bid.getCrop().getCropId(),sellRequest.getQuantity(),sellRequest.getFarmer().getFarmerEmail());

}
