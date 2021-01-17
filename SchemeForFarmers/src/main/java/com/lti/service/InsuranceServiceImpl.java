package com.lti.service;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lti.dto.CalculateInsuranceDto;
import com.lti.dto.InsuranceInputDto;
import com.lti.entity.ApplyInsurance;
import com.lti.entity.Crop;
import com.lti.entity.Farmer;
import com.lti.entity.Insurance;
import com.lti.entity.SellRequest;
import com.lti.repository.ApplyInsuranceRepository;
import com.lti.repository.CropRepository;
import com.lti.repository.FarmerRepository;
import com.lti.repository.InsuranceRepository;
import com.lti.repository.SellRequestRepository;

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
	@Autowired
	SellRequestRepository sellRequestRepository;
	@Autowired
	EmailService emailService;
	
	@Override
	public CalculateInsuranceDto calculate(InsuranceInputDto inputDto) {
		try {
			double premiumAmount = 0;
			SellRequest sellRequest = sellRequestRepository.fetchSellRequestByRequestId(inputDto.getRequestId());
			Crop crop = sellRequest.getCrop();
			Farmer farmer = sellRequest.getFarmer();
			Insurance insurance = insuranceRepository.findInsuranceByCrop(crop);
			CalculateInsuranceDto calulateInsurance = new CalculateInsuranceDto();
			calulateInsurance.setArea(inputDto.getArea());
			calulateInsurance.setRequestId(inputDto.getRequestId());
			calulateInsurance.setCropName(crop.getCropName());
			calulateInsurance.setInsuranceCompanyName(insurance.getInsuranceCompanyName());
			calulateInsurance.setCropType(crop.getCropType());
			calulateInsurance.setSumInsured(insurance.getSumInsuredPerHectare());
			if (crop.getCropType().equalsIgnoreCase("kharif")) {
				premiumAmount = insurance.getSumInsuredPerHectare() * 0.02 * inputDto.getArea();
				calulateInsurance.setPremiumAmount(premiumAmount);
			} else if (crop.getCropType().equalsIgnoreCase("rabi")) {
				premiumAmount = insurance.getSumInsuredPerHectare() * 0.15 * inputDto.getArea();
				calulateInsurance.setPremiumAmount(premiumAmount);
			} else {
				premiumAmount = insurance.getSumInsuredPerHectare() * 0.05 * inputDto.getArea();
				calulateInsurance.setPremiumAmount(premiumAmount);
			}
			calulateInsurance.setTotalSumInsured(inputDto.getArea() * insurance.getSumInsuredPerHectare());
			return calulateInsurance;
		} catch (Exception e) {
			return null;
		}
	}

	public long applyInsurance(CalculateInsuranceDto calculateInsuranceDto) {
		try {
			ApplyInsurance applyInsurance = new ApplyInsurance();
			SellRequest sellRequest = sellRequestRepository.fetchSellRequestByRequestId(calculateInsuranceDto.getRequestId());
			applyInsurance.setSellRequest(sellRequest);
			applyInsurance.setApprove("no");
			applyInsurance.setInsuranceStatus("notClaimed");
			applyInsurance.setArea(calculateInsuranceDto.getArea());
			Crop crop = cropRepository.findCropByCropNameAndCropType(calculateInsuranceDto.getCropName(),
					calculateInsuranceDto.getCropType());
			Insurance insurance = insuranceRepository.findInsuranceByCrop(crop);
			applyInsurance.setInsurance(insurance);
			applyInsurance.setCropName(calculateInsuranceDto.getCropName());
			applyInsurance.setPremiumAmount(calculateInsuranceDto.getPremiumAmount());
			applyInsurance.setTotalsumInsured(calculateInsuranceDto.getTotalSumInsured());
			long policyNo = applyInsuranceRepository.addApplyInsurance(applyInsurance).getPolicyNo();
			if(policyNo>0) {
				String subject = "Insurance Applied Successfully!!";
				String email =applyInsurance.getSellRequest().getFarmer().getFarmerEmail();
				String text = "Hi " + applyInsurance.getSellRequest().getFarmer().getFarmerName()+ 
				"!! Your policyNo is " + policyNo+ "Premium Amount Rs "+ calculateInsuranceDto.getPremiumAmount()
				+ " is debited from your registered bank account and waiting for approval"
				+ "In case of any damage Rs" + calculateInsuranceDto.getTotalSumInsured() + "will be credited to your bank account!!";
				emailService.sendEmailForNewRegistration(email, text, subject);
				System.out.println("Email sent successfully");
			}
			return policyNo;
		} catch (Exception e) {
			return 0;
		}
	}
	public long claimInsurance(long policyNo,String causeOfClaim,LocalDate dateOfLoss) {
		ApplyInsurance applyInsurance = applyInsuranceRepository.fetchInsuranceByPolicyNo(policyNo);
		if(dateOfLoss.compareTo(LocalDate.now())>0)
			return 2;  //date invalid
		if(applyInsurance!=null  && applyInsurance.getApprove().equalsIgnoreCase("yes")) {
			String subject = "Insurance Claimed Successfully!!";
			String email =applyInsurance.getSellRequest().getFarmer().getFarmerEmail();
			String text = "Hi " + applyInsurance.getSellRequest().getFarmer().getFarmerName()+ 
					"!! Your policyNo is " + applyInsurance.getPolicyNo()+" and waiting for approval";
			emailService.sendEmailForNewRegistration(email, text, subject);
			System.out.println("Email sent successfully");
			return applyInsuranceRepository.claimInsurance(policyNo,causeOfClaim,dateOfLoss); //1 for valid
		}
		return 0; //policyNo invalid
	}
	public List<ApplyInsurance> fetchPendingClaimInsurance(){
		return applyInsuranceRepository.fetchPendingclaimInsurance();
	}

	@Override
	public List<Insurance> viewAllInsurance() {
		return insuranceRepository.viewAllInsurance();
	}

	@Override
	public long deleteInsurance(long insuranceId) {
		return insuranceRepository.deleteInsurance(insuranceId);
	}
	
	public long updateInsurance(Insurance insurance)
	{
		return insuranceRepository.addOrUpdateInsurance(insurance).getInsuranceId();
	}
}
