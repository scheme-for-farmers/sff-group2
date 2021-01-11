package com.lti.service;

import java.util.List;

import com.lti.dto.DocumentDto;
import com.lti.dto.MarketPlaceDto;
import com.lti.dto.SoldHistoryDto;
import com.lti.entity.Farmer;

public interface FarmerService {
	public String isValidUser(String farmerEmail, String farmerPassword);
	public long registerFarmer(Farmer farmer);
	public List<MarketPlaceDto> viewMarketPlace(String cropName, String cropType);
	public List<SoldHistoryDto> viewSoldHistory(String farmerEmail);
	public boolean checkDuplicate(String farmerEmail);
	public String forgotPassword(String farmerEmail);
	public long uploadDocument(DocumentDto documentDto);
}
