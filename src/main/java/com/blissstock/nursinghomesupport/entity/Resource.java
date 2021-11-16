package com.blissstock.nursinghomesupport.entity;

import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Resource {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@JsonProperty("id")
	private Long id;
	@JsonProperty("title")
	private String title;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public Resource() {
		super();
	}
	
	
	public Resource(String title) {
		super();
		this.title = title;
	}
	@Override
	public String toString() {
		return "Resource [id=" + id + ", title=" + title + "]";
	}
	
	
	
	
}
