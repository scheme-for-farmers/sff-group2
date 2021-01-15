package com.lti.service;

import java.time.LocalDate;
import java.util.List;

import com.lti.dto.CalculateInsuranceDto;
import com.lti.dto.InsuranceInputDto;
import com.lti.entity.ApplyInsurance;
import com.lti.entity.Insurance;

public interface InsuranceService {
	public CalculateInsuranceDto calculate(InsuranceInputDto inputDto);
	public long applyInsurance(CalculateInsuranceDto calculateInsuranceDto);
	public long claimInsurance(long policyNo,String causeOfClaim,LocalDate dateOfLoss);
	public List<ApplyInsurance> fetchPendingClaimInsurance();
	public List<Insurance> viewAllInsurance();
	public long deleteInsurance(long insuranceId);
}
