package com.lti.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lti.entity.ApplyInsurance;
@Repository


public class ApplyInsuranceImpl implements ApplyInsuranceRepository {
	@PersistenceContext
	EntityManager em;
	
	@Transactional
	public ApplyInsurance addApplyInsurance(ApplyInsurance applyInsurance) {
		try {
			ApplyInsurance newInsurance = em.merge(applyInsurance);
			return newInsurance;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional
	public long updateInsuranceApproval(long policyNo) {
		try {
			String jpql = "update ApplyInsurance a set approve='yes' where a.policyNo=:pNo";
			Query query = em.createQuery(jpql);
			query.setParameter("pNo",policyNo);
			query.executeUpdate();
			return policyNo;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	@Transactional
	public long rejectInsuranceApproval(long policyNo) {
		try {
			String jpql = "delete from ApplyInsurance a where a.policyNo=:pNo";
			Query query = em.createQuery(jpql);
			query.setParameter("pNo",policyNo);
			query.executeUpdate();
			return policyNo;
		} catch (Exception e) {
			return 0;
		}
	}
}
