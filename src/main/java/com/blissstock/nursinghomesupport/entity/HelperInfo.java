package com.blissstock.nursinghomesupport.entity;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="helper_info")
public class HelperInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="helper_id")
	private Long helperId;
	
	@NotBlank(message="Please enter first name.")
	@Column(name="first_name")
	private String firstName;
	
	@NotBlank(message="Please enter last name.")
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="photo")
	private String photo;
	
	@Column(name="file1")
	private String file1;
	
	@Column(name="file2")
	private String file2;
	
	@NotBlank(message="Please choose gender.")
	@Column(name="sex")
	private String gender;
	
	@Column(name="birthDate")
	private Date birthDate;
	
	@NotBlank
	@Column(name="country_code")
	private String countryCode;   
	
	@NotBlank(message="Please enter phone number.")
    @Size(max = 10, min = 10, message = "Phone number should be of 10 digits")
	@Column(name="phone_no")
	private String phoneNo;
	
	@NotBlank(message="Please enter building or room number.")
	@Column(name="building_no")
	private String buildingNo;
	
	@NotBlank(message="Please enter street address.")
	@Column(name="street_address")
	private String streetAddress;
	
	@NotBlank(message="Please enter city.")
	@Column(name="city")
	private String city;
	
	@NotBlank(message="Please enter state.")
	@Column(name="state")
	private String state;
	
	@NotBlank(message="Please enter postal code.")
	@Column(name="postal_code")
	private String postalCode;
	
	@NotBlank(message="Please choose country.")
	@Column(name="country")
	private String country;   
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="hire_date")
	private Date hireDate;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="contract_start_date")
	private Date contractStartDate;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="contract_end_date")
	private Date contractEndDate;
	
	@NotBlank(message="Please choose hourly wage.")
	@Column(name="hourly_wage")
	private String hourlyWage;

	@NotBlank
	@Column(name="hourly_wage_currency")
	private String hourlyWageCurrency;   
	
	@NotBlank(message="Please choose shift type.")
	@Column(name="shift_type")
	private String shiftType;   
	
	@Column(name="remark")
	private String remark;
	
	@NotBlank(message="Please choose education.")
	@Column(name="education")
	private String education;
	
	@Transient
	private List<String> checkedShiftDays= new ArrayList<String>();
	
	@Transient
	private String shiftStartTime;
	
	@Transient
	private String shiftEndTime;
	
	//Mapping
		
		//@OneToMany(mappedBy="helpers",cascade=CascadeType.ALL) 
		//private List<HelperShiftDays> helper_shift_days=new ArrayList<>();
		@Valid
		@OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
		LoginUser acc;
		
	
		@ManyToMany(fetch = FetchType.EAGER)
		@JoinTable(
				name = "join_helper_shift_days", 
				joinColumns = {@JoinColumn(name = "helper_id")} ,
				inverseJoinColumns = {@JoinColumn(name = "helper_shiftday_id")}
				) 
		private List<HelperShiftDays> helper_shift_days = new ArrayList<>();
	
		@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="helperInfo")
		@JsonIgnore
		private List<TaskInfo> taskInfo= new ArrayList<>();
		
		//Constructor
		public HelperInfo() {}
		public HelperInfo(String fname,String lname,String gender,Date bd,String countryCode,
				String phoneNo,String buildingNo,String streetAddress,String city,String state,
				String postalCode,String country,Date hireDate,Date contractStartDate,Date contractEndDate,
				String hourlyWage,String hourlyWageType,String shiftType,String remark,String education)	{	
		    this.firstName=fname;
			this.lastName=lname;
			this.gender=gender;
			this.birthDate=bd;
			this.countryCode=countryCode;
			this.phoneNo=phoneNo;
			this.buildingNo=buildingNo;
			this.streetAddress=streetAddress;
			this.city=city;
			this.state=state;
			this.postalCode=postalCode;
			this.country=country;
			this.hireDate=hireDate;
			this.contractStartDate=contractStartDate;
			this.contractEndDate=contractEndDate;
			this.hourlyWage=hourlyWage;
			this.hourlyWageCurrency=hourlyWageType;
			this.shiftType=shiftType;
			this.remark=remark;
			this.education=education;
			
		}
		
		
		
//		  public void addShiftDayOfHelper(HelperShiftDays shiftDays) {
//			  addShiftDayOfHelper(shiftDays,true);
//		}
//		  
//		  void addShiftDayOfHelper(HelperShiftDays shiftDays,boolean stat) {
//			  if (shiftDays != null) {
//		            if(getHelper_shift_days().contains(shiftDays)) {
//		            	getHelper_shift_days().set(getHelper_shift_days().indexOf(shiftDays), shiftDays);
//		            }
//		            else {
//		            	getHelper_shift_days().add(shiftDays);
//		            }
//		            if (stat) {
//		            	shiftDays.addHelper(this, false);
//		            }
//		        }
//		  }
		 			
			public LoginUser getAcc() {
				return acc;
			}
			public void setAcc(LoginUser acc) {
				this.acc = acc;
			}
		  
		  
	public Date getBirthDate() {
				return birthDate;
			}
			public void setBirthDate(Date date) {
				this.birthDate = date;
			}
	public String getHourlyWageCurrency() {
			return hourlyWageCurrency;
		}
		public void setHourlyWageCurrency(String hourlyWageCurrency) {
			this.hourlyWageCurrency = hourlyWageCurrency;
		}
	public Long getHelperId() {
		return helperId;
	}
	public void setHelperId(Long helperId) {
		this.helperId = helperId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	
	
	public String getFile1() {
		return file1;
	}
	public void setFile1(String file1) {
		this.file1 = file1;
	}
	public String getFile2() {
		return file2;
	}
	public void setFile2(String file2) {
		this.file2 = file2;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getBuildingNo() {
		return buildingNo;
	}
	public void setBuildingNo(String buildingNo) {
		this.buildingNo = buildingNo;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Date getHireDate() {
		return hireDate;
	}
	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}
	public Date getContractStartDate() {
		return contractStartDate;
	}
	public void setContractStartDate(Date contractStartDate) {
		this.contractStartDate = contractStartDate;
	}
	public Date getContractEndDate() {
		return contractEndDate;
	}
	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}
	public String getHourlyWage() {
		return hourlyWage;
	}
	public void setHourlyWage(String hourlyWage) {
		this.hourlyWage = hourlyWage;
	}
	public String getShiftType() {
		return shiftType;
	}
	public void setShiftType(String shiftType) {
		this.shiftType = shiftType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	  public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public List<HelperShiftDays> getHelper_shift_days() {
		  return helper_shift_days; 
		  } 
	  public void setHelper_shift_days(List<HelperShiftDays>helper_shift_days) { 
		  this.helper_shift_days = helper_shift_days; 
		  }
	public List<TaskInfo> getTaskInfo() {
		return taskInfo;
	}
	public void setTaskInfo(List<TaskInfo> taskInfo) {
		this.taskInfo = taskInfo;
	}
	public List<String> getCheckedShiftDays() {
		return checkedShiftDays;
	}
	public void setCheckedShiftDays(List<String> checkedShiftDays) {
		this.checkedShiftDays = checkedShiftDays;
	}
	 public String getShiftStartTime() {
		return shiftStartTime;
	}
	public void setShiftStartTime(String shiftStartTime) {
		this.shiftStartTime = shiftStartTime;
	}
	public String getShiftEndTime() {
		return shiftEndTime;
	}
	public void setShiftEndTime(String shiftEndTime) {
		this.shiftEndTime = shiftEndTime;
	}
	
}
