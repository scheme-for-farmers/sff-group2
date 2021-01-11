package com.lti.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lti.entity.Bidder;

@Repository
public class BidderRepositoryImpl implements BidderRepository {
	@PersistenceContext
	EntityManager em;

	@Transactional
	public Bidder addOrUpdateBidder(Bidder bidder) {
		Bidder b = em.merge(bidder);
		return b;
	}
	@Transactional
	public Bidder fetchBidderByEmailAndPassword(String bidderEmail, String bidderPassword) {
		try {
			String jpql = "select b from Bidder b where bidderEmail=:bEmail and bidderPassword=:bPassword";
			Query query = em.createQuery(jpql);
			query.setParameter("bEmail", bidderEmail);
			query.setParameter("bPassword", bidderPassword);
			Bidder bidders = (Bidder) query.getSingleResult();
			return bidders;
		} catch (Exception e) {
			return null;
		}
	}
	@Transactional
	public Bidder fetchBidderByEmail(String bidderEmail) {
		try {
			String jpql = "select b from Bidder b where bidderEmail=:bEmail";
			Query query = em.createQuery(jpql);
			query.setParameter("bEmail", bidderEmail);
			Bidder bidders = (Bidder) query.getSingleResult();
			return bidders;
		} catch (Exception e) {
			return null;
		}
	}
}
