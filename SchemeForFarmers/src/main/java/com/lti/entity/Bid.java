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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Bid {
	@Id
	@SequenceGenerator(name="seq_bid",initialValue=500,allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq_bid")
	long bidId;
	double bidAmount;
	LocalDate bidDate;
	String bidApprove;
	String bidSold;
	
	@ManyToOne
	@JoinColumn(name="cropId")
	Crop crop;
	
	@ManyToOne
	@JoinColumn(name="bidderId")
	Bidder bidder;
	
	public long getBidId() {
		return bidId;
	}
	public void setBidId(long bidId) {
		this.bidId = bidId;
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
	public String getBidApprove() {
		return bidApprove;
	}
	public void setBidApprove(String bidApprove) {
		this.bidApprove = bidApprove;
	}
	public String getBidSold() {
		return bidSold;
	}
	public void setBidSold(String bidSold) {
		this.bidSold = bidSold;
	}
	public Crop getCrop() {
		return crop;
	}
	public void setCrop(Crop crop) {
		this.crop = crop;
	}
	public Bidder getBidder() {
		return bidder;
	}
	public void setBidder(Bidder bidder) {
		this.bidder = bidder;
	}
}
