package com.lti.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.boot.model.source.spi.EmbeddableMapping;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lti.entity.Admin;
import com.lti.entity.ContactUs;

@Repository
public class AdminRepositoryImpl implements AdminRepository {
	@PersistenceContext
	EntityManager em;

	@Override
	@Transactional
	public long addOrUpdateAdmin(Admin admin) {
		try {
			Admin a = em.merge(admin);
			return a.getAdminId();
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	@Transactional
	public Admin fetchAdminByEmailAndPassword(String adminEmail, String adminPassword) {
		try {
			String jpql = "select a from Admin a where adminEmail=:aEmail and adminPassword=:aPassword";
			Query query = em.createQuery(jpql);
			query.setParameter("aEmail", adminEmail);
			query.setParameter("aPassword", adminPassword);
			Admin admin = (Admin) query.getSingleResult();
			return admin;
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	public Admin fetchAdminByEmail(String adminEmail) {
		try {
			String jpql = "select a from Admin a where adminEmail=:aEmail";
			Query query = em.createQuery(jpql);
			query.setParameter("aEmail", adminEmail);
			Admin admin = (Admin) query.getSingleResult();
			return admin;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Transactional
	public ContactUs addContactUs(ContactUs contactUs) {
		try {
			ContactUs contact = em.merge(contactUs);
			return contact;
		} catch (Exception e) {
			return null;
		}
	}
}
