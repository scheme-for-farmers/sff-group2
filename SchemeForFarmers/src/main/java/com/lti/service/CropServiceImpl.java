package com.lti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.entity.Crop;
import com.lti.repository.CropRepository;

@Service
public class CropServiceImpl implements CropService {
	@Autowired
	CropRepository cropRepository;

	public long addOrUpdateCrop(Crop crop) {
		return cropRepository.addOrUpdateCrop(crop);
	}
	public Crop findCropById(long cropId) {
		return cropRepository.findCropById(cropId);
	}
	public long deleteCrop(long cropId) {
		return cropRepository.deleteCrop(cropId);
	}
	public List<Crop> viewAllCrops(){
		return cropRepository.viewAllCrops();
	}
}
