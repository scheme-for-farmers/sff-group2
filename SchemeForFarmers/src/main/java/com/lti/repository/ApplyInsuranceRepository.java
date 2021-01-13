package com.lti.repository;

import com.lti.entity.ApplyInsurance;

public interface ApplyInsuranceRepository {
	public ApplyInsurance addApplyInsurance(ApplyInsurance applyInsurance);
	public long updateInsuranceApproval(long policyNo);
	public long rejectInsuranceApproval(long policyNo);
}
