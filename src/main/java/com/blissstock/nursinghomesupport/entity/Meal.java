package com.blissstock.nursinghomesupport.entity;

import javax.persistence.CascadeType;
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
@Table(name="MEALS_INPUT")

public class Meal {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="meal_id")
	@Id
	private Long mealID;
	
	@Column(name="breakfast_sf_name")
	private String breakfastSFName;
	
	@Column(name="breakfast_sf_amount")
	private String breakfastSFAmount;
	
	@Column(name="breakfast_sd_name")
	private String breakfastSDName;
	
	@Column(name="breakfast_sd_amount")
	private String breakfastSDAmount;
	
	@Column(name="breakfast_soup_name")
	private String breakfastSoupName;
	
	@Column(name="breakfast_soup_amount")
	private String breakfastSoupAmount;
	
	@Column(name="breakfast_observation_content")
	private String breakfasObservationContent;;
	
	@Column(name="luncht_sf_name")
	private String lunchSFName;

	@Column(name="lunch_sf_amount")
	private String lunchSFAmount;
	
	@Column(name="lunch_sd_name")
	private String lunchSDName;
	
	@Column(name="lunch_sd_amount")
	private String lunchSDAmount;
	
	@Column(name="lunch_soup_name")
	private String lunchSoupName;
	
	@Column(name="lunch_soup_amount")
	private String lunchSoupAmount;
	
	@Column(name="lunch_observation_content")
	private String lunchObservationContent;
	
	@Column(name="dinner_sf_name")
	private String dinnerSFName;
	
	@Column(name="dinner_sf_amount")
	private String dinnerSFAmount;
	
	@Column(name="dinner_sd_name")
	private String dinnerSDName;
	
	@Column(name="dinner_sd_amount")
	private String dinnerSDAmount;
	
	@Column(name="dinner_soup_name")
	private String dinnerSoupName;
	
	@Column(name="dinner_soup_amount")
	private String dinnerSoupAmount;
	
	@Column(name="dinner_observation_content")
	private String dinnerObservationContent;
	
	//mapping
	@OneToOne(mappedBy="meal", fetch=FetchType.LAZY)
	@JsonIgnore
	private DailyRecord dailyRecord;
	
	//constructors
	public Meal() {}
	public Meal(String breakfastSFName, String breakfastSFAmount, String breakfastSDName,
			String breakfastSDAmount, String breakfastSoupName, String breakfastSoupAmount, String breakfasObservationContent,
			String lunchSFName, String lunchSFAmount, String lunchSDName, String lunchSDAmount, String lunchSoupName,
			String lunchSoupAmount, String lunchObservationContent, String dinnerSFName, String dinnerSFAmount,
			String dinnerSDName, String dinnerSDAmount, String dinnerSoupName, String dinnerSoupAmount,
			String dinnerObservationContent, DailyRecord dailyRecord) {
		super();
		this.breakfastSFName = breakfastSFName;
		this.breakfastSFAmount = breakfastSFAmount;
		this.breakfastSDName = breakfastSDName;
		this.breakfastSDAmount = breakfastSDAmount;
		this.breakfastSoupName = breakfastSoupName;
		this.breakfastSoupAmount = breakfastSoupAmount;
		this.breakfasObservationContent = breakfasObservationContent;
		this.lunchSFName = lunchSFName;
		this.lunchSFAmount = lunchSFAmount;
		this.lunchSDName = lunchSDName;
		this.lunchSDAmount = lunchSDAmount;
		this.lunchSoupName = lunchSoupName;
		this.lunchSoupAmount = lunchSoupAmount;
		this.lunchObservationContent = lunchObservationContent;
		this.dinnerSFName = dinnerSFName;
		this.dinnerSFAmount = dinnerSFAmount;
		this.dinnerSDName = dinnerSDName;
		this.dinnerSDAmount = dinnerSDAmount;
		this.dinnerSoupName = dinnerSoupName;
		this.dinnerSoupAmount = dinnerSoupAmount;
		this.dinnerObservationContent = dinnerObservationContent;
		this.dailyRecord = dailyRecord;
	}
	
	//getters and setters
	public String getLunchSFAmount() {
		return lunchSFAmount;
	}

	public void setLunchSFAmount(String lunchSFAmount) {
		this.lunchSFAmount = lunchSFAmount;
	}

	public Long getMealID() {
		return mealID;
	}

	public void setMealID(Long mealID) {
		this.mealID = mealID;
	}

	public String getBreakfastSFName() {
		return breakfastSFName;
	}

	public void setBreakfastSFName(String breakfastSFName) {
		this.breakfastSFName = breakfastSFName;
	}

	public String getBreakfastSFAmount() {
		return breakfastSFAmount;
	}

	public void setBreakfastSFAmount(String breakfastSFAmount) {
		this.breakfastSFAmount = breakfastSFAmount;
	}

	public String getBreakfastSDName() {
		return breakfastSDName;
	}

	public void setBreakfastSDName(String breakfastSDName) {
		this.breakfastSDName = breakfastSDName;
	}

	public String getBreakfastSDAmount() {
		return breakfastSDAmount;
	}

	public void setBreakfastSDAmount(String breakfastSDAmount) {
		this.breakfastSDAmount = breakfastSDAmount;
	}

	public String getBreakfastSoupName() {
		return breakfastSoupName;
	}

	public void setBreakfastSoupName(String breakfastSoupName) {
		this.breakfastSoupName = breakfastSoupName;
	}

	public String getBreakfastSoupAmount() {
		return breakfastSoupAmount;
	}

	public void setBreakfastSoupAmount(String breakfastSoupAmount) {
		this.breakfastSoupAmount = breakfastSoupAmount;
	}

	public String getBreakfasObservationContent() {
		return breakfasObservationContent;
	}

	public void setBreakfasObservationContent(String breakfasObservationContent) {
		this.breakfasObservationContent = breakfasObservationContent;
	}

	public String getLunchSFName() {
		return lunchSFName;
	}

	public void setLunchSFName(String lunchSFName) {
		this.lunchSFName = lunchSFName;
	}

	public String getLunchSDName() {
		return lunchSDName;
	}

	public void setLunchSDName(String lunchSDName) {
		this.lunchSDName = lunchSDName;
	}

	public String getLunchSDAmount() {
		return lunchSDAmount;
	}

	public void setLunchSDAmount(String lunchSDAmount) {
		this.lunchSDAmount = lunchSDAmount;
	}

	public String getLunchSoupName() {
		return lunchSoupName;
	}

	public void setLunchSoupName(String lunchSoupName) {
		this.lunchSoupName = lunchSoupName;
	}

	public String getLunchSoupAmount() {
		return lunchSoupAmount;
	}

	public void setLunchSoupAmount(String lunchSoupAmount) {
		this.lunchSoupAmount = lunchSoupAmount;
	}

	public String getLunchObservationContent() {
		return lunchObservationContent;
	}

	public void setLunchObservationContent(String lunchObservationContent) {
		this.lunchObservationContent = lunchObservationContent;
	}

	public String getDinnerSFName() {
		return dinnerSFName;
	}

	public void setDinnerSFName(String dinnerSFName) {
		this.dinnerSFName = dinnerSFName;
	}

	public String getDinnerSFAmount() {
		return dinnerSFAmount;
	}

	public void setDinnerSFAmount(String dinnerSFAmount) {
		this.dinnerSFAmount = dinnerSFAmount;
	}

	public String getDinnerSDName() {
		return dinnerSDName;
	}

	public void setDinnerSDName(String dinnerSDName) {
		this.dinnerSDName = dinnerSDName;
	}

	public String getDinnerSDAmount() {
		return dinnerSDAmount;
	}

	public void setDinnerSDAmount(String dinnerSDAmount) {
		this.dinnerSDAmount = dinnerSDAmount;
	}

	public String getDinnerSoupName() {
		return dinnerSoupName;
	}

	public void setDinnerSoupName(String dinnerSoupName) {
		this.dinnerSoupName = dinnerSoupName;
	}

	public String getDinnerSoupAmount() {
		return dinnerSoupAmount;
	}

	public void setDinnerSoupAmount(String dinnerSoupAmount) {
		this.dinnerSoupAmount = dinnerSoupAmount;
	}

	public String getDinnerObservationContent() {
		return dinnerObservationContent;
	}

	public void setDinnerObservationContent(String dinnerObservationContent) {
		this.dinnerObservationContent = dinnerObservationContent;
	}

	public DailyRecord getDailyRecord() {
		return dailyRecord;
	}

	public void setDailyRecord(DailyRecord dailyRecord) {
		this.dailyRecord = dailyRecord;
	}
	
