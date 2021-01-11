package com.lti.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.lti.dto.DocumentDto;
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
	@Autowired
	EmailService emailService;

	public boolean checkDuplicate(String farmerEmail) {
		Farmer farmer = farmerRepository.fetchFarmerByEmail(farmerEmail);
		if (farmer == null)
			return true;
		return false;
	}

	public long registerFarmer(Farmer farmer) {
		boolean output = checkDuplicate(farmer.getFarmerEmail());
		if(output) {
			farmer.getFarmerAddress().setFarmer(farmer);
			farmer.getFarmerBank().setFarmer(farmer);
			farmer.getFarmerLand().setFarmer(farmer);
			farmer.setFarmerApprove("no");
			Farmer newFarmer = farmerRepository.addOrUpdateFarmer(farmer);
			if (newFarmer != null)
			{
				if(newFarmer.getFarmerId()>0)
				{
					String subject = "Registeration successful";
					String email =newFarmer.getFarmerEmail();
					String text = "Hi " + newFarmer.getFarmerName()+ 
							"!! Your registeration Id is " + newFarmer.getFarmerId()+" waiting for approval";
					emailService.sendEmailForNewRegistration(email, text, subject);
					System.out.println("Email sent successfully");
					return newFarmer.getFarmerId();
				}
			}
		}
		return 0; 
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
		if (crop != null) {
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
		if (sellRequest.size() != 0) {
			for (SellRequest s : sellRequest) {
				SoldHistoryDto sold = new SoldHistoryDto();
				sold.setBasePrice(s.getCrop().getBasePrice());
				sold.setBidAmount(s.getBid().getBidAmount());
				sold.setBidderEmail(s.getBid().getBidder().getBidderEmail());
				sold.setCropName(s.getCrop().getCropName());
				sold.setCropType(s.getCrop().getCropType());
				sold.setQuantity(s.getQuantity());
				sold.setSoldDate(s.getBid().getBidDate());
				sold.setTotalPrice(s.getBid().getBidAmount() * s.getQuantity());
				soldHistory.add(sold);
			}
		}
		return soldHistory;
	}

	public String forgotPassword(String farmerEmail) {
		Farmer farmer = farmerRepository.fetchFarmerByEmail(farmerEmail);
		if (farmer != null)
			return farmer.getFarmerPassword();
		else
			return null;
	}
	
	
	public long uploadDocument(DocumentDto documentDto) {
		String farmerMail=documentDto.getMail();
		Farmer farmer=farmerRepository.fetchFarmerByEmail(farmerMail);
		String imgUploadLocation = "f:/uploads/";
		String uploadedFileName = documentDto.getPancard().getOriginalFilename();
		String newFileName = farmer.getFarmerId() + "-" + uploadedFileName;
		String targetFileName = imgUploadLocation + newFileName;
		try {
			FileCopyUtils.copy(documentDto.getPancard().getInputStream(), new FileOutputStream(targetFileName));
			farmer.setFarmerPan(newFileName);
			return farmerRepository.addOrUpdateFarmer(farmer).getFarmerId();
		} catch(IOException e) {
			return 0;
		}
	}
	
}