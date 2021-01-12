package com.lti.dto;

import org.springframework.web.multipart.MultipartFile;

public class DocumentDto {
	String mail;
	MultipartFile pancard;
	MultipartFile aadharCard;
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
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
