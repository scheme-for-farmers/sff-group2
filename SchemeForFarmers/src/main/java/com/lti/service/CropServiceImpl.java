package com.lti.service;

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
}
