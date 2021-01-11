package com.lti.repository;

import java.util.List;

import com.lti.entity.Farmer;

public interface FarmerRepository {
	public Farmer addOrUpdateFarmer(Farmer farmer);

	public Farmer isValidUser(String farmerEmail, String farmerPassword);

	public Farmer fetchFarmerByEmail(String farmerEmail);

	public List<Farmer> fetchApprovalPendingFarmers();

	public Farmer updateFarmerByEmail(String farmerEmail);

	public long rejectFarmer(String farmerEmail);

}
