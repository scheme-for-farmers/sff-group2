package com.lti.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lti.entity.Crop;
import com.lti.entity.SellRequest;

@Repository

public class SellRequestRepositoryImpl implements SellRequestRepository {
	@PersistenceContext
	EntityManager em;

	@Transactional
	public List<SellRequest> soldDetails(String farmerEmail) {
		try {
			String jpql = "select s from SellRequest s where s.status='sold' and s.farmer.farmerEmail=:fEmail";
			Query query = em.createQuery(jpql);
			query.setParameter("fEmail", farmerEmail);
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}
	public List<SellRequest> fetchApprovalPendingSellRequest() {
		try {
			String jpql = "select s from SellRequest s where s.approve='no'";
			Query query = em.createQuery(jpql);
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}
	
	public SellRequest fetchSellRequestByRequestId(long requestId) {
		
		String jpql = "select s from SellRequest s where s.requestId=:rid";
		Query query = em.createQuery(jpql);
		query.setParameter("rid", requestId);
		SellRequest sellRequest=(SellRequest)query.getSingleResult();
		return sellRequest;
	}
	public SellRequest fetchSellRequestByRequestIdWithApproveYes(long requestId) {
		
		String jpql = "select s from SellRequest s where s.requestId=:rid and approve='yes'";
		Query query = em.createQuery(jpql);
		query.setParameter("rid", requestId);
		SellRequest sellRequest=(SellRequest)query.getSingleResult();
		return sellRequest;
	}

	@Transactional
	public SellRequest updateSellRequestByRequestId(long requestId) {
		try {
			String jpql = "update SellRequest s set s.approve='yes' where s.requestId=:rid";
			Query query = em.createQuery(jpql);
			query.setParameter("rid", requestId);
			query.executeUpdate();
			SellRequest sellRequest=fetchSellRequestByRequestId(requestId);
			return sellRequest;
		} catch (Exception e) {
			return null;
		}
	}
	@Transactional
	public SellRequest removeSellRequestByRequestId(long requestId) {
		try {
			SellRequest sellRequest=fetchSellRequestByRequestId(requestId);
			System.out.println("id: "+sellRequest.getRequestId());
			String jpql="delete from SellRequest s where s.requestId=:rid";
			Query query = em.createQuery(jpql);
			query.setParameter("rid", requestId);
			query.executeUpdate();
			return sellRequest;
		} catch (Exception e) {
			return null;
		}
	}
	@Transactional
	public List<SellRequest> fetchApprovedSellRequest() {
		try {
			String jpql = "select s from SellRequest s where s.approve='yes' and s.status='unsold'";
			Query query = em.createQuery(jpql);
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}
	@Transactional
	public SellRequest addOrUpdateSellRequest(SellRequest sellRequest) {
		try {
			SellRequest s=em.merge(sellRequest);
			return s;
		} catch (Exception e) {
			return null;
		}
	}
	@Transactional
	public List<Crop> viewUnsoldCrops(){
		try {
			String jpql = "select s.crop from SellRequest s where s.status='unsold' and s.approve='yes'";
			Query query = em.createQuery(jpql);
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}
}
