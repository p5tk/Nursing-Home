package com.blissstock.nursinghomesupport.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="DAILY_RECORD_AND_OTHERS_INPUT")
public class OtherActivity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="daily_record_and_other_id")
	@Id
	private Long dailyRecordAndOtherID;
	
	@Column(name="activity_name")
	private String activityName;
	
	@Column(name="activity_obser_content")
	private String activityObserContent;
	
	//mapping
	@OneToOne(mappedBy="otherActivity", fetch=FetchType.LAZY)
	@JsonIgnore
	private DailyRecord dailyRecord;
	
	public OtherActivity() {}
	public OtherActivity(String activityName, String activityObserContent,
			DailyRecord dailyRecord) {
		super();
		this.activityName = activityName;
		this.activityObserContent = activityObserContent;
		this.dailyRecord = dailyRecord;
	}

	public Long getDailyRecordAndOtherID() {
		return dailyRecordAndOtherID;
	}

	public void setDailyRecordAndOtherID(Long dailyRecordAndOtherID) {
		this.dailyRecordAndOtherID = dailyRecordAndOtherID;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityObserContent() {
		return activityObserContent;
	}

	public void setActivityObserContent(String activityObserContent) {
		this.activityObserContent = activityObserContent;
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
//		return "OtherActivity [dailyRecordAndOtherID=" + dailyRecordAndOtherID + ", dailyRecordID=" + dailyRecordID
//				+ ", activityName=" + activityName + ", activityObserContent=" + activityObserContent + ", dailyRecord="
//				+ dailyRecord + "]";
//	}
	
	
	
	
}
