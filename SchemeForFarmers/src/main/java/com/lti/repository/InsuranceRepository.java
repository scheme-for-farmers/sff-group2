package com.lti.repository;

import java.util.List;

import com.lti.entity.Crop;
import com.lti.entity.Insurance;

public interface InsuranceRepository {
	public Insurance addOrUpdateInsurance(Insurance insurance);
	public Insurance findInsuranceByCrop(Crop crop);
	public List<Insurance> viewAllInsurance();
	public long deleteInsurance(long insuranceId);
	public Insurance findInsuranceByInsuranceId(long insuranceId);
}
