package com.lti.service;

import java.util.List;

import com.lti.dto.SellRequestDto;
import com.lti.entity.Crop;
import com.lti.entity.SellRequest;

public interface SellRequestService {
	public long placeSellRequest(SellRequestDto sellRequestDto);
	public List<Crop> viewUnsoldCrops();
}
