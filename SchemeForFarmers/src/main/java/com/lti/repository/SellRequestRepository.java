package com.lti.repository;

import java.util.List;

import com.lti.entity.Crop;
import com.lti.entity.SellRequest;

public interface SellRequestRepository {
	public List<SellRequest> soldDetails(String farmerEmail);
	public List<SellRequest> fetchApprovalPendingSellRequest();
	public SellRequest updateSellRequestByRequestId(long requestId);
	public SellRequest removeSellRequestByRequestId(long requestId);
	public SellRequest fetchSellRequestByRequestId(long requestId);
	public List<SellRequest> fetchApprovedSellRequest();
	public SellRequest addOrUpdateSellRequest(SellRequest sellRequest);
	public SellRequest fetchSellRequestByRequestIdWithApproveYes(long requestId);
	public List<Crop> viewUnsoldCrops();
	public List<SellRequest> viewUnsoldCropsOfAFarmer(String farmerEmail);
}
