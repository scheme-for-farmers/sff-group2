package com.lti.service;

import java.util.List;

import com.lti.dto.ApprovalBidDto;
import com.lti.dto.ApprovalSellRequestDto;
import com.lti.dto.DisplayBidDto;
import com.lti.entity.Admin;

public interface AdminService {
	public long addOrUpdateAdmin(Admin admin);
	public boolean isValidUser(String adminEmail,String adminPassword);
	public List<ApprovalSellRequestDto> fetchApprovalPendingSellRequest();
	public long approveSellRequest(long requestId);
	public long rejectSellRequestApproval(long requestId);
	public List<DisplayBidDto> viewBid();
	public String sellCropToBidder(long bidId);
	public List<ApprovalBidDto> fetchApprovalPendingBids();
	public long approveBid(long bidId);
}
