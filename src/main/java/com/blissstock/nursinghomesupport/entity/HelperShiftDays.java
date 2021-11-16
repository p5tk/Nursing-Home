
package com.blissstock.nursinghomesupport.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="helper_shift_days")
public class HelperShiftDays {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="helper_shiftday_id")
	private Long helperShiftDayId;

	@Column(name="helper_shiftday_name")
	private String shiftDayName;
	
	//mapping
	
	  /*@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	  @JsonIgnore 
	  private HelperInfo helpers;*/
	
	  @ManyToMany(mappedBy = "helper_shift_days",fetch=FetchType.EAGER)
	  @JsonIgnore
	  private List<HelperInfo> helperInfo = new ArrayList<>();
	  
	  public void addHelper(HelperInfo helper) {
		  helperInfo.add(helper);
		  helper.getHelper_shift_days().add(this);
	  }
//	  void addHelper(HelperInfo helper,boolean stat) {
//		  if(helper!=null) {
//			  if(getHelperInfo().contains(helper)) {
//				  getHelperInfo().set(getHelperInfo().indexOf(helper), helper);
//			  }else {
//				  getHelperInfo().add(helper);
//			  }
//			  if(stat) {
//				  helper.addShiftDayOfHelper(this,false);
//			  }
//		  }
//	  }
			
	public Long getHelperShiftDayId() {
		return helperShiftDayId;
	}
	public void setHelperShiftDayId(Long helperShiftDayId) {
		this.helperShiftDayId = helperShiftDayId;
	}
	public String getShiftDayName() {
		return shiftDayName;
	}
	public void setShiftDayName(String shiftDayName) {
		this.shiftDayName = shiftDayName;
	}
	
	public List<HelperInfo> getHelperInfo() {
		return helperInfo;
	}
	public void setHelperInfo(List<HelperInfo> helperInfo) {
		this.helperInfo = helperInfo;
	}
	//constructors
	public HelperShiftDays() {
		super();
	}
	public HelperShiftDays(String shiftDayName, List<HelperInfo> helperInfo) {
		super();
		this.shiftDayName = shiftDayName;
		this.helperInfo = helperInfo;
	}
	@Override
	public String toString() {
		return "HelperShiftDays [helperShiftDayId=" + helperShiftDayId + ", shiftDayName=" + shiftDayName
				+ "]";
	}
	
}

