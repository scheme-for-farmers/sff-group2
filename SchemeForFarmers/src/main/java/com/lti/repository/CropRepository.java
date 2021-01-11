package com.lti.repository;

import com.lti.entity.Crop;

public interface CropRepository {
	public long addOrUpdateCrop(Crop crop);
	public Crop findCropByCropNameAndCropType(String cropName,String cropType);

}
