package com.lti.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lti.entity.Crop;
import com.lti.entity.Insurance;
@Repository
public class InsuranceRepositoryImpl implements InsuranceRepository {
	@PersistenceContext
	EntityManager em;

	@Override
	@Transactional
	public Insurance addOrUpdateInsurance(Insurance insurance) {
		try {
			Insurance newInsur=em.merge(insurance);
			return newInsur;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Insurance findInsuranceByCrop(Crop crop) {
		try {
			Insurance insurance=crop.getInsurance();
			return insurance;
		} catch (Exception e) {
			return null;
		}
	}

}
