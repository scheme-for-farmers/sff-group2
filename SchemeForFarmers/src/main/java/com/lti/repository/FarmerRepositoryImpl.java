package com.lti.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
	@Transactional
	public Farmer isValidUser(String farmerEmail,String farmerPassword) {
		try {
			String jpql = "select f from Farmer f where farmerEmail=:fEmail and farmerPassword=:fPassword";
			Query query = em.createQuery(jpql);
			query.setParameter("fEmail", farmerEmail);
			query.setParameter("fPassword", farmerPassword);
			Farmer farmers = (Farmer) query.getSingleResult();
			return farmers;
		} catch (Exception e) {
			return null;
		}
	}
}
