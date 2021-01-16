package com.lti.repository;

import java.util.List;

import com.lti.entity.Crop;

public interface CropRepository {
	public long addOrUpdateCrop(Crop crop);
	public Crop findCropByCropNameAndCropType(String cropName,String cropType);
	public Crop findCropById(long cropId);
	public long deleteCrop(long cropId);
	public List<Crop> viewAllCrops();
	public List<String> viewAllCropTypes();
	public List<String> findAllCropNamesByCropType(String cropType);

}
