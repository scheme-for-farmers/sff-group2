package com.lti.resource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.lti.dto.ApprovalBidDto;
import com.lti.dto.ApprovalSellRequestDto;
import com.lti.dto.CalculateInsuranceDto;
import com.lti.dto.DisplayBidDto;
import com.lti.dto.DisplayRequestDto;
import com.lti.dto.DocumentDto;
import com.lti.dto.InsuranceApproval;
import com.lti.dto.InsuranceDto;
import com.lti.dto.InsuranceInputDto;
import com.lti.dto.MarketPlaceDto;
import com.lti.dto.SellRequestDto;
import com.lti.dto.SoldHistoryDto;
import com.lti.dto.ViewUnsoldCrops;
import com.lti.entity.Admin;
import com.lti.entity.ApplyInsurance;
import com.lti.entity.Bidder;
import com.lti.entity.ContactUs;
import com.lti.entity.Crop;
import com.lti.entity.Farmer;
import com.lti.entity.Insurance;
import com.lti.entity.SellRequest;
import com.lti.service.AdminService;
import com.lti.service.BidService;
import com.lti.service.BidderService;
import com.lti.service.CropService;
import com.lti.service.FarmerService;
import com.lti.service.InsuranceService;
import com.lti.service.SellRequestService;

@RestController
@CrossOrigin
public class SchemeController {

	@Autowired
	FarmerService farmerService;
	@Autowired
	BidderService bidderService;
	@Autowired
	AdminService adminService;
	@Autowired
	CropService cropService;
	@Autowired
	BidService bidService;
	@Autowired
	SellRequestService sellRequestService;
	@Autowired
	InsuranceService insuranceService;

	@RequestMapping(value = "/placeSellRequest", method = RequestMethod.POST)
	public long placeSellRequest(@RequestBody SellRequestDto sellRequestDto) {
		System.out.print(sellRequestDto.getCropName() + " " + sellRequestDto.getCropType() + " "
				+ sellRequestDto.getFarmerEmail() + " " + sellRequestDto.getQuantity());
		return sellRequestService.placeSellRequest(sellRequestDto);
	}

	@RequestMapping(value = "/registerUser", method = RequestMethod.POST) // http://localhost:8080/sff/registerUser
	public long registerFarmer(@RequestBody Farmer farmer) {
		System.out.println("hello" + farmer.getFarmerBank().getIfscCode() + " ");
		return farmerService.registerFarmer(farmer);
	}

	@RequestMapping(value = "/registerBidder", method = RequestMethod.POST)
	public long registerBidder(@RequestBody Bidder bidder) {
		return bidderService.registerBidder(bidder);
	}

//	http://localhost:8080/isVaild/svraj24@gmail.com/selvaraj!23

	@RequestMapping(value = "/isVaild/{email}/{password}", method = RequestMethod.GET)
	public int isValidUser(@PathVariable("email") String Email, @PathVariable("password") String Password) {
		System.out.println("hello");
		String farmer = farmerService.isValidUser(Email, Password);
		if (farmer.equals("valid")) {
			return 1;
		} else if (farmer.equals("pending")) {
			return 12;
		} else {
			String bidder = bidderService.isValidUser(Email, Password);
			if (bidder.equals("valid")) {
				return 2;
			} else if (bidder.equals("pending")) {
				return 22;
			} else {
				boolean admin = adminService.isValidUser(Email, Password);
				if (admin) {
					System.out.println("Admin is present");
					return 3;
				} else {
					return 4;
				}
			}
		}

	}

	@RequestMapping(value = "/addCrop", method = RequestMethod.POST)
	public long addOrUpdateCrop(@RequestBody Crop crop) {
//		{
//		    "basePrice": 500.0,
//		    "cropName": "potato",
//		    "cropType": "vegetable"
//		}
		System.out.println(crop.getCropName());
		return cropService.addOrUpdateCrop(crop);
	}

	@RequestMapping(value = "/viewAllCrops", method = RequestMethod.GET)
	public List<Crop> viewAllCrops() {
		return cropService.viewAllCrops();
	}

	@RequestMapping(value = "/deleteCrop/{cId}", method = RequestMethod.GET)
	public long deleteCrop(@PathVariable("cId") long cropId) {
		return cropService.deleteCrop(cropId);
	}

	@RequestMapping(value = "/viewPendingApprovalFarmers", method = RequestMethod.GET)
	public List<Farmer> pendingApprovalFarmers() { // http://localhost:8080/sff/viewPendingApprovalFarmers
		return adminService.fetchApprovalPendingFarmers();
	}

	@RequestMapping(value = "/approveFarmer/{email}", method = RequestMethod.GET)
	public long approveFarmer(@PathVariable("email") String farmerEmail) {
		return adminService.approveFarmer(farmerEmail);
	}

