package com.lti.service;

import com.lti.entity.Crop;

public interface CropService {
	public long addOrUpdateCrop(Crop crop);
	public Crop findCropById(long cropId);

}
