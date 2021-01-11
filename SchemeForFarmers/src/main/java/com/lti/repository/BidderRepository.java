package com.lti.repository;

import com.lti.entity.Bidder;

public interface BidderRepository {
	public Bidder addOrUpdateBidder(Bidder bidder);
	public Bidder fetchBidderByEmailAndPassword(String bidderEmail,String bidderPassword);
	public Bidder fetchBidderByEmail(String bidderEmail);
}
