package com.lti.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.lti.entity.Crop;
import com.lti.entity.Farmer;

@Repository
public class CropRepositoryImpl implements CropRepository {
	@PersistenceContext
	EntityManager em;

	@Override
	@Transactional
	public long addOrUpdateCrop(Crop crop) {
		try {
			Crop c = em.merge(crop);
			return c.getCropId();
		} catch (Exception e) {
			return 0;
		}
	}
	@Transactional
	public Crop findCropByCropNameAndCropType(String cropName, String cropType) {
			try {
				String jpql = "select c from Crop c where c.CropName=:cName and c.CropType=:cType";
				Query query = em.createQuery(jpql);
				query.setParameter("cName", cropName);
				query.setParameter("cType", cropType);
				Crop c = (Crop) query.getSingleResult();
				return c;
			} catch (Exception e) {
				return null;
			}
	}
	@Transactional
	public Crop findCropById(long cropId) {
		try {
			return em.find(Crop.class,cropId);
		} catch (Exception e) {
			return null;
		}
	}
	
	@Transactional
	public long deleteCrop(long cropId) {
		try {
			long id=0;
			Crop newcrop = findCropById(cropId);
			if(newcrop!=null)
				id = newcrop.getCropId();
			em.remove(newcrop);
			return id;
		} catch (Exception e) {
			return 0;
		}
	}
	@Transactional
	public List<Crop> viewAllCrops(){
		try {
			String jpql = "select c from Crop c";
			Query query = em.createQuery(jpql);
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}
}
