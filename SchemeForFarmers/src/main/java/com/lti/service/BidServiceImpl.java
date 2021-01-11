package com.lti.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.dto.DisplayRequestDto;
import com.lti.entity.Bid;
import com.lti.entity.Bidder;
import com.lti.entity.Crop;
import com.lti.entity.SellRequest;
import com.lti.repository.BidRepository;
import com.lti.repository.BidderRepository;
import com.lti.repository.CropRepository;
import com.lti.repository.SellRequestRepository;
@Service
public class BidServiceImpl implements BidService {
	
	@Autowired
	BidRepository bidRepository;
	@Autowired
	CropRepository cropRepository;
	@Autowired
	BidderRepository bidderRepository;
	@Autowired
	SellRequestRepository sellRequestRepository;
	public List<DisplayRequestDto> fetchApprovedSellRequest() {
		List<SellRequest> sellRequests = sellRequestRepository.fetchApprovedSellRequest(); 
		List<DisplayRequestDto> appSellReqDto =new ArrayList<DisplayRequestDto>();
		for(SellRequest s : sellRequests) {
			DisplayRequestDto approval = new DisplayRequestDto();
			approval.setCurrentBidAmount(bidRepository.findMaximumBidAmount(s.getCrop().getCropId()));
			approval.setCropName(s.getCrop().getCropName());
			approval.setCropType(s.getCrop().getCropType());
			approval.setEmail(s.getFarmer().getFarmerEmail());
			approval.setQuantity(s.getQuantity());
			approval.setRequestId(s.getRequestId());
			appSellReqDto.add(approval);
		}
		return appSellReqDto;
	}
	public long placeBid(DisplayRequestDto displayRequestDto) {
		System.out.println("hii");
		try {
			System.out.println(displayRequestDto.getCropName());
			Crop crop=cropRepository.findCropByCropNameAndCropType(displayRequestDto.getCropName(),displayRequestDto.getCropType());
			System.out.println(crop.getCropId());
			double maxBidAmount=bidRepository.findMaximumBidAmount(crop.getCropId());
			System.out.println(maxBidAmount);
			if(displayRequestDto.getCurrentBidAmount()>maxBidAmount)
			{
				System.out.println("hello ");
				Bid bid=new Bid();
				bid.setBidAmount(displayRequestDto.getCurrentBidAmount());
				bid.setBidDate(LocalDate.now());
				bid.setBidApprove("no");
				bid.setRequestId(displayRequestDto.getRequestId());
				Bidder bidder=bidderRepository.fetchBidderByEmail(displayRequestDto.getEmail());
				bid.setBidder(bidder);
				bid.setCrop(crop);
				Bid newbid=bidRepository.addOrUpdateBid(bid);
				return newbid.getBidId();
			}
			else {
				return 0;
			}
		} catch (Exception e) {
			return 0;
		}
	}

}
