package com.lti.service;

import java.time.LocalDate;

import com.lti.dto.CalculateInsuranceDto;
import com.lti.dto.InsuranceInputDto;

public interface InsuranceService {
	public CalculateInsuranceDto calculate(InsuranceInputDto inputDto);
	public long applyInsurance(CalculateInsuranceDto calculateInsuranceDto);
	public long claimInsurance(long policyNo,String causeOfClaim,LocalDate dateOfLoss);
}
