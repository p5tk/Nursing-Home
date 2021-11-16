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
@Table(name="DAILY_RECORD_AND_MEDICATION")

public class Medication {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="daily_rec_and_medication_id")
	@Id
	private Long dailyRecAndMedicationID;
	
	@Column(name="medicine_name")
	private String medicineName;
	
	@Column(name="medication_observation")
	private String medicationObservation;
	

	//mapping
	@OneToOne(mappedBy="medication", fetch=FetchType.LAZY)
	@JsonIgnore
	private DailyRecord dailyRecord;
	
	public Medication(){}
	public Medication(String medicineName,String medicationObservation, DailyRecord dailyRecord) {
		super();
		this.medicineName = medicineName;
		this.medicationObservation = medicationObservation;
		this.dailyRecord = dailyRecord;
	}
	public Long getDailyRecAndMedicationID() {
		return dailyRecAndMedicationID;
	}
	public void setDailyRecAndMedicationID(Long dailyRecAndMedicationID) {
		this.dailyRecAndMedicationID = dailyRecAndMedicationID;
	}
	public String getMedicineName() {
		return medicineName;
	}
	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}
	public String getMedicationObservation() {
		return medicationObservation;
	}
	public void setMedicationObservation(String medicationObservation) {
		this.medicationObservation = medicationObservation;
	}
	public DailyRecord getDailyRecord() {
		return dailyRecord;
	}
	public void setDailyRecord(DailyRecord dailyRecord) {
		this.dailyRecord = dailyRecord;
	}
	
//
//	@Override
//	public String toString() {
//		return "Medication [dailyRecAndMedicationID=" + dailyRecAndMedicationID + ", dailyRecordID=" + dailyRecordID
//				+ ", medicineName=" + medicineName + ", medicineConsumptionAmount=" + medicineConsumptionAmount
//				+ ", dailyRecord=" + dailyRecord + "]";
//	}
	
}
