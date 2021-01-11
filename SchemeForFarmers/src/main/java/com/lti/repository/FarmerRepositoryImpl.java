package com.lti.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.lti.entity.Farmer;

@Repository
public class FarmerRepositoryImpl implements FarmerRepository {
	@PersistenceContext
	EntityManager em;

	@Transactional
	public Farmer addOrUpdateFarmer(Farmer farmer) {
		try {
			Farmer f = em.merge(farmer);
			return f;
		} catch (Exception e) {
			return null;
		}
	}

}
