package com.lti.dto;

import org.springframework.web.multipart.MultipartFile;

public class DocumentDto {
	long id;
	MultipartFile pancard;
	MultipartFile aadharCard;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
		public MultipartFile getPancard() {
		return pancard;
	}
	public void setPancard(MultipartFile pancard) {
		this.pancard = pancard;
	}
	public MultipartFile getAadharCard() {
		return aadharCard;
	}
	public void setAadharCard(MultipartFile aadharCard) {
		this.aadharCard = aadharCard;
	}
	
}
