package com.lti.service;

import java.util.List;

import com.lti.dto.ApprovalBidDto;
import com.lti.dto.ApprovalSellRequestDto;
import com.lti.dto.CalculateInsuranceDto;
import com.lti.dto.DisplayBidDto;
import com.lti.dto.InsuranceApproval;
import com.lti.dto.InsuranceDto;
import com.lti.entity.Admin;
import com.lti.entity.Bidder;
import com.lti.entity.Farmer;
import com.lti.entity.Insurance;

public interface AdminService {
	public long addOrUpdateAdmin(Admin admin);
	public boolean isValidUser(String adminEmail,String adminPassword);
	public List<ApprovalSellRequestDto> fetchApprovalPendingSellRequest();
	public long approveSellRequest(long requestId);
	public long rejectSellRequestApproval(long requestId);
	public List<DisplayBidDto> viewBid();
	public int sellCropToBidder(long bidId);
	//public List<ApprovalBidDto> fetchApprovalPendingBids();
	//public long approveBid(long bidId);
	public List<Farmer> fetchApprovalPendingFarmers();
    public long approveFarmer(String farmerEmail);
    public List<Bidder> fetchApprovalPendingBidders();
    public long approveBidder(String bidderEmail);
    public long rejectFarmer(String farmerEmail);
    public long rejectBidder(String bidderEmail);
    public String forgotPassword(String adminEmail);
    public long addOrUpdateInsurance(InsuranceDto insuranceDto);
    public List<InsuranceApproval> fetchApprovalPendingInsurance();
    public long approveInsurance(long policyNo,long requestId);
    public long rejectInsuranceApproval(long policyNo);
    public long approveClaimInsurance(long policyNo);
    public long rejectClaimInsurance(long policyNo);
}
