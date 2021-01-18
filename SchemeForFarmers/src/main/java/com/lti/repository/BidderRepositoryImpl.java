package com.lti.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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
			System.out.println("repo: "+bidders.getBidderEmail());
			return bidders;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Transactional
	public Bidder fetchBidderById(long id) {
		try {
			String jpql = "select b from Bidder b where b.bidderId=:bId";
			Query query = em.createQuery(jpql);
			query.setParameter("bId", id);
			Bidder bidders = (Bidder) query.getSingleResult();
			System.out.println("repo: "+bidders.getBidderEmail());
			return bidders;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	
	@Transactional
	public Bidder fetchBidderByEmailWithApproveYes(String bidderEmail) {
		try {
			String jpql = "select b from Bidder b where bidderEmail=:bEmail and bidderApprove='yes'";
			Query query = em.createQuery(jpql);
			query.setParameter("bEmail", bidderEmail);
			Bidder bidders = (Bidder) query.getSingleResult();
			System.out.print(bidders.getBidderEmail());
			return bidders;
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	public List<Bidder> fetchApprovalPendingBidders() { // to display farmers with approve="No"
		String jpql = "select b from Bidder b where b.bidderApprove='no'";
		TypedQuery<Bidder> query = em.createQuery(jpql, Bidder.class);
		return query.getResultList();
	}

	@Transactional
	public Bidder updateBidderByEmail(String bidderEmail) {
		try {
			String jpql = "update Bidder b set b.bidderApprove='yes' where b.bidderEmail=:bEmail";
			Query query = em.createQuery(jpql);
			query.setParameter("bEmail", bidderEmail);
			query.executeUpdate();
			Bidder bidder = fetchBidderByEmail(bidderEmail);
			System.out.println("repo: "+bidder.getBidderEmail());
			return bidder;
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	public long rejectBidder(String bidderEmail) {
		String jpql1 = "select b from Bidder b where b.bidderEmail=:bEmail";
		TypedQuery<Bidder> query1 = em.createQuery(jpql1, Bidder.class);
		query1.setParameter("bEmail", bidderEmail);
		Bidder b = (Bidder) query1.getSingleResult();
		long bidderId = b.getBidderId();
		em.remove(b);
		return bidderId;
	}

}
