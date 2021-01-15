package com.lti.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lti.entity.Crop;
import com.lti.entity.Insurance;
@Repository
public class InsuranceRepositoryImpl implements InsuranceRepository {
	@PersistenceContext
	EntityManager em;
	
	
	@Transactional
	public Insurance findInsuranceByInsuranceId(long insuranceId) {
		try {
			String jpql = "select i from Insurance i where i.insuranceId=:iId";
			Query query = em.createQuery(jpql);
			query.setParameter("iId",insuranceId);
			Insurance newInsurance = (Insurance) query.getSingleResult();
			return newInsurance;
		} catch (Exception e) {
			return null;
		}
	}
	
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

	@Override
	@Transactional
	public List<Insurance> viewAllInsurance() {
		try {
			String jpql = "select i from Insurance i";
			Query query = em.createQuery(jpql);
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional
	public long deleteInsurance(long insuranceId) {
		try {
			Insurance insurance = findInsuranceByInsuranceId(insuranceId);
			long id = insurance.getInsuranceId();
			em.remove(insurance);
			Insurance afterInsurance = findInsuranceByInsuranceId(insuranceId);
			if(afterInsurance==null)
				return id;  //deleted
			else
				return 0;  //not deleted
		} catch (Exception e) {
			return 0;
		}
	}
}
