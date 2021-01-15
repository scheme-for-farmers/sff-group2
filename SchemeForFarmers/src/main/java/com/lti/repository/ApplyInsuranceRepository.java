package com.lti.repository;

import java.time.LocalDate;
import java.util.List;

import com.lti.entity.ApplyInsurance;

public interface ApplyInsuranceRepository {
	public ApplyInsurance addApplyInsurance(ApplyInsurance applyInsurance);
	public List<ApplyInsurance> pendingApprovalInsurance();
	public long updateInsuranceApproval(long policyNo,long requestId);
	public long rejectInsuranceApproval(long policyNo);
	public long claimInsurance(long policyNo,String causeOfClaim,LocalDate dateOfLoss);
	public ApplyInsurance fetchInsuranceByPolicyNo(long policyNo);
	public List<ApplyInsurance> fetchPendingclaimInsurance();
	public long approveclaimInsurance(long policyNo);
	public long rejectclaimInsurance(long policyNo);
}
