package com.lti.service;

import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.lti.dto.DocumentDto;
import com.lti.entity.Bidder;
import com.lti.entity.Farmer;
import com.lti.repository.BidderRepository;

@Service
public class BidderServiceImpl implements BidderService {
	@Autowired
	BidderRepository bidderRepository;
	@Autowired
	EmailService emailService;
	
	public boolean checkDuplicate(String bidderEmail) {
		Bidder bidder = bidderRepository.fetchBidderByEmail(bidderEmail);
		if (bidder == null)
			return true;
		return false;
	}
	
	public long registerBidder(Bidder bidder) {
		boolean output = checkDuplicate(bidder.getBidderEmail());
		if(output) {
			bidder.setBidderApprove("no".toLowerCase());
			bidder.getBidderAddress().setBidder(bidder);
			bidder.getBidderBank().setBidder(bidder);
			Bidder newBidder = bidderRepository.addOrUpdateBidder(bidder);
			if (newBidder != null) {
				if(newBidder.getBidderId()>0)
				{
					String subject = "Registeration successful";
					String email =newBidder.getBidderEmail();
					String text = "Hi " + newBidder.getBidderName()+
					"!! Your registeration Id is " + newBidder.getBidderId()+" waiting for approval";
					emailService.sendEmailForNewRegistration(email, text, subject);
					System.out.println("Email sent successfully");
					return newBidder.getBidderId();
				}
			}
		}
		return 0;
	}

	public String isValidUser(String bidderEmail, String bidderPassword) {
		// TODO Auto-generated method stub
		Bidder bidder = bidderRepository.fetchBidderByEmailAndPassword(bidderEmail, bidderPassword);
		if (bidder != null) {
			if (bidder.getBidderApprove().equals("yes"))
				return "valid";
			else
				return "pending";
		} else
			return "invalid";
	}

	public String forgotPassword(String bidderEmail) {
		Bidder bidder = bidderRepository.fetchBidderByEmail(bidderEmail);
		if (bidder != null)
		{
			String subject = "Password Recovery";
			String email =bidder.getBidderEmail();
			String text = "Hi " + bidder.getBidderName()+
			"!! Your password is " + bidder.getBidderPassword();
			emailService.sendEmailForNewRegistration(email, text, subject);
			System.out.println("Email sent successfully");
			return bidder.getBidderPassword();
		}
		else
			return null;
	}
	
	public long uploadDocument(DocumentDto documentDto) {
		long id=documentDto.getId();
		String aadharCardUploadLocation = "d:/bidderAadhar/";
		String panCardUploadLocation = "d:/bidderPan/";
		String uploadedPanFileName= documentDto.getPancard().getOriginalFilename();
		String uploadedAadharFileName = documentDto.getAadharCard().getOriginalFilename();
		String newAadharFileName = id + "-" + uploadedAadharFileName;
		String newPanFileName = id + "-" + uploadedPanFileName;
		String targetAadharFileName = aadharCardUploadLocation + newAadharFileName;
		String targetPanFileName = panCardUploadLocation + newPanFileName;
		try {
		FileCopyUtils.copy(documentDto.getAadharCard().getInputStream(), new FileOutputStream(targetAadharFileName));
		FileCopyUtils.copy(documentDto.getPancard().getInputStream(), new FileOutputStream(targetPanFileName));
		} catch(IOException e) {
		e.printStackTrace(); //hoping no error would occur
		return 404;
		}
		Bidder bidder=bidderRepository.fetchBidderById(id);
		bidder.setBidderAadhar(newAadharFileName);
		bidder.setBidderPan(newPanFileName);
		return bidderRepository.addOrUpdateBidder(bidder).getBidderId();
}
	
}

