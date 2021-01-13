package com.lti.service;

import com.lti.dto.CalculateInsuranceDto;

public interface InsuranceService {
	public CalculateInsuranceDto calculate(String cropName,String cropType,double Area);

}
