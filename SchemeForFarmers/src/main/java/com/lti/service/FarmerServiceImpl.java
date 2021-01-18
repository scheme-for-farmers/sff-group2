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

	public MarketPlaceDto viewMarketPlace(String cropName, String cropType) {
		Crop crop = cropRepository.findCropByCropNameAndCropType(cropName, cropType);
		MarketPlaceDto mdto = new MarketPlaceDto();
		double basePrice = crop.getBasePrice();
		double currentBidAmount = bidRepository.findMaximumBidAmount(crop.getCropId());
		if(currentBidAmount>basePrice) {
			mdto.setCurrentBidAmount(currentBidAmount);
		}
		else
			mdto.setCurrentBidAmount(basePrice);
		List<Double> previousBids = bidRepository.previousBidsByCropId(crop.getCropId());
		mdto.setBasePrice(crop.getBasePrice());
		mdto.setPreviousBids(previousBids);
		mdto.setCropName(cropName);
		mdto.setCropType(cropType);
		return mdto;
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
		if (farmer != null) {
			String subject = "Password Recovery!!";
			String email =farmer.getFarmerEmail();
			String text = "Hi " + farmer.getFarmerName()+ 
					"!! Your password is " + farmer.getFarmerPassword();
			emailService.sendEmailForNewRegistration(email, text, subject);
			System.out.println("Email sent successfully");
			return farmer.getFarmerPassword();
		}
		else
			return null;
	}
	
	public long uploadDocument(DocumentDto documentDto) {
		long id=documentDto.getId();
		String aadharCardUploadLocation = "d:/aadharUploads/";
		String panCardUploadLocation = "d:/panUploads/";
		String uploadedPanFileName= documentDto.getPancard().getOriginalFilename();
		String uploadedAadharFileName = documentDto.getAadharCard().getOriginalFilename();
		String newAadharFileName = id + "-" + uploadedAadharFileName;
		String newPanFileName = id + "-" + uploadedPanFileName;
		String targetAadharFileName = aadharCardUploadLocation + newAadharFileName;
		String targetPanFileName = panCardUploadLocation + newPanFileName;
		try {
		FileCopyUtils.copy(documentDto.getAadharCard().getInputStream(), new FileOutputStream(targetAadharFileName));
		FileCopyUtils.copy(documentDto.getPancard().getInputStream(), new FileOutputStream(targetPanFileName));
		} catch(IOException e) {
		e.printStackTrace(); //hoping no error would occur
		return 404;
		}
		Farmer farmer=farmerRepository.fetchFarmerById(id);
		farmer.setFarmerAadhar(newAadharFileName);
		farmer.setFarmerPan(newPanFileName);
		// userAndAdminService.uploadDocs(userId, newAadharFileName,newPanFileName);
		// Status status = new Status();
		// status.setStatus(StatusType.SUCCESS);
		// status.setMessage("Doccuments Uploaded");
		return farmerRepository.addOrUpdateFarmer(farmer).getFarmerId();
}
}