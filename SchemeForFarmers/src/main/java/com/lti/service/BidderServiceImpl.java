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
//	public long uploadDocument(DocumentDto documentDto) {
//		String bidderMail=documentDto.getMail();
//		Bidder bidder=bidderRepository.fetchBidderByEmail(bidderMail);
//		String imgUploadLocation = "e:/uploads/";
//		String uploadedFileName = documentDto.getPancard().getOriginalFilename();
//		String aadharFileName = documentDto.getAadharCard().getOriginalFilename();
//		String newaadharFileName = bidder.getBidderId()+"-"+aadharFileName;
//		String newFileName = bidder.getBidderId() + "-" + uploadedFileName;
//		String targetFileName = imgUploadLocation + newFileName;
//		String targetAadharFileName = imgUploadLocation+newaadharFileName;
//
//		try {
//			FileCopyUtils.copy(documentDto.getPancard().getInputStream(), new FileOutputStream(targetFileName));
//			FileCopyUtils.copy(documentDto.getPancard().getInputStream(), new FileOutputStream(targetAadharFileName));
//			bidder.setBidderAadhar(newaadharFileName);
//			bidder.setBidderPan(newFileName);
//			return bidderRepository.addOrUpdateBidder(bidder).getBidderId();
//		} catch(IOException e) {
//			return 0;
//		}
//	}	
}
