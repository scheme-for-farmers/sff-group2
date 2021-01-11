package com.lti.service;

import com.lti.dto.SellRequestDto;
import com.lti.entity.SellRequest;

public interface SellRequestService {
	public long placeSellRequest(SellRequestDto sellRequestDto);

}
