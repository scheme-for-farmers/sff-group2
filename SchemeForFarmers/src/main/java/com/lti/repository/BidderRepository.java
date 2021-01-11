package com.lti.repository;

import java.util.List;

import com.lti.entity.Bidder;

public interface BidderRepository {
	public Bidder addOrUpdateBidder(Bidder bidder);

	public Bidder fetchBidderByEmailAndPassword(String bidderEmail, String bidderPassword);

	public Bidder fetchBidderByEmail(String bidderEmail);

	public List<Bidder> fetchApprovalPendingBidders();

	public Bidder updateBidderByEmail(String bidderEmail);

	public long rejectBidder(String bidderEmail);

	public Bidder fetchBidderByEmailWithApproveYes(String bidderEmail);
}
