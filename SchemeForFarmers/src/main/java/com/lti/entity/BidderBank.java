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
public class BidderBank {
	@Id
	@SequenceGenerator(name="seq_bidderBank",initialValue=10000,allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq_bidderBank")
	long bidderBankId;
	long accNo;
	String ifscCode;
	
	@OneToOne
	@JoinColumn(name="bidderId")
	Bidder bidder;

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
	public Bidder getBidder() {
		return bidder;
	}
	public void setBidder(Bidder bidder) {
		this.bidder = bidder;
	}

	public long getBidderBankId() {
		return bidderBankId;
	}

	public void setBidderBankId(long bidderBankId) {
		this.bidderBankId = bidderBankId;
	}
	
}
