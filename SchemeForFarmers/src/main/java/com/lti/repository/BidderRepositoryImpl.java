package com.lti.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}
