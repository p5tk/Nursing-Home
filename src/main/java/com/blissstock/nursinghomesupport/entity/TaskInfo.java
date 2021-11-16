package com.blissstock.nursinghomesupport.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "task")
public class TaskInfo {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@JsonProperty("id")
	private Long id;
	@NotBlank(message="Please input activity name")
	@JsonProperty("title")
	private String title;
	@JsonProperty("start")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime start;
	@JsonProperty("end")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime finish;
	/* @Temporal(TemporalType.DATE) */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate assignedDate;
	private String adminTaskRemark;
	
	private String taskStatus;
	private String helperTaskRemark;
	@JsonProperty("url")
	@Transient
	private String url="";
	@JsonProperty("color")
	@Transient
	private String color;
	@Transient
	private List<Long> checkedElders= new ArrayList<Long>();
	
	@Transient
	private List<Long> checkedFirstShift= new ArrayList<Long>();
	@Transient
	private List<Long> checkedSecondShift= new ArrayList<Long>();
	@Transient
	private List<Long> checkedThirdShift= new ArrayList<Long>();
	@Transient
	private List<Long> checkedForthShift= new ArrayList<Long>();
	@Transient
	private List<Long> checkedOtherActivity= new ArrayList<Long>();
	
	 @ManyToMany(fetch = FetchType.LAZY)
	 @JoinTable(name = "elder_task",joinColumns = { @JoinColumn(name = "task_id") },inverseJoinColumns = { @JoinColumn(name = "elder_id") })
	 private List<ElderInfo> elderInfo = new ArrayList<>();
	 
	 @ManyToOne(fetch = FetchType.EAGER, optional = false)
	 @JoinColumn(name = "helper_id_fkey")
	 @JsonIgnore
	 private HelperInfo helperInfo;
	 
	
	
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
	public LocalDate getAssignedDate() {
		return assignedDate;
	}
	public void setAssignedDate(LocalDate assignedDate) {
		this.assignedDate = assignedDate;
	}
	public List<ElderInfo> getElderInfo() {
		return elderInfo;
	}
	public void setElderInfo(List<ElderInfo> elderInfo) {
		this.elderInfo = elderInfo;
	}
	
	public String getAdminTaskRemark() {
		return adminTaskRemark;
	}
	public void setAdminTaskRemark(String adminTaskRemark) {
		this.adminTaskRemark = adminTaskRemark;
	}
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	
	public String getHelperTaskRemark() {
		return helperTaskRemark;
	}
	public void setHelperTaskRemark(String helperTaskRemark) {
		this.helperTaskRemark = helperTaskRemark;
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
		
		
	public List<Long> getCheckedFirstShift() {
			return checkedFirstShift;
		}
		public void setCheckedFirstShift(List<Long> checkedFirstShift) {
			this.checkedFirstShift = checkedFirstShift;
		}
		public List<Long> getCheckedSecondShift() {
			return checkedSecondShift;
		}
		public void setCheckedSecondShift(List<Long> checkedSecondShift) {
			this.checkedSecondShift = checkedSecondShift;
		}
		public List<Long> getCheckedThirdShift() {
			return checkedThirdShift;
		}
		public void setCheckedThirdShift(List<Long> checkedThirdShift) {
			this.checkedThirdShift = checkedThirdShift;
		}
		public List<Long> getCheckedForthShift() {
			return checkedForthShift;
		}
		public void setCheckedForthShift(List<Long> checkedForthShift) {
			this.checkedForthShift = checkedForthShift;
		}
		public List<Long> getCheckedOtherActivity() {
			return checkedOtherActivity;
		}
		public void setCheckedOtherActivity(List<Long> checkedOtherActivity) {
			this.checkedOtherActivity = checkedOtherActivity;
		}
	public TaskInfo() {
		super();
	}
	
	public List<Long> getCheckedElders() {
		return checkedElders;
	}
	public void setCheckedElders(List<Long> checkedElders) {
		this.checkedElders = checkedElders;
	}
	public TaskInfo(String title, LocalDateTime start, LocalDateTime finish, LocalDate assignedDate,
			String adminTaskRemark, String taskStatus, String helperTaskRemark, String color,
			List<ElderInfo> elderInfo, HelperInfo helperInfo) {
		super();
		this.title = title;
		this.start = start;
		this.finish = finish;
		this.assignedDate = assignedDate;
		this.adminTaskRemark = adminTaskRemark;
		this.taskStatus = taskStatus;
		this.helperTaskRemark = helperTaskRemark;
		this.color = color;
		this.elderInfo = elderInfo;
		this.helperInfo = helperInfo;
	}
	
	@Override
	public String toString() {
		return "TaskInfo [id=" + id + ", title=" + title + ", start=" + start + ", finish=" + finish + ", assignedDate="
				+ assignedDate + ", adminTaskRemark=" + adminTaskRemark + ", taskStatus=" + taskStatus
				+ ", helperTaskRemark=" + helperTaskRemark + ", url=" + url + ", color=" + color + "]";
	}
	public HelperInfo getHelperInfo() {
		return helperInfo;
	}
	public void setHelperInfo(HelperInfo helperInfo) {
		this.helperInfo = helperInfo;
	}
}
	
	
	
	
