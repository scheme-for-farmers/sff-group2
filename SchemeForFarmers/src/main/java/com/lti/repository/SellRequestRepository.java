package com.lti.repository;

import java.util.List;

import com.lti.entity.SellRequest;

public interface SellRequestRepository {
	public List<SellRequest> soldDetails(String farmerEmail);
	public List<SellRequest> fetchApprovalPendingSellRequest();
	public SellRequest updateSellRequestByRequestId(long requestId);
	public SellRequest removeSellRequestByRequestId(long requestId);
	public SellRequest fetchSellRequestByRequestId(long requestId);
}
