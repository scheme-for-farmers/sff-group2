package com.lti.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class ApplyInsurance {
	@Id
	@SequenceGenerator(name="seq_insuranceApplied",initialValue=6000,allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq_insuranceApplied")
	long policyNo;
	LocalDate validFrom;
	LocalDate validTill;
	double premiumAmount;
	double area;
	double sumInsured;
	String approve;
	
	@ManyToOne
	@JoinColumn(name="InsuranceId")
	Insurance insurance;
	
	@ManyToOne
	@JoinColumn(name="farmerId")
	Farmer farmer;
	
	@OneToOne
	@JoinColumn(name="cropId")
	Crop crop;
}
