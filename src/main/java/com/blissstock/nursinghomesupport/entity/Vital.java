package com.blissstock.nursinghomesupport.entity;

import java.math.BigDecimal;

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
@Table(name="VITALS_INPUT")


public class Vital {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="vital_id")
	@Id
	private Long vitalID;
	
	@Column(name="bt_am")
	private BigDecimal btAM;
	
	@Column(name="bt_pm")
	private BigDecimal btPM;
	
	@Column(name="bp_am_upper")
	private Integer bpAMUpper;
	
	@Column(name="bp_am_lower")
	private Integer bpAMLower;
	
	@Column(name="bp_pm_upper")
	private Integer bpPMUpper;
	
	@Column(name="bp_pm_lower")
	private Integer bpPMLower;
	
	@Column(name="body_weight")
	private Integer bodyWeight;

	@Column(name="pee_ml")
	private Integer peeMl;
	
	@Column(name="pee_times")
	private Integer peeTimes;
	
	@Column(name="poop_volume")
	private String poopVolume;
	
	@Column(name="poop_times")
	private Integer poopTimes;
	
	//mapping
	@OneToOne(mappedBy="vital", fetch=FetchType.LAZY)
	@JsonIgnore
	private DailyRecord dailyRecord;
	
	//constructors
	public Vital() {}
	public Vital(BigDecimal btAM, BigDecimal btPM, Integer bpAMUpper, Integer bpAMLower, Integer bpPMUpper, Integer bpPMLower,
			Integer bodyWeight, Integer peeMl, Integer peeTimes, String poopVolume, Integer poopTimes) {
		super();
		this.btAM = btAM;
		this.btPM = btPM;
		this.bpAMUpper = bpAMUpper;
		this.bpAMLower = bpAMLower;
		this.bpPMUpper = bpPMUpper;
		this.bpPMLower = bpPMLower;
		this.bodyWeight = bodyWeight;
		this.peeMl = peeMl;
		this.peeTimes = peeTimes;
		this.poopVolume = poopVolume;
		this.poopTimes = poopTimes;
	}
	
	//getters and setters
	public Long getVitalID() {
		return vitalID;
	}

	public void setVitalID(Long vitalID) {
		this.vitalID = vitalID;
	}

	public BigDecimal getBtAM() {
		return btAM;
	}

	public void setBtAM(BigDecimal btAM) {
		this.btAM = btAM;
	}

	public BigDecimal getBtPM() {
		return btPM;
	}

	public void setBtPM(BigDecimal btPM) {
		this.btPM = btPM;
	}

	public Integer getBpAMUpper() {
		return bpAMUpper;
	}

	public void setBpAMUpper(Integer bpAMUpper) {
		this.bpAMUpper = bpAMUpper;
	}

	public Integer getBpAMLower() {
		return bpAMLower;
	}

	public void setBpAMLower(Integer bpAMLower) {
		this.bpAMLower = bpAMLower;
	}

	public Integer getBpPMUpper() {
		return bpPMUpper;
	}

	public void setBpPMUpper(Integer bpPMUpper) {
		this.bpPMUpper = bpPMUpper;
	}

	public Integer getBpPMLower() {
		return bpPMLower;
	}

	public void setBpPMLower(Integer bpPMLower) {
		this.bpPMLower = bpPMLower;
	}

	public Integer getBodyWeight() {
		return bodyWeight;
	}

	public void setBodyWeight(Integer bodyWeight) {
		this.bodyWeight = bodyWeight;
	}

	public Integer getPeeMl() {
		return peeMl;
	}

	public void setPeeMl(Integer peeMl) {
		this.peeMl = peeMl;
	}

	public Integer getPeeTimes() {
		return peeTimes;
	}

	public void setPeeTimes(Integer peeTimes) {
		this.peeTimes = peeTimes;
	}


	public String getPoopVolume() {
		return poopVolume;
	}

	public void setPoopVolume(String poopVolume) {
		this.poopVolume = poopVolume;
	}

	public Integer getPoopTimes() {
		return poopTimes;
	}

	public void setPoopTimes(Integer poopTimes) {
		this.poopTimes = poopTimes;
	}

	public DailyRecord getDailyRecord() {
		return dailyRecord;
	}

	public void setDailyRecord(DailyRecord dailyRecord) {
		this.dailyRecord = dailyRecord;
	}



//	@Override
//	public String toString() {
//		return "Vital [vitalID=" + vitalID + ", btAM=" + btAM + ", btPM=" + btPM + ", bpAMUpper=" + bpAMUpper
//				+ ", bpAMLower=" + bpAMLower + ", bpPMUpper=" + bpPMUpper + ", bpPMLower=" + bpPMLower + ", bodyWeight="
//				+ bodyWeight + ", peeMl=" + peeMl + ", peeTimes=" + peeTimes + ", poopVolume=" + poopVolume
//				+ ", poopTimes=" + poopTimes + "]";
//	}
	
	
	
}
