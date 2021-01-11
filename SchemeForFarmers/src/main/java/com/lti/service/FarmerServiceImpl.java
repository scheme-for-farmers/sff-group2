package com.lti.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.dto.MarketPlaceDto;
import com.lti.dto.SoldHistoryDto;
import com.lti.entity.Crop;
import com.lti.entity.Farmer;
import com.lti.entity.SellRequest;
import com.lti.repository.BidRepository;
import com.lti.repository.BidderRepository;
import com.lti.repository.CropRepository;
import com.lti.repository.FarmerRepository;
import com.lti.repository.SellRequestRepository;

@Service
public class FarmerServiceImpl implements FarmerService {

	@Autowired
	FarmerRepository farmerRepository;
	@Autowired
	SellRequestRepository sellRequestRepository;
	@Autowired
	CropRepository cropRepository;
	@Autowired
	BidRepository bidRepository;
	
	public long registerFarmer(Farmer farmer) {
		farmer.getFarmerAddress().setFarmer(farmer);
		farmer.getFarmerBank().setFarmer(farmer);
		farmer.getFarmerLand().setFarmer(farmer);
		farmer.setFarmerApprove("no");
		Farmer newFarmer = farmerRepository.addOrUpdateFarmer(farmer);
		if (newFarmer != null)
			return newFarmer.getFarmerId(); // sending id
		return 0; // else 0

	}

	public String isValidUser(String farmerEmail, String farmerPassword) {
		Farmer farmer = farmerRepository.isValidUser(farmerEmail, farmerPassword);
		if (farmer != null) {
			if (farmer.getFarmerApprove().equals("yes"))
				return "valid";
			else
				return "pending";
		} else
			return "invalid";
	}
	public List<MarketPlaceDto> viewMarketPlace(String cropName, String cropType) {
		Crop crop = cropRepository.findCropByCropNameAndCropType(cropName, cropType);
		List<MarketPlaceDto> marketplace = new ArrayList<MarketPlaceDto>();
		if(crop!=null) {
			double currentBidAmount = bidRepository.findMaximumBidAmount(crop.getCropId());
			List<Double> previousBids = bidRepository.previousBidsByCropId(crop.getCropId());
			MarketPlaceDto mdto = new MarketPlaceDto();
			mdto.setBasePrice(crop.getBasePrice());
			mdto.setCurrentBidAmount(currentBidAmount);
			mdto.setPreviousBids(previousBids);
			marketplace.add(mdto);
		}
		return marketplace;
	}
	public List<SoldHistoryDto> viewSoldHistory(String farmerEmail) {
		List<SoldHistoryDto> soldHistory = new ArrayList<SoldHistoryDto>();
		List<SellRequest> sellRequest = sellRequestRepository.soldDetails(farmerEmail);
		if(sellRequest.size()!=0) {
			for(SellRequest s : sellRequest) {
				SoldHistoryDto sold = new SoldHistoryDto();
				sold.setBasePrice(s.getCrop().getBasePrice());
				sold.setBidAmount(s.getBid().getBidAmount());
				sold.setBidderEmail(s.getBid().getBidder().getBidderEmail());
				sold.setCropName(s.getCrop().getCropName());
				sold.setCropType(s.getCrop().getCropType());
				sold.setQuantity(s.getQuantity());
				sold.setSoldDate(s.getBid().getBidDate());
				sold.setTotalPrice(s.getBid().getBidAmount()*s.getQuantity());
				soldHistory.add(sold);
			}
		}
		return soldHistory;
	}
}