package com.lti.service;

import com.lti.dto.DocumentDto;
import com.lti.entity.Bidder;

public interface BidderService {
	public long registerBidder(Bidder bidder);
	public String isValidUser(String bidderEmail,String bidderPassword);
	public String forgotPassword(String bidderEmail);
	public boolean checkDuplicate(String bidderEmail);
	//public long uploadDocument(DocumentDto documentDto);
}
