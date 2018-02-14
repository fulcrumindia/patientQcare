package com.avviotech.labs.patientq.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Pateint {

	@Id
	private Long id;
	
	private String firstname;
	private String lastname;
	private String address;
	private String city;
	private String zipcode;
	
	private String dob;
	private String gender;
	private String email;
	private String mobile;
	
	private String parentname;
	private String parentcontactnumber;
	private String emeregencycontact;
	private String emeregencycontactnumber;
	
	private String allergies;
	private String pcp;
	private boolean ispcp;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getParentname() {
		return parentname;
	}
	public void setParentname(String parentname) {
		this.parentname = parentname;
	}
	public String getParentcontactnumber() {
		return parentcontactnumber;
	}
	public void setParentcontactnumber(String parentcontactnumber) {
		this.parentcontactnumber = parentcontactnumber;
	}
	public String getEmeregencycontact() {
		return emeregencycontact;
	}
	public void setEmeregencycontact(String emeregencycontact) {
		this.emeregencycontact = emeregencycontact;
	}
	public String getEmeregencycontactnumber() {
		return emeregencycontactnumber;
	}
	public void setEmeregencycontactnumber(String emeregencycontactnumber) {
		this.emeregencycontactnumber = emeregencycontactnumber;
	}
	public String getPcp() {
		return pcp;
	}
	public void setPcp(String pcp) {
		this.pcp = pcp;
	}
	public boolean isIspcp() {
		return ispcp;
	}
	public void setIspcp(boolean ispcp) {
		this.ispcp = ispcp;
	}
	public String getAllergies() {
		return allergies;
	}
	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}
	
	
	
}
