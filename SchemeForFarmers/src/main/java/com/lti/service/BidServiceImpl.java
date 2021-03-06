package com.lti.service;

 

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 

import com.lti.dto.DisplayRequestDto;
import com.lti.entity.Bid;
import com.lti.entity.Bidder;
import com.lti.entity.Crop;
import com.lti.entity.SellRequest;
import com.lti.repository.BidRepository;
import com.lti.repository.BidderRepository;
import com.lti.repository.CropRepository;
import com.lti.repository.SellRequestRepository;

 

@Service
public class BidServiceImpl implements BidService {

 

    @Autowired
    BidRepository bidRepository;
    @Autowired
    CropRepository cropRepository;
    @Autowired
    BidderRepository bidderRepository;
    @Autowired
    SellRequestRepository sellRequestRepository;
    @Autowired
    EmailService emailService;

 

    public List<DisplayRequestDto> fetchApprovedSellRequest() {
        List<SellRequest> sellRequests = sellRequestRepository.fetchApprovedSellRequest();
        List<DisplayRequestDto> appSellReqDto = new ArrayList<DisplayRequestDto>();
        for (SellRequest s : sellRequests) {
        	if(s.getApplyInsurance()!=null)
        	{
        		if(s.getApplyInsurance().getCauseOfClaim()==null && s.getApplyInsurance().getDateOfLoss()==null) {
                    DisplayRequestDto approval = new DisplayRequestDto();
                    double basePrice = s.getCrop().getBasePrice();
                    double currentBidAmount = bidRepository.findMaximumBidAmountByRequestId(s.getCrop().getCropId(),s.getRequestId());
                    if (currentBidAmount > basePrice) {
                        approval.setCurrentBidAmount(currentBidAmount);
                    } else
                        approval.setCurrentBidAmount(basePrice);
                    approval.setCropName(s.getCrop().getCropName());
                    approval.setCropType(s.getCrop().getCropType());
                    approval.setFarmerEmail(s.getFarmer().getFarmerEmail());
                    approval.setQuantity(s.getQuantity());
                    approval.setRequestId(s.getRequestId());
                    appSellReqDto.add(approval);
                }
        	}
        	else {
            DisplayRequestDto approval = new DisplayRequestDto();
            double basePrice = s.getCrop().getBasePrice();
            double currentBidAmount = bidRepository.findMaximumBidAmountByRequestId(s.getCrop().getCropId(),s.getRequestId());
            if (currentBidAmount > basePrice) {
                approval.setCurrentBidAmount(currentBidAmount);
            } else
                approval.setCurrentBidAmount(basePrice);
            approval.setCropName(s.getCrop().getCropName());
            approval.setCropType(s.getCrop().getCropType());
            approval.setFarmerEmail(s.getFarmer().getFarmerEmail());
            approval.setQuantity(s.getQuantity());
            approval.setRequestId(s.getRequestId());
            appSellReqDto.add(approval);
        }
        }
        return appSellReqDto;
    }

 

    public long placeBid(DisplayRequestDto displayRequestDto) {
        try {
            Bidder bidder = bidderRepository.fetchBidderByEmailWithApproveYes(displayRequestDto.getBidderEmail());
            Crop crop = cropRepository.findCropByCropNameAndCropType(displayRequestDto.getCropName(),
                    displayRequestDto.getCropType());
            double maxBidAmount = bidRepository.findMaximumBidAmountByRequestId(crop.getCropId(),displayRequestDto.getRequestId());
            System.out.println("max: "+maxBidAmount);
            SellRequest sellRequest = sellRequestRepository
                    .fetchSellRequestByRequestIdWithApproveYes(displayRequestDto.getRequestId());
            if(displayRequestDto.getCurrentBidAmount()>crop.getBasePrice())
            {
            	if (displayRequestDto.getCurrentBidAmount() > maxBidAmount)
            	{
            		Bid bid = new Bid();
                    bid.setBidAmount(displayRequestDto.getCurrentBidAmount());
                    bid.setBidDate(LocalDate.now());
                    //bid.setBidApprove("no");
                    bid.setRequestId(displayRequestDto.getRequestId());
                    bid.setBidder(bidder);
                    bid.setCrop(crop);
                    Bid newbid = bidRepository.addOrUpdateBid(bid);
                    String subject = "Registeration successful";
                    String email = newbid.getBidder().getBidderEmail();
                    String text = "Hi " + newbid.getBidder().getBidderName() + "!! Your bidRequest is placed for "
                            + newbid.getCrop().getCropName() + "!!. And your bid id is " + newbid.getBidId()
                            + " waiting for approval";
                    emailService.sendEmailForNewRegistration(email, text, subject);
                    System.out.println("Email sent successfully");
                    return newbid.getBidId();
            	}
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }
}