//	@Override
//	public String toString() {
//		return "Meal [mealID=" + mealID + ", breakfastSFName=" + breakfastSFName + ", breakfastSFAmount="
//				+ breakfastSFAmount + ", breakfastSDName=" + breakfastSDName + ", breakfastSDAmount="
//				+ breakfastSDAmount + ", breakfastSoupName=" + breakfastSoupName + ", breakfastSoupAmount="
//				+ breakfastSoupAmount + ", breakfasObservationContent=" + breakfasObservationContent + ", lunchSFName="
//				+ lunchSFName + ", lunchAmount=" + lunchSFAmount + ", lunchSDName=" + lunchSDName + ", lunchSDAmount="
//				+ lunchSDAmount + ", lunchSoupName=" + lunchSoupName + ", lunchSoupAmount=" + lunchSoupAmount
//				+ ", lunchObservationContent=" + lunchObservationContent + ", dinnerSFName=" + dinnerSFName
//				+ ", dinnerSFAmount=" + dinnerSFAmount + ", dinnerSDName=" + dinnerSDName + ", dinnerSDAmount="
//				+ dinnerSDAmount + ", dinnerSoupName=" + dinnerSoupName + ", dinnerSoupAmount=" + dinnerSoupAmount
//				+ ", dinnerObservationContent=" + dinnerObservationContent + ", dailyRecord=" + dailyRecord + "]";
//	}
		
}
