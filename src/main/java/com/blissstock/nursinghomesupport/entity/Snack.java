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
@Table(name="MEALS_INPUT_SNACKS")

public class Snack {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="snack_id")
	@Id
	private Long snack_id;
	
	@Column(name="snack_name")
	private String snackName;

	@Column(name="snack_consumption_amount")
	private String snackConsumptionAmount;
	
	@Column(name="drink_name")
	private String drinkNmae;
	
	@Column(name="drink_consumption_amount")
	private String drinkConsumptionAmount;
	
	@Column(name="observation_content")
	private String observationContent;
	
	//mapping
	@OneToOne(mappedBy="snack", fetch=FetchType.LAZY)
	@JsonIgnore
	private DailyRecord dailyRecord;
	
	//constructors
	public Snack() {}
	public Snack( String snackName, String snackConsumptionAmount, String drinkNmae,
			String drinkConsumptionAmount, String observationContent, DailyRecord dailyRecord) {
		super();
		this.snackName = snackName;
		this.snackConsumptionAmount = snackConsumptionAmount;
		this.drinkNmae = drinkNmae;
		this.drinkConsumptionAmount = drinkConsumptionAmount;
		this.observationContent = observationContent;
		this.dailyRecord = dailyRecord;
	}
	
	public Long getSnack_id() {
		return snack_id;
	}

	public void setSnack_id(Long snack_id) {
		this.snack_id = snack_id;
	}

	public String getSnackName() {
		return snackName;
	}

	public void setSnackName(String snackName) {
		this.snackName = snackName;
	}

	public String getSnackConsumptionAmount() {
		return snackConsumptionAmount;
	}

	public void setSnackConsumptionAmount(String snackConsumptionAmount) {
		this.snackConsumptionAmount = snackConsumptionAmount;
	}

	public String getDrinkNmae() {
		return drinkNmae;
	}

	public void setDrinkNmae(String drinkNmae) {
		this.drinkNmae = drinkNmae;
	}

	public String getDrinkConsumptionAmount() {
		return drinkConsumptionAmount;
	}

	public void setDrinkConsumptionAmount(String drinkConsumptionAmount) {
		this.drinkConsumptionAmount = drinkConsumptionAmount;
	}

	public String getObservationContent() {
		return observationContent;
	}

	public void setObservationContent(String observationContent) {
		this.observationContent = observationContent;
	}

	public DailyRecord getDailyRecord() {
		return dailyRecord;
	}

	public void setDailyRecord(DailyRecord dailyRecord) {
		this.dailyRecord = dailyRecord;
	}


//	@Override
//	public String toString() {
//		return "Snack [snack_id=" + snack_id + ", snackName=" + snackName + ", snackConsumptionAmount="
//				+ snackConsumptionAmount + ", drinkNmae=" + drinkNmae + ", drinkConsumptionAmount="
//				+ drinkConsumptionAmount + ", observationContent=" + observationContent + ", dailyRecord=" + dailyRecord
//				+ "]";
//	}
//	
	
}
