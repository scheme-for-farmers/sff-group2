package com.lti.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lti.entity.Bid;

@Repository
public class BidRepositoryImpl implements BidRepository {
	@PersistenceContext
	EntityManager em;

	@Transactional
	public Bid addOrUpdateBid(Bid bid) {
		//Bid b=em.merge(bid);
		return em.merge(bid); 
	}
	
	@Transactional
	public double findMaximumBidAmount(long cropId) {
		try {
			String jpql = "select max(b.bidAmount) from Bid b where b.crop.CropId=:cId and bidApprove='yes'";
			Query query = em.createQuery(jpql);
			query.setParameter("cId", cropId);
			double maxBidAmount = (Double) query.getSingleResult();
			return maxBidAmount;
		} catch (Exception e) {
			return 0;
		}
	}

	@Transactional
	public List<Double> previousBidsByCropId(long cropId) {
		String jpql = "select b.bidAmount from Bid b where b.crop.CropId=:cId";
		Query query = em.createQuery(jpql);
		query.setParameter("cId", cropId);
		return query.getResultList();
	}
	
	public List<Bid> fetchAllBidsByApproveYes() {
		try {
			System.out.println("Bid Repo");
			String jpql = "select b from Bid b join SellRequest s on s.status='unsold' where b.bidApprove='yes'";
			Query query = em.createQuery(jpql);
			List<Bid> bids = query.getResultList(); 
			for(Bid b : bids)
				System.out.println("bid: "+b.getBidId());
			return bids;
		} catch (Exception e) {
			return null;
		}
	}
	@Transactional
	public Bid fetchBidByBidId(long bidId) {
		String jpql = "select b from Bid b where b.bidId=:bId";
		Query query = em.createQuery(jpql);
		query.setParameter("bId", bidId);
		Bid bid=(Bid)query.getSingleResult();
		return bid;
	}
	@Transactional
	public Bid updateBidBybidId(long bidId) {
		String jpql = "update Bid b set b.bidApprove='yes' where b.bidId=:bId";
		Query query = em.createQuery(jpql);
		query.setParameter("bId", bidId);
		query.executeUpdate();
		Bid bid = fetchBidByBidId(bidId);
		return bid;
	}
	
	@Transactional
	public List<Bid> fetchBidsByBidApproveNo() {
		try {
			String jpql = "select b from Bid b where b.bidApprove='no'";
			Query query = em.createQuery(jpql);
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}
}
