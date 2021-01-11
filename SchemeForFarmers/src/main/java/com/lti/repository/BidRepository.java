package com.lti.repository;

import java.util.List;

public interface BidRepository {
	public double findMaximumBidAmount(long cropId);
	public List<Double> previousBidsByCropId(long cropId);
}
