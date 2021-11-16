package com.blissstock.nursinghomesupport.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="elder_hobby")
public class ElderHobbies {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="elder_hobby_id")
	private Long elderHobbyId;
	
	@Column(name="hobby_name")
	private String hobbyName;

	
	//mapping
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnore
	private ElderInfo elder;
	
	//constructors
	public ElderHobbies() {}
	public ElderHobbies(String name) {
		this.hobbyName=name;
	}
	
	//getters and setters
	public Long getElderHobbyId() {
		return elderHobbyId;
	}
	public void setElderHobbyId(Long elderHobbyId) {
		this.elderHobbyId = elderHobbyId;
	}
	public String getHobbyName() {
		return hobbyName;
	}
	public void setHobbyName(String hobbyName) {
		this.hobbyName = hobbyName;
	}
	public ElderInfo getElder() {
		return elder;
	}
	public void setElder(ElderInfo elder) {
		this.elder = elder;
	}

	
	
	
}
