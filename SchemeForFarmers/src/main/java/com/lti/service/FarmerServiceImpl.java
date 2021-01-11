package com.lti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.entity.Farmer;
import com.lti.repository.FarmerRepository;

@Service
public class FarmerServiceImpl implements FarmerService {
	
	
    
	@Autowired
	FarmerRepository farmerRepository;

	public long registerFarmer(Farmer farmer) {
		farmer.getFarmerAddress().setFarmer(farmer);
		farmer.getFarmerBank().setFarmer(farmer);
		farmer.getFarmerLand().setFarmer(farmer);
		farmer.setFarmerApprove("no");
		Farmer newFarmer=farmerRepository.addOrUpdateFarmer(farmer);
		if(newFarmer!=null)
		return newFarmer.getFarmerId(); //sending id
		return 0; //else 0

}
}