package com.lti.entity;

import java.time.LocalDate;
import java.util.List;

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
public class Bid {
	@Id
	@SequenceGenerator(name="seq_bid",initialValue=500,allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq_bid")
	long bidId;
	double bidAmount;
	LocalDate bidDate;
	String bidApprove;
	long requestId;
	
	@ManyToOne
	@JoinColumn(name="cropId")
	@JsonIgnore
	Crop crop;
	
	@ManyToOne
	@JoinColumn(name="bidderId")
	Bidder bidder;
	
	@OneToMany(mappedBy = "bid",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	List<SellRequest> sellRequest;
	
	
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
	
	@JsonIgnore
	public List<SellRequest> getSellRequest() {
		return sellRequest;
	}
	public void setSellRequest(List<SellRequest> sellRequest) {
		this.sellRequest = sellRequest;
	}
	public long getRequestId() {
		return requestId;
	}
	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}
}
