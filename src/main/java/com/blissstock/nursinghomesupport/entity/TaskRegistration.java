package com.blissstock.nursinghomesupport.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.blissstock.nursinghomesupport.repository.ElderRepository;
// Display values in tabs and get modal check boxes checked values
public class TaskRegistration {

	private Long id;
	private String name;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	/* @Temporal(TemporalType.DATE) */
	private LocalDate date;
	private String adminRemark0;
	private String adminRemark1;
	private String adminRemark2;
	private String adminRemark3;
	private LocalTime otherStartTime;
	private LocalTime otherEndTime;
	private String otherActivityName;
	private String otherRemark;
	

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public LocalDate getDate() {
		return date;
	}



	public void setDate(LocalDate date) {
		this.date = date;
	}



	public LocalTime getOtherStartTime() {
		return otherStartTime;
	}



	public void setOtherStartTime(LocalTime otherStartTime) {
		this.otherStartTime = otherStartTime;
	}



	public LocalTime getOtherEndTime() {
		return otherEndTime;
	}



	public void setOtherEndTime(LocalTime otherEndTime) {
		this.otherEndTime = otherEndTime;
	}



	public String getOtherActivityName() {
		return otherActivityName;
	}



	public void setOtherActivityName(String otherActivityName) {
		this.otherActivityName = otherActivityName;
	}



	public String getOtherRemark() {
		return otherRemark;
	}



	public void setOtherRemark(String otherRemark) {
		this.otherRemark = otherRemark;
	}

	public String getadminRemark0() {
		return adminRemark0;
	}



	public void setadminRemark0(String adminRemark0) {
		this.adminRemark0 = adminRemark0;
	}



	public String getadminRemark1() {
		return adminRemark1;
	}



	public void setadminRemark1(String adminRemark1) {
		this.adminRemark1 = adminRemark1;
	}



	public String getadminRemark2() {
		return adminRemark2;
	}



	public void setadminRemark2(String adminRemark2) {
		this.adminRemark2 = adminRemark2;
	}



	public String getadminRemark3() {
		return adminRemark3;
	}



	public void setadminRemark3(String adminRemark3) {
		this.adminRemark3 = adminRemark3;
	}



	public TaskRegistration() {
		super();
	}
	

	public TaskRegistration(Long id, String name, LocalDate date, String adminRemark0, String adminRemark1,
			String adminRemark2, String adminRemark3, LocalTime otherStartTime, LocalTime otherEndTime,
			String otherActivityName, String otherRemark) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.adminRemark0 = adminRemark0;
		this.adminRemark1 = adminRemark1;
		this.adminRemark2 = adminRemark2;
		this.adminRemark3 = adminRemark3;
		this.otherStartTime = otherStartTime;
		this.otherEndTime = otherEndTime;
		this.otherActivityName = otherActivityName;
		this.otherRemark = otherRemark;
	}



	@Override
	public String toString() {
		return "TaskRegistration [id=" + id + ", name=" + name + ", date=" + date + ", adminRemark0=" + adminRemark0
				+ ", adminRemark1=" + adminRemark1 + ", adminRemark2=" + adminRemark2 + ", adminRemark3=" + adminRemark3
				+ ", otherStartTime=" + otherStartTime + ", otherEndTime=" + otherEndTime + ", otherActivityName="
				+ otherActivityName + ", otherRemark=" + otherRemark + "]";
	}


}
