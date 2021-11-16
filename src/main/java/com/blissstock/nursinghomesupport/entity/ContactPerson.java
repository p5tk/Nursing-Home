package com.blissstock.nursinghomesupport.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.blissstock.nursinghomesupport.controller.ElderController;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="contact_person")
public class ContactPerson {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="contact_person_id")
	private Long contactPersonId;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="relationship")
	private String relationship;
	
	@Column(name="country_code")
	private String countryCode;
	
	@Column(name="phone_no")
	private String phoneNo;
	
	@Column(name="building_or_room_no")
	private String buildingOrRoomNo;
	
	@Column(name="street")
	private String street;
	
	@Column(name="city")
	private String city;
	
	@Column(name="state")
	private String state;
	
	@Column(name="postal_code")
	private String postalCode;
	
	@Column(name="country")
	private String country;
	
	public ContactPerson() {}
	public ContactPerson(String fname,String lname,String relationship,String countryCode,String PhNo,String buildingNo,String street,String city,String state,String postalCode,String country) {
		this.firstName=fname;
		this.lastName=lname;
		this.relationship=relationship;
		this.countryCode=countryCode;
		this.phoneNo=PhNo;
		this.buildingOrRoomNo=buildingNo;
		this.street=street;
		this.city=city;
		this.state=state;
		this.postalCode=postalCode;
		this.country=country;
	}
	
	@ManyToMany
	@JoinTable(
			  name = "elder_contact_person", 
			  joinColumns = @JoinColumn(name = "contact_person_id"), 
			  inverseJoinColumns = @JoinColumn(name = "elder_id"))
	@JsonIgnore
	private List<ElderInfo> elders=new ArrayList<>();
	
	public void addElder(ElderInfo elder) {
		this.elders.add(elder);
	}

	public Long getContactPersonId() {
		return contactPersonId;
	}
	public void setContactPersonId(Long contactPersonId) {
		this.contactPersonId = contactPersonId;
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
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
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
	public List<ElderInfo> getElders() {
		return elders;
	}
	public void setElders(List<ElderInfo> elders) {
		this.elders = elders;
	}

}
