package com.blissstock.nursinghomesupport.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="PERSONAL_CARE_INPUT")

public class PersonalCare {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="personal_care_id")
	@Id
	private Long personalCareID;
	
	@Column(name="oral_care_am")
	private String oralCareAM;
	
	@Column(name="oral_care_pm")
	private String oralCarePM;
	
	@Column(name="bathing")
	private String bathing;
	
	@Column(name="exercise")
	private String exercise;
	
	//mapping
	@OneToOne(mappedBy="personalCare", fetch=FetchType.LAZY)
	@JsonIgnore
	private DailyRecord dailyRecord;
	
	//constructors
	public PersonalCare() {}
	public PersonalCare(String oralCareAM, String oralCarePM, String bathing, String exercise,
			DailyRecord dailyRecord) {
		super();
		this.oralCareAM = oralCareAM;
		this.oralCarePM = oralCarePM;
		this.bathing = bathing;
		this.exercise = exercise;
		this.dailyRecord = dailyRecord;
	}

	//getters and setters
	public Long getPersonalCareID() {
		return personalCareID;
	}

	public void setPersonalCareID(Long personalCareID) {
		this.personalCareID = personalCareID;
	}

	
	
	public String getOralCareAM() {
		return oralCareAM;
	}

	public void setOralCareAM(String oralCareAM) {
		this.oralCareAM = oralCareAM;
	}

	public String getOralCarePM() {
		return oralCarePM;
	}

	public void setOralCarePM(String oralCarePM) {
		this.oralCarePM = oralCarePM;
	}

	public String getBathing() {
		return bathing;
	}

	public void setBathing(String bathing) {
		this.bathing = bathing;
	}

	public String getExercise() {
		return exercise;
	}

	public void setExercise(String exercise) {
		this.exercise = exercise;
	}

	public DailyRecord getDailyRecord() {
		return dailyRecord;
	}

	public void setDailyRecord(DailyRecord dailyRecord) {
		this.dailyRecord = dailyRecord;
	}


//	@Override
//	public String toString() {
//		return "PersonalCare [personalCareID=" + personalCareID + ", oralCareAM=" + oralCareAM + ", oralCarePM="
//				+ oralCarePM + ", bathing=" + bathing + ", exercise=" + exercise + ", dailyRecord=" + dailyRecord + "]";
//	}
	
	

}
