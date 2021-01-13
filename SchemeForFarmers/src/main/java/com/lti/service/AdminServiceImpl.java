package com.lti.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.apache.bcel.generic.ReturnaddressType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.dto.ApprovalBidDto;
import com.lti.dto.ApprovalSellRequestDto;
import com.lti.dto.DisplayBidDto;
import com.lti.dto.InsuranceDto;
import com.lti.entity.Admin;
import com.lti.entity.Bid;
import com.lti.entity.Bidder;
import com.lti.entity.Crop;
import com.lti.entity.Farmer;
import com.lti.entity.Insurance;
import com.lti.entity.SellRequest;
import com.lti.repository.AdminRepository;
import com.lti.repository.ApplyInsuranceRepository;
import com.lti.repository.BidRepository;
import com.lti.repository.BidderRepository;
import com.lti.repository.CropRepository;
import com.lti.repository.FarmerRepository;
import com.lti.repository.InsuranceRepository;
import com.lti.repository.SellRequestRepository;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	FarmerRepository farmerRepository;
	@Autowired
	BidderRepository bidderRepository;
	@Autowired
	SellRequestRepository sellRequestRepository;
	@Autowired
	BidRepository bidRepository;
	@Autowired
	EmailService emailService;
	@Autowired
	InsuranceRepository insuranceRepository;
	@Autowired
	CropRepository cropRepository;
	@Autowired
	ApplyInsuranceRepository applyInsuranceRepository;
	
	public long addOrUpdateAdmin(Admin admin) {
		return adminRepository.addOrUpdateAdmin(admin);
	}

	@Override
	public boolean isValidUser(String adminEmail, String adminPassword) {
		Admin admin = adminRepository.fetchAdminByEmailAndPassword(adminEmail, adminPassword);
		if (admin != null)
			return true;
		else
			return false;
	}

	public long approveSellRequest(long requestId) {
		SellRequest sellRequest = sellRequestRepository.updateSellRequestByRequestId(requestId);
		if (sellRequest != null) {
			if (sellRequest.getRequestId() > 0) {
				String subject = "SellRequest Approved";
				String email = sellRequest.getFarmer().getFarmerEmail();
				String text = "Hi " + sellRequest.getFarmer().getFarmerName() + "!! Your sellRequest for "
						+ sellRequest.getCrop().getCropName() + " is approved ";
				emailService.sendEmailForNewRegistration(email, text, subject);
				System.out.println("Email sent successfully");
			}
			return sellRequest.getRequestId();
		} else
			return 0;
	}

	public List<ApprovalSellRequestDto> fetchApprovalPendingSellRequest() {
		List<SellRequest> sellRequests = sellRequestRepository.fetchApprovalPendingSellRequest();
		List<ApprovalSellRequestDto> appSellReqDto = new ArrayList<ApprovalSellRequestDto>();
		for (SellRequest s : sellRequests) {
			ApprovalSellRequestDto approval = new ApprovalSellRequestDto();
			approval.setCropName(s.getCrop().getCropName());
			approval.setCropType(s.getCrop().getCropType());
			approval.setFarmerEmail(s.getFarmer().getFarmerEmail());
			approval.setQuantity(s.getQuantity());
			approval.setRequestId(s.getRequestId());
			appSellReqDto.add(approval);
		}
		return appSellReqDto;
	}

	public long rejectSellRequestApproval(long requestId) {
		SellRequest sellRequest = sellRequestRepository.removeSellRequestByRequestId(requestId);
		System.out.println("id: "+sellRequest.getRequestId());
		if (sellRequest != null) {
			if (sellRequest.getRequestId() > 0) {
				String subject = "Oops:-( SellRequest Rejected ";
				String email = sellRequest.getFarmer().getFarmerEmail();
				String text = "Hi " + sellRequest.getFarmer().getFarmerName() + "!! Your sellRequest for "
						+ sellRequest.getCrop().getCropName() + " is rejected ";
				emailService.sendEmailForNewRegistration(email, text, subject);
				System.out.println("Email sent successfully");
			}
			return sellRequest.getRequestId();
		}
		return 0;
	}

	public List<DisplayBidDto> viewBid() {
		List<Bid> bids = bidRepository.fetchAllBidsByApproveYes();
		List<DisplayBidDto> displayBidDto = new ArrayList<DisplayBidDto>();
		for (Bid b : bids) {
			SellRequest s=sellRequestRepository.fetchSellRequestByRequestId(b.getRequestId());
			DisplayBidDto disDto = new DisplayBidDto();
			disDto.setBidAmount(b.getBidAmount());
			disDto.setBidDate(b.getBidDate());
			disDto.setBidderEmail(b.getBidder().getBidderEmail());
			disDto.setCropName(b.getCrop().getCropName());
			disDto.setCropType(b.getCrop().getCropType());
			disDto.setBidId(b.getBidId());
			disDto.setFarmerEmail(s.getFarmer().getFarmerEmail());
			disDto.setRequestId(b.getRequestId());
			displayBidDto.add(disDto);
		}
		return displayBidDto;
	}

	public int sellCropToBidder(long bidId) {
		Bid bid = bidRepository.fetchBidByBidId(bidId);
		long requestId = bid.getRequestId();
		SellRequest newSellRequest = new SellRequest();
		SellRequest sellRequest = sellRequestRepository.fetchSellRequestByRequestIdWithApproveYes(requestId);
		if (sellRequest != null && bid!=null) {
			sellRequest.setStatus("sold");
			sellRequest.setBid(bid);
			sellRequest.setSoldDate(LocalDate.now());
			newSellRequest = sellRequestRepository.addOrUpdateSellRequest(sellRequest);
			if (newSellRequest != null) {
				String subject = "Congragulations!!";
				String email = newSellRequest.getBid().getBidder().getBidderEmail();
				String text = "Hi " + newSellRequest.getBid().getBidder().getBidderName()
						+ "!! Your bidRequest for the " + newSellRequest.getCrop().getCropName() + " is accepted. Rs. "
						+ newSellRequest.getBid().getBidAmount() * newSellRequest.getQuantity()
						+ " will be debited from your registered bank account";
				emailService.sendEmailForNewRegistration(email, text, subject);
				System.out.println("Bidder Email sent successfully");
				// for farmer
				String farmersubject = "Congragulations!!";
				String farmeremail = newSellRequest.getFarmer().getFarmerEmail();
				String farmertext = "Hi " + newSellRequest.getFarmer().getFarmerName() + "!! Your sellRequest for the "
						+ newSellRequest.getCrop().getCropName() + " is sold. Rs. "
						+ newSellRequest.getBid().getBidAmount() * newSellRequest.getQuantity()
						+ " will be credited to your registered bank account";
				emailService.sendEmailForNewRegistration(farmeremail, farmertext, farmersubject);
				System.out.println("Farmer Email sent successfully");
				return 1;
			}
		}
		return 0;
	}

	public List<ApprovalBidDto> fetchApprovalPendingBids() {
		List<Bid> bids = bidRepository.fetchBidsByBidApproveNo();
		List<ApprovalBidDto> appBidDto = new ArrayList<ApprovalBidDto>();
		for (Bid b : bids) {
			ApprovalBidDto appDto = new ApprovalBidDto();
			appDto.setBidId(b.getBidId());
			appDto.setBidderEmail(b.getBidder().getBidderEmail());
			appDto.setCropName(b.getCrop().getCropName());
			appDto.setCropType(b.getCrop().getCropType());
			appDto.setCurrentBidAmount(b.getBidAmount());
			appBidDto.add(appDto);
		}
		return appBidDto;
	}

	public long approveBid(long bidId) {
		Bid bid = bidRepository.updateBidBybidId(bidId);
		if (bid != null) {
			if (bid.getBidId() > 0) {
				String subject = "BidRequest approved!! ";
				String email = bid.getBidder().getBidderEmail();
				String text = "Hi " + bid.getBidder().getBidderName() + "!! Your bidRequest for "
						+ bid.getCrop().getCropName() + " is approved ";
				emailService.sendEmailForNewRegistration(email, text, subject);
				System.out.println("Email sent successfully");
			}
			return bid.getBidId();
		} else
			return 0;
	}

	public List<Farmer> fetchApprovalPendingFarmers() {
		return farmerRepository.fetchApprovalPendingFarmers();
	}

	public long approveFarmer(String farmerEmail) {
		Farmer farmer = farmerRepository.updateFarmerByEmail(farmerEmail);
		if (farmer != null) {
			String subject = "Account approved";
			String email = farmer.getFarmerEmail();
			String text = "Hi " + farmer.getFarmerName() + "!! Your account is approved";
			emailService.sendEmailForNewRegistration(email, text, subject);
			System.out.println("Email sent successfully");
			return farmer.getFarmerId();
		}
		return 0;
	}

	public List<Bidder> fetchApprovalPendingBidders() {
		return bidderRepository.fetchApprovalPendingBidders();
	}

	public long approveBidder(String bidderEmail) {
		Bidder bidder = bidderRepository.updateBidderByEmail(bidderEmail);
		if (bidder != null) {
			String subject = "Registeration successful";
			String email = bidder.getBidderEmail();
			String text = "Hi " + bidder.getBidderName() + "Your account is approved!";
			emailService.sendEmailForNewRegistration(email, text, subject);
			System.out.println("Email sent successfully");
			return bidder.getBidderId();
		}
		return 0;
	}

	public long rejectFarmer(String farmerEmail) {
		try {
			Farmer farmer = farmerRepository.fetchFarmerByEmail(farmerEmail);
			System.out.println("email: " + farmer.getFarmerEmail());
			long farmerId = farmerRepository.rejectFarmer(farmerEmail);
			if (farmer.getFarmerId() > 0) {
				String subject = "Sorry!! Account rejected";
				String email = farmer.getFarmerEmail();
				String text = "Hi " + farmer.getFarmerName() + "!! Your account is rejected";
				emailService.sendEmailForNewRegistration(email, text, subject);
				System.out.println("Email sent successfully");
			}
			return farmerId;
		} catch (Exception e) {
			return 0;
		}
	}

	public long rejectBidder(String bidderEmail) {
		try {
			Bidder bidder = bidderRepository.fetchBidderByEmail(bidderEmail);
			long bidderId = bidderRepository.rejectBidder(bidderEmail);
			if (bidder.getBidderId() > 0) {
				String subject = "Sorry! Account Rejec	ted";
				String email = bidder.getBidderEmail();
				String text = "Hi " + bidder.getBidderName() + "Your account is rejected!";
				emailService.sendEmailForNewRegistration(email, text, subject);
				System.out.println("Email sent successfully");
			}
			return bidderId;
		} catch (Exception e) {
			return 0;
		}
	}

	public String forgotPassword(String adminEmail) {
		Admin admin = adminRepository.fetchAdminByEmail(adminEmail);
		if (admin != null) {
			String subject = "Password Recovery";
			String email =admin.getAdminEmail();
			String text = "Hi !! Your password is " + admin.getAdminPassword();
			emailService.sendEmailForNewRegistration(email, text, subject);
			System.out.println("Email sent successfully");
			return admin.getAdminPassword();
		} else
			return null;
	}
	
	public long addOrUpdateInsurance(InsuranceDto insuranceDto)
	{
		Insurance insurance=new Insurance();
		Crop crop=cropRepository.findCropByCropNameAndCropType(insuranceDto.getCropName(), insuranceDto.getCropType());
		insurance.setCrop(crop);
		insurance.setInsuranceCompanyName(insuranceDto.getInsuranceCompanyName());
		insurance.setPremiumAmount(insuranceDto.getPremiumAmount());
		insurance.setSumInsuredPerHectare(insuranceDto.getSumInsuredPerHectare());
		Insurance newInsur=insuranceRepository.addOrUpdateInsurance(insurance);
		if(newInsur!=null)
			return newInsur.getInsuranceId();
		return 0;
	}
	public long approveInsurance(long policyNo) {
		return applyInsuranceRepository.updateInsuranceApproval(policyNo);
	}
	
	public long rejectInsuranceApproval(long policyNo) {
		return applyInsuranceRepository.rejectInsuranceApproval(policyNo);
	}
}
