package com.lti.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.dto.ApprovalSellRequestDto;
import com.lti.entity.Admin;
import com.lti.entity.SellRequest;
import com.lti.repository.AdminRepository;
import com.lti.repository.SellRequestRepository;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	SellRequestRepository sellRequestRepository;
	
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
	public long approveSellRequest(long requestId)
	{
		SellRequest sellRequest=sellRequestRepository.updateSellRequestByRequestId(requestId);
		if(sellRequest!=null)
		{
			return sellRequest.getRequestId();
		}
		else
			return 0;
	}
	public List<ApprovalSellRequestDto> fetchApprovalPendingSellRequest() {
		List<SellRequest> sellRequests = sellRequestRepository.fetchApprovalPendingSellRequest(); 
		List<ApprovalSellRequestDto> appSellReqDto =new ArrayList<ApprovalSellRequestDto>();
		for(SellRequest s : sellRequests) {
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
		SellRequest sellRequest=sellRequestRepository.removeSellRequestByRequestId(requestId);
		if(sellRequest!=null)
			return sellRequest.getRequestId();
		return 0;
	}
}
