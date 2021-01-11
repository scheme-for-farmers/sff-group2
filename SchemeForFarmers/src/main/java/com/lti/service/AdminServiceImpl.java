package com.lti.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.dto.ApprovalBidDto;
import com.lti.dto.ApprovalSellRequestDto;
import com.lti.dto.DisplayBidDto;
import com.lti.entity.Admin;
import com.lti.entity.Bid;
import com.lti.entity.Bidder;
import com.lti.entity.Farmer;
import com.lti.entity.SellRequest;
import com.lti.repository.AdminRepository;
import com.lti.repository.BidRepository;
import com.lti.repository.BidderRepository;
import com.lti.repository.FarmerRepository;
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
		if (sellRequest != null)
			return sellRequest.getRequestId();
		return 0;
	}

	public List<DisplayBidDto> viewBid() {
		List<Bid> bids = bidRepository.fetchAllBidsByApproveYes();
		List<DisplayBidDto> displayBidDto = new ArrayList<DisplayBidDto>();
		for (Bid b : bids) {
			DisplayBidDto disDto = new DisplayBidDto();
			disDto.setBidAmount(b.getBidAmount());
			disDto.setBidDate(b.getBidDate());
			disDto.setBidderEmail(b.getBidder().getBidderEmail());
			disDto.setCropName(b.getCrop().getCropName());
			disDto.setCropType(b.getCrop().getCropType());
			disDto.setBidId(b.getBidId());
			disDto.setRequestId(b.getRequestId());
			displayBidDto.add(disDto);
		}
		return displayBidDto;
	}

	public String sellCropToBidder(long bidId) {
		Bid bid = bidRepository.fetchBidByBidId(bidId);
		long requestId = bid.getRequestId();
		SellRequest sellRequest = sellRequestRepository.fetchSellRequestByRequestId(requestId);
		sellRequest.setStatus("sold");
		sellRequest.setBid(bid);
		sellRequest.setSoldDate(LocalDate.now());
		SellRequest newSellRequest = sellRequestRepository.addOrUpdateSellRequest(sellRequest);
		if (newSellRequest != null)
			return "Sold";
		else
			return "unsold";
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
			return bid.getBidId();
		} else
			return 0;
	}

	public List<Farmer> fetchApprovalPendingFarmers() {
		return farmerRepository.fetchApprovalPendingFarmers();
	}

	public long approveFarmer(String farmerEmail) {
		Farmer farmer = farmerRepository.updateFarmerByEmail(farmerEmail);
		return farmer.getFarmerId();
	}

	public List<Bidder> fetchApprovalPendingBidders() {
		return bidderRepository.fetchApprovalPendingBidders();
	}

	public long approveBidder(String bidderEmail) {
		Bidder bidder = bidderRepository.updateBidderByEmail(bidderEmail);
		return bidder.getBidderId();
	}

	public long rejectFarmer(String farmerEmail) {
		return farmerRepository.rejectFarmer(farmerEmail);
	}

	public long rejectBidder(String bidderEmail) {
		return bidderRepository.rejectBidder(bidderEmail);
	}

	public String forgotPassword(String adminEmail) {
		Admin admin = adminRepository.fetchAdminByEmail(adminEmail);
		if (admin != null) {
			System.out.println(admin.getAdminPassword());
			return admin.getAdminPassword();
		} else
			return null;
	}
}
