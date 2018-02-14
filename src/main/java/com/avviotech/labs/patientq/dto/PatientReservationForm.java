package com.avviotech.labs.patientq.dto;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document
public class PatientReservationForm {

	@Id
	private Long id;
	
	@DateTimeFormat(iso=ISO.DATE_TIME)
	private Date time;
	
	private String symptoms;
	private String firstname;
	private String lastname;
	private String address;
	private String city;
	private String zip;
	private String reason;
	private String latitude;
	private String longitude;
	private String reference;
	
	private String dob;
	private String gender;
	private String text;
	private String mobile;
	private String email;
	private String isvistedbefore;
	private String parentname;
	private String parentcontact;
	private String emergecycontact;
	private String emergecynumber;
	private boolean ispcp;
	private String pcp;
	private String isallergies;
	private String allergies;
	private String paymentmode;
	private String ipaddress;
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getSymptoms() {
		return symptoms;
	}
	public void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
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
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
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
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIsvistedbefore() {
		return isvistedbefore;
	}
	public void setIsvistedbefore(String isvistedbefore) {
		this.isvistedbefore = isvistedbefore;
	}
	public String getParentname() {
		return parentname;
	}
	public void setParentname(String parentname) {
		this.parentname = parentname;
	}
	public String getEmergecycontact() {
		return emergecycontact;
	}
	public void setEmergecycontact(String emergecycontact) {
		this.emergecycontact = emergecycontact;
	}
	public String getEmergecynumber() {
		return emergecynumber;
	}
	public void setEmergecynumber(String emergecynumber) {
		this.emergecynumber = emergecynumber;
	}
	
	public String getPcp() {
		return pcp;
	}
	public void setPcp(String pcp) {
		this.pcp = pcp;
	}
	public String getIsallergies() {
		return isallergies;
	}
	public void setIsallergies(String isallergies) {
		this.isallergies = isallergies;
	}
	public String getAllergies() {
		return allergies;
	}
	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}
	public String getPaymentmode() {
		return paymentmode;
	}
	public void setPaymentmode(String paymentmode) {
		this.paymentmode = paymentmode;
	}
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getParentcontact() {
		return parentcontact;
	}
	public void setParentcontact(String parentcontact) {
		this.parentcontact = parentcontact;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public boolean isIspcp() {
		return ispcp;
	}
	public void setIspcp(boolean ispcp) {
		this.ispcp = ispcp;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	
	
	
	
}
