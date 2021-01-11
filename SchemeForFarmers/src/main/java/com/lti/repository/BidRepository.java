package com.lti.repository;

import java.util.List;

import com.lti.entity.Bid;

public interface BidRepository {
	public Bid addOrUpdateBid(Bid bid);
	public double findMaximumBidAmount(long cropId);
	public List<Double> previousBidsByCropId(long cropId);
	public List<Bid> fetchAllBidsByApproveYes();
	public Bid fetchBidByBidId(long bidId);
	public List<Bid> fetchBidsByBidApproveNo();
	public Bid updateBidBybidId(long bidId);

}