	@RequestMapping(value = "/rejectFarmer/{email}", method = RequestMethod.GET)
	public long rejectFarmer(@PathVariable("email") String farmerEmail) {
		return adminService.rejectFarmer(farmerEmail);
	}

	@RequestMapping(value = "/viewPendingApprovalBidders", method = RequestMethod.GET)
	public List<Bidder> pendingApprovalBidders() {
		return adminService.fetchApprovalPendingBidders();
	}

	@RequestMapping(value = "/approveBidder/{email}", method = RequestMethod.GET)
	public long approveBidder(@PathVariable("email") String bidderEmail) {
		return adminService.approveBidder(bidderEmail);
	}

	@RequestMapping(value = "/rejectBidder/{email}", method = RequestMethod.GET)
	public long rejectBidder(@PathVariable("email") String bidderEmail) {
		return adminService.rejectBidder(bidderEmail);
	}

	@RequestMapping(value = "/viewMarketPlace/{cName}/{cType}", method = RequestMethod.GET)
	public MarketPlaceDto viewMarketPlace(@PathVariable("cName") String cropName,
			@PathVariable("cType") String cropType) {
		return farmerService.viewMarketPlace(cropName, cropType);
	}

	@RequestMapping(value = "/viewSoldHistory/{email}", method = RequestMethod.GET)
	public List<SoldHistoryDto> viewSoldHistory(@PathVariable("email") String farmerEmail) {
		return farmerService.viewSoldHistory(farmerEmail);
	}

	@RequestMapping(value = "/fetchApprovalPendingSellRequest", method = RequestMethod.GET)
	public List<ApprovalSellRequestDto> fetchApprovalPendingSellRequest() {
		return adminService.fetchApprovalPendingSellRequest();
	}

	@RequestMapping(value = "/approveSellRequest/{rId}", method = RequestMethod.GET)
	public long approveSellRequest(@PathVariable("rId") long requestId) {
		return adminService.approveSellRequest(requestId);
	}

	@RequestMapping(value = "/rejectSellRequest/{rId}", method = RequestMethod.GET)
	public long rejectSellRequestApproval(@PathVariable("rId") long requestId) {
		return adminService.rejectSellRequestApproval(requestId);
	}

	@RequestMapping(value = "/displaySellRequest", method = RequestMethod.GET)
	public List<DisplayRequestDto> displaySellRequest() {
		return bidService.fetchApprovedSellRequest();
	}

	@RequestMapping(value = "/placeBid", method = RequestMethod.POST)
	public long PlaceBidRequest(@RequestBody DisplayRequestDto displayRequestDto) {

		return bidService.placeBid(displayRequestDto);
	}

	@RequestMapping(value = "/displayBids", method = RequestMethod.GET)
	public List<DisplayBidDto> viewAllBids() {
		return adminService.viewBid();
	}

	@RequestMapping(value = "/sellCrop/{bId}", method = RequestMethod.GET)
	public int sellCropToBidder(@PathVariable("bId") long bidId) {
		return adminService.sellCropToBidder(bidId);
	}

	@RequestMapping(value = "/findCropById/{cId}", method = RequestMethod.GET)
	public Crop findCropById(@PathVariable("cId") long cropId) {
		return cropService.findCropById(cropId);
	}

	@GetMapping(value = "/forgotPassword/{email}")
	public int forgotPassword(@PathVariable("email") String Email) {
		String s = farmerService.forgotPassword(Email);
		if (s != null)
			return 1;
		else {
			String s1 = bidderService.forgotPassword(Email);
			if (s1 != null)
				return 2;
			else {
				String s2 = adminService.forgotPassword(Email);
				if (s2 != null)
					return 3;
				else
					return 0;
			}
		}
	}

	@RequestMapping(value = "/uploadFarmerDoc", method = RequestMethod.POST, consumes = { "multipart/form-data" })
//	consumes = MediaType.MULTIPART_FORM_DATA_VALUE
	public long uploadFarmerDocument(@ModelAttribute DocumentDto documentDto) {
		System.out.println(documentDto.getMail());
		return farmerService.uploadDocument(documentDto);
	}

	@RequestMapping(value = "/uploadBidderDoc", method = RequestMethod.POST, consumes = { "multipart/form-data" })
//	consumes = MediaType.MULTIPART_FORM_DATA_VALUE
	public long uploadBidderDocument(@ModelAttribute DocumentDto documentDto) {
		return bidderService.uploadDocument(documentDto);
	}

	@RequestMapping(value = "/addAdmin", method = RequestMethod.POST) // http://localhost:8080/sff/registerUser
	public long addAdmin(@RequestBody Admin admin) {
//		System.out.println("hello" + farmer.getFarmerBank().getIFSC_code());
		return adminService.addOrUpdateAdmin(admin);
	}

