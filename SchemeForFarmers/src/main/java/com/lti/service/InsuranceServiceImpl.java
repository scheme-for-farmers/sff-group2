package com.lti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.dto.CalculateInsuranceDto;
import com.lti.entity.Crop;
import com.lti.repository.CropRepository;
@Service
public class InsuranceServiceImpl implements InsuranceService {
	@Autowired
	CropRepository cropRepository;

	@Override
	public CalculateInsuranceDto calculate(String cropName, String cropType, double Area) {
		Crop crop=cropRepository.findCropByCropNameAndCropType(cropName, cropType);
//		Insurance insurance;
		return null;
	}

}
