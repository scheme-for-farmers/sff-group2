package com.lti.repository;

import com.lti.entity.Crop;
import com.lti.entity.Insurance;

public interface InsuranceRepository {
	public Insurance addOrUpdateInsurance(Insurance insurance);
	public Insurance findInsuranceByCrop(Crop crop);

}