	// --------------------------------------------insurance

	@RequestMapping(value = "/addInsurance", method = RequestMethod.POST)
	public long addOrUpdateInsurance(@RequestBody InsuranceDto insuranceDto) {

		return adminService.addOrUpdateInsurance(insuranceDto);
	}

	@RequestMapping(value = "/calculateInsurance", method = RequestMethod.POST)
	public CalculateInsuranceDto calculate(@RequestBody InsuranceInputDto inputDto) {
		return insuranceService.calculate(inputDto);
	}

	@RequestMapping(value = "/applyInsurance", method = RequestMethod.POST)
	public long applyInsurance(@RequestBody CalculateInsuranceDto calculateInsuranceDto) {
		return insuranceService.applyInsurance(calculateInsuranceDto);
	}

	@RequestMapping(value = "/fetchApprovalPendingInsurance", method = RequestMethod.GET)
	public List<InsuranceApproval> fetchApprovalPendingInsurance() {
		return adminService.fetchApprovalPendingInsurance();
	}

	@RequestMapping(value = "/approveInsurance/{iId}/{rId}", method = RequestMethod.GET)
	public long approveInsurance(@PathVariable("iId") long policyNo, @PathVariable("rId") long requestId) {
		return adminService.approveInsurance(policyNo, requestId);
	}

	@RequestMapping(value = "/rejectInsurance/{rId}", method = RequestMethod.GET)
	public long rejectInsurance(@PathVariable("rId") long policyNo) {
		return adminService.rejectInsuranceApproval(policyNo);
	}

	@RequestMapping(value = "/viewUnsoldCrops", method = RequestMethod.GET)
	public List<Crop> viewUnSoldCrops() {
		return sellRequestService.viewUnsoldCrops();
	}

	@RequestMapping(value = "/claimInsurance/{pNo}/{cause}/{date}", method = RequestMethod.GET)
	public long claimInsurance(@PathVariable("pNo") long policyNo, @PathVariable("cause") String causeOfClaim,
			@PathVariable("date") String dateOfLoss) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate fDate = LocalDate.parse(dateOfLoss, formatter);
		return insuranceService.claimInsurance(policyNo, causeOfClaim, fDate);
	}

	@RequestMapping(value = "/fetchApprovalPendingClaimInsurance", method = RequestMethod.GET)
	public List<ApplyInsurance> fetchPendingClaimInsurance() {
		return insuranceService.fetchPendingClaimInsurance();
	}
	
	@RequestMapping(value = "/approveClaimedInsurance/{pNo}", method = RequestMethod.GET)
	public long approveClaimInsurance(@PathVariable("pNo") long policyNo) {
		return adminService.approveClaimInsurance(policyNo);
	}
	@RequestMapping(value = "/rejectClaimedInsurance/{pNo}", method = RequestMethod.GET)
	public long rejectClaimInsurance(@PathVariable("pNo") long policyNo) {
		return adminService.rejectClaimInsurance(policyNo);
	}
	
	@RequestMapping(value = "/viewAllinsurance", method = RequestMethod.GET)
	public List<Insurance> viewAllInsurance() {
		return insuranceService.viewAllInsurance();
	}
	
	@RequestMapping(value = "/deleteInsurance/{iId}", method = RequestMethod.GET)
	public long deleteInsurance(@PathVariable("iId") long insuranceId) {
		return insuranceService.deleteInsurance(insuranceId);
	}
	
	@RequestMapping(value = "/contactUs", method = RequestMethod.POST)
	public long addContactUs(@RequestBody ContactUs contact) {
		return adminService.addContactUs(contact);
	}
	@RequestMapping(value="/viewUnsoldCropsByFarmer/{femail}", method=RequestMethod.GET)
	public List<SellRequest> viewUnsoldCropsOfAFarmer(@PathVariable("femail") String farmerEmail)
	{
		return sellRequestService.viewUnsoldCropsOfAFarmer(farmerEmail);
	}

	@RequestMapping(value = "/viewAllCropType", method = RequestMethod.GET)
	public List<String> viewAllCropType() {
		return cropService.viewAllCropTypes();
	}

	@RequestMapping(value = "/viewAllCropNameByCropType/{cropType}", method = RequestMethod.GET)
	public List<String> viewAllCropNameByCropType(@PathVariable("cropType") String cropType) {
		return cropService.findAllCropNamesByCropType(cropType);
	}
	@RequestMapping(value="/updateInsurance",method=RequestMethod.POST)
	public long updateInsurance(@RequestBody Insurance insurance)
	{
		return insuranceService.updateInsurance(insurance);
	}
}	
