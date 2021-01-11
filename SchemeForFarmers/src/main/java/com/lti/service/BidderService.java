package com.lti.service;

import com.lti.entity.Bidder;

public interface BidderService {
	public long registerBidder(Bidder bidder);
	public String isValidUser(String bidderEmail,String bidderPassword);

}
