package com.lti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lti.entity.Bidder;
import com.lti.repository.BidderRepository;

@Service
public class BidderServiceImpl implements BidderService {
	@Autowired
	BidderRepository bidderRepository;
	
	public long registerBidder(Bidder bidder) {
		bidder.setBidderApprove("no".toLowerCase());
        bidder.getBidderAddress().setBidder(bidder);
        bidder.getBidderBank().setBidder(bidder);
        Bidder newBidder=bidderRepository.addOrUpdateBidder(bidder);
		if(newBidder!=null)
		{
			return newBidder.getBidderId();
		}
		return 0;
	}

}
