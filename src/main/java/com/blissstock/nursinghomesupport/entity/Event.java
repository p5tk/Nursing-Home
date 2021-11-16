package com.blissstock.nursinghomesupport.entity;

import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Event {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@JsonProperty("id")
	private Long id;
	@Id
	@JsonProperty("resourceId")
	private Long resourceId;
	@JsonProperty("title")
	private String title;
	@JsonProperty("start")
	private LocalDateTime start;
	@JsonProperty("end")
	private LocalDateTime finish;
	@JsonProperty("url")
	private String url="";
	@JsonProperty("color")
	private String color;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getResourceId() {
		return resourceId;
	}
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public LocalDateTime getStart() {
		return start;
	}
	public void setStart(LocalDateTime start) {
		this.start = start;
	}
	public LocalDateTime getFinish() {
		return finish;
	}
	public void setFinish(LocalDateTime finish) {
		this.finish = finish;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Event() {
		super();
	}
	
	public Event(String title, LocalDateTime start, LocalDateTime finish, String eventColor, String url) {
		super();
		this.title = title;
		this.start = start;
		this.finish = finish;
		this.url = url;
	}
	@Override
	public String toString() {
		return "Event [id=" + id + ", resourceId=" + resourceId + ", title=" + title + ", start=" + start + ", finish="
				+ finish + ", url=" + url + ", color=" + color + "]";
	}
	
}
