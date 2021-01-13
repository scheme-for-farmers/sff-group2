package com.lti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.dto.CalculateInsuranceDto;
import com.lti.entity.ApplyInsurance;
import com.lti.entity.Crop;
import com.lti.entity.Farmer;
import com.lti.entity.Insurance;
import com.lti.repository.ApplyInsuranceRepository;
import com.lti.repository.CropRepository;
import com.lti.repository.FarmerRepository;
import com.lti.repository.InsuranceRepository;
@Service
public class InsuranceServiceImpl implements InsuranceService {
	@Autowired
	CropRepository cropRepository;
	@Autowired
	InsuranceRepository insuranceRepository;
	@Autowired
	ApplyInsuranceRepository applyInsuranceRepository;
	@Autowired
	FarmerRepository farmerRepository;
	
	@Override
	public CalculateInsuranceDto calculate(String cropName, String cropType, double area) {
		double premiumAmount=0;
		try {
			Crop crop=cropRepository.findCropByCropNameAndCropType(cropName, cropType);
			Insurance insurance = insuranceRepository.findInsuranceByCrop(crop);
			CalculateInsuranceDto calulateInsurance = new CalculateInsuranceDto();
			calulateInsurance.setArea(area);
			calulateInsurance.setCropName(crop.getCropName());
			calulateInsurance.setInsuranceCompanyName(insurance.getInsuranceCompanyName());
			calulateInsurance.setCropType(cropType);
			calulateInsurance.setSumInsured(insurance.getSumInsuredPerHectare());
			if(crop.getCropType().equalsIgnoreCase("kharif")) {
				premiumAmount = insurance.getSumInsuredPerHectare()*0.02*area;
				calulateInsurance.setPremiumAmount(premiumAmount);
			}
			else if(crop.getCropType().equalsIgnoreCase("rabi")) {
				premiumAmount = insurance.getSumInsuredPerHectare()*0.15*area;
				calulateInsurance.setPremiumAmount(premiumAmount);
			}
			else {
				premiumAmount = insurance.getSumInsuredPerHectare()*0.05*area;
				calulateInsurance.setPremiumAmount(premiumAmount);
			}
			calulateInsurance.setTotalSumInsured(area*insurance.getSumInsuredPerHectare());
			return calulateInsurance;
		} catch (Exception e) {
			return null;
		}
	}
	
	public long applyInsurance(CalculateInsuranceDto calculateInsuranceDto) {
		try {
			ApplyInsurance applyInsurance = new ApplyInsurance();
			Farmer farmer = farmerRepository.fetchFarmerByEmail(calculateInsuranceDto.getFarmerEmail());
			applyInsurance.setFarmer(farmer);
			applyInsurance.setApprove("no");
			applyInsurance.setArea(calculateInsuranceDto.getArea());
			Crop crop = cropRepository.findCropByCropNameAndCropType(calculateInsuranceDto.getCropName(), calculateInsuranceDto.getCropType());
			Insurance insurance = insuranceRepository.findInsuranceByCrop(crop);
			applyInsurance.setInsurance(insurance);
			applyInsurance.setCropName(calculateInsuranceDto.getCropName());
			applyInsurance.setPremiumAmount(calculateInsuranceDto.getPremiumAmount());
			applyInsurance.setSumInsured(calculateInsuranceDto.getSumInsured());
			return applyInsuranceRepository.addApplyInsurance(applyInsurance).getPolicyNo();
		} catch (Exception e) {
			return 0;
		}
	}
}
