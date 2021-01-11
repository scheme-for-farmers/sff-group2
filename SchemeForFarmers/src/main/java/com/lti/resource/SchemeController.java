package com.lti.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lti.entity.Bidder;
import com.lti.entity.Farmer;
import com.lti.service.BidderService;
import com.lti.service.FarmerService;

@RestController
@CrossOrigin
public class SchemeController {

	@Autowired
	FarmerService farmerService;
	@Autowired
	BidderService bidderService;

	@RequestMapping(value = "/registerUser", method = RequestMethod.POST) // http://localhost:8080/sff/registerUser
	public long registerFarmer(@RequestBody Farmer farmer) {
		System.out.println("hello" + farmer.getFarmerBank().getIFSC_code());
		return farmerService.registerFarmer(farmer);
	}

@RequestMapping(value="/registerBidder",method=RequestMethod.POST)
public long registerBidder(@RequestBody Bidder bidder)
{
    return bidderService.registerBidder(bidder);
}
}