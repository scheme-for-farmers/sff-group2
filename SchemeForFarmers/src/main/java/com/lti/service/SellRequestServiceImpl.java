package com.lti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.dto.SellRequestDto;
import com.lti.entity.Crop;
import com.lti.entity.Farmer;
import com.lti.entity.SellRequest;
import com.lti.repository.BidRepository;
import com.lti.repository.CropRepository;
import com.lti.repository.FarmerRepository;
import com.lti.repository.SellRequestRepository;

@Service
public class SellRequestServiceImpl implements SellRequestService {
	@Autowired
	SellRequestRepository sellRequestRepository;
	@Autowired
	FarmerRepository farmerRepository;
	@Autowired
	CropRepository cropRepository;
	@Autowired
	BidRepository bidRepository;
	public long placeSellRequest(SellRequestDto sellRequestDto)
	{
		try {
			SellRequest sellRequest = new SellRequest();
			Farmer farmer=farmerRepository.fetchFarmerByEmail(sellRequestDto.getFarmerEmail());
			Crop crop=cropRepository.findCropByCropNameAndCropType(sellRequestDto.getCropName(), sellRequestDto.getCropType());
			sellRequest.setApprove("no");
			sellRequest.setStatus("unsold");
			sellRequest.setFarmer(farmer);
			sellRequest.setCrop(crop);
			sellRequest.setQuantity(sellRequestDto.getQuantity());
			SellRequest newsellRequest = sellRequestRepository.addOrUpdateSellRequest(sellRequest);
			return newsellRequest.getRequestId();
		} catch (Exception e) {
			return 0;
		}
	}

}
