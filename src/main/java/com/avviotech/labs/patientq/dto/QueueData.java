package com.avviotech.labs.patientq.dto;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document
public class QueueData {

	
	@Id
	private Long id;
	
	private Long queueid;
	private Long pateintid;
	private Long providerid;
	
	private String symptom;
	private String traige;
	private String traigecolor;
	private String status;
	private String statuscolor;
	private String uniquereference;
	
	private String remarks;
	private String treatment;
	private String assignedwaittime;
	
	@DateTimeFormat(iso=ISO.DATE_TIME)
	private Date visittime;
	
	@DateTimeFormat(iso=ISO.DATE_TIME)
	private Date checkouttime;
	
	private String waittime;
	@DateTimeFormat(iso=ISO.DATE_TIME)
	private Date entertime;
	
	private String pateintname;
	private String referred;
	
	private Pateint patient;
	private Provider provider;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getQueueid() {
		return queueid;
	}
	public void setQueueid(Long queueid) {
		this.queueid = queueid;
	}
	public Long getPateintid() {
		return pateintid;
	}
	public void setPateintid(Long pateintid) {
		this.pateintid = pateintid;
	}
	public Long getProviderid() {
		return providerid;
	}
	public void setProviderid(Long providerid) {
		this.providerid = providerid;
	}
	public String getSymptom() {
		return symptom;
	}
	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}
	public String getTraige() {
		return traige;
	}
	public void setTraige(String traige) {
		this.traige = traige;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getVisittime() {
		return visittime;
	}
	public void setVisittime(Date visittime) {
		this.visittime = visittime;
	}
	public String getWaittime() {
		return waittime;
	}
	public void setWaittime(String waittime) {
		this.waittime = waittime;
	}
	public Date getEntertime() {
		return entertime;
	}
	public void setEntertime(Date entertime) {
		this.entertime = entertime;
	}
	public String getAssignedwaittime() {
		return assignedwaittime;
	}
	public void setAssignedwaittime(String assignedwaittime) {
		this.assignedwaittime = assignedwaittime;
	}
	public String getPateintname() {
		return pateintname;
	}
	public void setPateintname(String pateintname) {
		this.pateintname = pateintname;
	}
	public String getReferred() {
		return referred;
	}
	public void setReferred(String referred) {
		this.referred = referred;
	}
	public String getTreatment() {
		return treatment;
	}
	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
	public Pateint getPatient() {
		return patient;
	}
	public void setPatient(Pateint patient) {
		this.patient = patient;
	}
	public Provider getProvider() {
		return provider;
	}
	public void setProvider(Provider provider) {
		this.provider = provider;
	}
	public String getTraigecolor() {
		return traigecolor;
	}
	public void setTraigecolor(String traigecolor) {
		this.traigecolor = traigecolor;
	}
	public String getStatuscolor() {
		return statuscolor;
	}
	public void setStatuscolor(String statuscolor) {
		this.statuscolor = statuscolor;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getUniquereference() {
		return uniquereference;
	}
	public void setUniquereference(String uniquereference) {
		this.uniquereference = uniquereference;
	}
	public Date getCheckouttime() {
		return checkouttime;
	}
	public void setCheckouttime(Date checkouttime) {
		this.checkouttime = checkouttime;
	}
	
	
	
}
