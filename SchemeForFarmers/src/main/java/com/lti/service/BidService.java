package com.lti.service;

import java.util.List;

import com.lti.dto.DisplayRequestDto;

public interface BidService {
	public List<DisplayRequestDto> fetchApprovedSellRequest();
	public long placeBid(DisplayRequestDto displayRequestDto);

}
