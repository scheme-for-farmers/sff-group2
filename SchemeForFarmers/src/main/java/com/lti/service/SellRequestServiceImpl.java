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
	@Autowired
	EmailService emailService;
	public long placeSellRequest(SellRequestDto sellRequestDto)
	{
		try {
			SellRequest sellRequest = new SellRequest();
			SellRequest newsellRequest = new SellRequest();
			Farmer farmer=farmerRepository.fetchFarmerByEmailWithApproveYes(sellRequestDto.getFarmerEmail());
			System.out.println("farmer: "+ farmer.getFarmerEmail());
			Crop crop=cropRepository.findCropByCropNameAndCropType(sellRequestDto.getCropName(), sellRequestDto.getCropType());
			if(crop!=null && farmer!=null) {
				sellRequest.setApprove("no");
				sellRequest.setStatus("unsold");
				sellRequest.setFarmer(farmer);
				sellRequest.setCrop(crop);
				sellRequest.setQuantity(sellRequestDto.getQuantity());
				newsellRequest = sellRequestRepository.addOrUpdateSellRequest(sellRequest);
			}
			if (newsellRequest != null) {
				if(newsellRequest.getRequestId()>0)
				{
					String subject = "Registeration successful";
		            String email =newsellRequest.getFarmer().getFarmerEmail();
		            String text = "Hi " + newsellRequest.getFarmer().getFarmerName()+ "!! Your sellRequest is placed for " 
		            + newsellRequest.getCrop().getCropName()+"!!. And your request id is "+newsellRequest.getRequestId()+" waiting for approval";
		            emailService.sendEmailForNewRegistration(email, text, subject);
		            System.out.println("Email sent successfully");
				} 
				 return newsellRequest.getRequestId();
			}
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}

}
