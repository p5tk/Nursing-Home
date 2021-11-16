package com.blissstock.nursinghomesupport.entity;

import java.time.LocalDate;

public class SearchCriteria {

   
    String helperName;
    LocalDate date;
    Long helperId;

	public String getHelperName() {
		return helperName;
	}



	public void setHelperName(String helperName) {
		this.helperName = helperName;
	}
	
	


	public LocalDate getDate() {
		return date;
	}



	public void setDate(LocalDate date) {
		this.date = date;
	}

	

	public Long getHelperId() {
		return helperId;
	}



	public void setHelperId(Long helperId) {
		this.helperId = helperId;
	}



	@Override
	public String toString() {
		return "SearchCriteria [helperName=" + helperName + ", date=" + date + ", helperId=" + helperId + "]";
	}

}