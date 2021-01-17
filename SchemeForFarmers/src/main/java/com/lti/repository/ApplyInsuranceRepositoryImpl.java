package com.lti.repository;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lti.entity.ApplyInsurance;
@Repository


public class ApplyInsuranceRepositoryImpl implements ApplyInsuranceRepository {
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
	public long updateInsuranceApproval(long policyNo,long requestId) {
			try {
				String jpql = "update ApplyInsurance a set a.approve='yes' where a.policyNo=:pNo "
						+ "and a.sellRequest.requestId in (select s.requestId from SellRequest s where s.requestId=:rId and s.approve='yes')";
				Query query = em.createQuery(jpql);
				query.setParameter("pNo",policyNo);
				query.setParameter("rId",requestId);
				int rows = query.executeUpdate();
				return rows;
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
			int rows = query.executeUpdate();
			return rows;
		} catch (Exception e) {
			return 0;
		}
	}
	
	@Transactional
	public List<ApplyInsurance> pendingApprovalInsurance(){
		try {
			System.out.println("repo");
			String jpql = "select a from ApplyInsurance a where a.approve='no'";
			Query query = em.createQuery(jpql);
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}
	@Transactional
	public long claimInsurance(long policyNo,String causeOfClaim,LocalDate dateOfLoss) {
		try {
			String jpql = "update ApplyInsurance a set a.causeOfClaim=:claim,a.dateOfLoss=:date where a.policyNo=:pNo";
			Query query = em.createQuery(jpql);
			query.setParameter("pNo",policyNo);
			query.setParameter("claim",causeOfClaim);
			query.setParameter("date",dateOfLoss);
			int rows = query.executeUpdate();
			return rows;
		} catch (Exception e) {
			return 0;
		}
	}
	@Transactional
	public ApplyInsurance fetchInsuranceByPolicyNo(long policyNo) {
		try {
			String jpql = "select a from ApplyInsurance a where a.policyNo=:pNo";
			Query query = em.createQuery(jpql);
			query.setParameter("pNo",policyNo);
			ApplyInsurance newInsurance = (ApplyInsurance) query.getSingleResult();
			return newInsurance;
		} catch (Exception e) {
			return null;
		}
	}
	@Transactional
	public List<ApplyInsurance> fetchPendingclaimInsurance(){
		try {
			String jpql = "select a from ApplyInsurance a where a.insuranceStatus='notClaimed' and a.causeOfClaim!='null'";
			Query query = em.createQuery(jpql);
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Transactional
	public long approveclaimInsurance(long policyNo) {
		try {
			String jpql = "update ApplyInsurance a set a.insuranceStatus='claimed' where a.policyNo=:pNo";
			Query query = em.createQuery(jpql);
			query.setParameter("pNo",policyNo);
			int rows = query.executeUpdate();
			return rows;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	@Transactional
	public long rejectclaimInsurance(long policyNo) {
		try {
			String jpql = "delete from ApplyInsurance a where a.policyNo=:pNo";
			Query query = em.createQuery(jpql);
			query.setParameter("pNo",policyNo);
			int rows = query.executeUpdate();
			return rows;
		} catch (Exception e) {
			return 0;
		}
	}
}
