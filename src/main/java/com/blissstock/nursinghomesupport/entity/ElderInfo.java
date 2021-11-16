package com.blissstock.nursinghomesupport.entity;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="elder_info")
public class ElderInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="elder_id")
	private Long elderId;
	
	@NotBlank(message="Please enter first name.")
	@Column(name="first_name")
	private String firstName;
	
	@NotBlank(message="Please enter last name.")
	@Column(name="last_name")
	private String lastName;
	
	@NotBlank(message="Please choose gender.")
	@Column(name="sex")
	private String sex;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Past
    @Column(name="birthday")
	private Date Birthday;
	
	
	@Column(name="country_code")
	private String countryCode;
	
	@NotBlank(message="Please enter phone number.")
    @Size(max = 10, min = 10, message = "Phone number should be of 10 digits")
	@Column(name="phone_no")
	private String phoneNo;
	
	@NotBlank(message="Please enter building or room number.")
	@Column(name="building_or_room_no")
	private String buildingOrRoomNo;
	
	@NotBlank(message="Please enter street address.")
	@Column(name="street")
	private String street;
	
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
	@Column(name="admission_date")
	private Date admissionDate;
	
	@Column(name="room_no")
	private String roomNo;
	
	@Column(name="photo")
	private String photo;
	
	@Column(name="file1")
	private String file1;
	
	@Column(name="file2")
	private String file2;
	
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
	@Column(name="dislike_meal")
	private String dislikeMeal;
	
	@Column(name="favourite_meal")
	private String favouriteMeal;
	
	@Column(name="illness")
	private String illness;
	
	@Column(name="medication_info")
	private String medicationInfo;
	
	@Column(name="remarks")
	private String remarks;

	
	@OneToMany(mappedBy="elder",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private List<ElderHobbies> elder_hobby=new ArrayList<>();
	
	@Valid
	@ManyToMany(mappedBy="elders",cascade=CascadeType.ALL)
	private List<ContactPerson> contact_person=new ArrayList<>();
	
	
	@ManyToMany(fetch=FetchType.LAZY, cascade = {CascadeType.MERGE},mappedBy = "elderInfo")
	@JsonIgnore
	private List<TaskInfo> taskInfo = new ArrayList<>();
	
	
	@OneToMany(mappedBy="elder",cascade=CascadeType.ALL)
	@JsonIgnore
	private List<DailyRecord> daily_records=new ArrayList<>();

	//constructors
	public ElderInfo() {}
	public ElderInfo(String fname,String lname,String sex,Date bd,String countryCode,
			String PhNo,String buildingNo,String street,String city,String state,
			String postalCode,String country,Date admitDate,String roomNo,
			String dislikeMeal,String favMeal,String illness,String medicine,String remarks) {
		this.firstName=fname;
		this.lastName=lname;
		this.sex=sex;
		this.Birthday=bd;
		this.countryCode=countryCode;
		this.phoneNo=PhNo;
		this.buildingOrRoomNo=buildingNo;
		this.street=street;
		this.city=city;
		this.state=state;
		this.postalCode=postalCode;
		this.country=country;
		this.admissionDate=admitDate;
		this.roomNo=roomNo;
		this.dislikeMeal=dislikeMeal;
		this.favouriteMeal=favMeal;
		this.illness=illness;
		this.medicationInfo=medicine;
		this.remarks=remarks;
	}
	
	//getters and setters	
	public void addHobbyOfElder(ElderHobbies hobby) {
		this.elder_hobby.add(hobby);
		hobby.setElder(this);
	}
	public void addContactOfElder(ContactPerson contactPerson) {
		this.contact_person.add(contactPerson);
	}
	
	public Long getElderId() {
		return elderId;
	}
	public void setElderId(Long elderId) {
		this.elderId = elderId;
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
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return Birthday;
	}
	public void setBirthday(Date birthday) {
		Birthday = birthday;
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
	public String getBuildingOrRoomNo() {
		return buildingOrRoomNo;
	}
	public void setBuildingOrRoomNo(String buildingOrRoomNo) {
		this.buildingOrRoomNo = buildingOrRoomNo;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
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
	public Date getAdmissionDate() {
		return admissionDate;
	}
	public void setAdmissionDate(Date admissionDate) {
		this.admissionDate = admissionDate;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getDislikeMeal() {
		return dislikeMeal;
	}
	public void setDislikeMeal(String dislikeMeal) {
		this.dislikeMeal = dislikeMeal;
	}
	public String getFavouriteMeal() {
		return favouriteMeal;
	}
	public void setFavouriteMeal(String favouriteMeal) {
		this.favouriteMeal = favouriteMeal;
	}
	public String getIllness() {
		return illness;
	}
	public void setIllness(String illness) {
		this.illness = illness;
	}
	public String getMedicationInfo() {
		return medicationInfo;
	}
	public void setMedicationInfo(String medicationInfo) {
		this.medicationInfo = medicationInfo;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public List<ElderHobbies> getElder_hobby() {
		return elder_hobby;
	}
	public void setElder_hobby(List<ElderHobbies> elder_hobby) {
		this.elder_hobby = elder_hobby;
	}
	public List<ContactPerson> getContact_person() {
		return contact_person;
	}
	public void setContact_person(List<ContactPerson> contact_person) {
		this.contact_person = contact_person;
	}
	public List<TaskInfo> getTaskInfo() {
		return taskInfo;
	}
	public void setTaskInfo(List<TaskInfo> taskInfo) {
		this.taskInfo = taskInfo;
	}
	public List<DailyRecord> getDaily_records() {
		return daily_records;
	}
	public void setDaily_records(List<DailyRecord> daily_records) {
		this.daily_records = daily_records;
	}
	
}

