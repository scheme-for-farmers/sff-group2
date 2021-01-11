package com.lti.service;

import java.util.List;

import com.lti.entity.Crop;

public interface CropService {
	public long addOrUpdateCrop(Crop crop);
	public Crop findCropById(long cropId);
	public long deleteCrop(long cropId);
	public List<Crop> viewAllCrops();
}
