package com.blissstock.nursinghomesupport.entity;


import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="ELDERS_DAILY_RECORD")

public class DailyRecord {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="daily_record_id")
	@Id
	private Long dailyRecordID;
	
	@Column(name="date")
	private Date date;
	
	
	//mappings
	@ManyToOne(fetch=FetchType.EAGER)
	private ElderInfo elder;

	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Vital vital;
	
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Meal meal;
	
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Snack snack;
	
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private PersonalCare personalCare;
	
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Medication medication;
	
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private OtherActivity otherActivity;
	
	//constructors
	public DailyRecord() {}
	public DailyRecord(ElderInfo elder, Date date, Vital vital, Meal meal, Snack snack,
			PersonalCare personalCare,Medication medication,OtherActivity other) {
		super();
		this.elder = elder;
		this.date = date;
		this.vital = vital;
		this.meal = meal;
		this.snack = snack;
		this.personalCare = personalCare;
		this.medication=medication;
		this.otherActivity=other;		
	}

	//getters and setters
	public Long getDailyRecordID() {
		return dailyRecordID;
	}

	public void setDailyRecordID(Long dailyRecordID) {
		this.dailyRecordID = dailyRecordID;
	}

	public ElderInfo getElder() {
		return elder;
	}

	public void setElder(ElderInfo elder) {
		this.elder = elder;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	public Vital getVital() {
		return vital;
	}


	public void setVital(Vital vital) {
		this.vital = vital;
		vital.setDailyRecord(this);
	}


	public Meal getMeal() {
		return meal;
	}


	public void setMeal(Meal meal) {
		this.meal = meal;
		meal.setDailyRecord(this);
	}


	public Snack getSnack() {
		return snack;
	}


	public void setSnack(Snack snack) {
		this.snack = snack;
		snack.setDailyRecord(this);
	}


	public PersonalCare getPersonalCare() {
		return personalCare;
	}


	public void setPersonalCare(PersonalCare personalCare) {
		this.personalCare = personalCare;
		personalCare.setDailyRecord(this);
	}
	public Medication getMedication() {
		return medication;
	}
	public void setMedication(Medication medication) {
		this.medication = medication;
		medication.setDailyRecord(this);
	}
	public OtherActivity getOtherActivity() {
		return otherActivity;
	}
	public void setOtherActivity(OtherActivity otherActivity) {
		this.otherActivity = otherActivity;
		otherActivity.setDailyRecord(this);
	}


//	@Override
//	public String toString() {
//		return "DailyRecord [dailyRecordID=" + dailyRecordID + ", elderID=" + elder + ", date=" + /*date +*/ ", vital="
//				+ vital + ", meal=" + meal + ", snack=" + snack + ", medicalObservationContent="
//				+", personalCare=" + personalCare + "]";
//	}

	
}
