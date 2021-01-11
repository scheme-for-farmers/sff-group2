package com.lti.repository;

import com.lti.entity.Farmer;

public interface FarmerRepository {
	public Farmer addOrUpdateFarmer(Farmer farmer);
	public Farmer isValidUser(String farmerEmail,String farmerPassword);

}
