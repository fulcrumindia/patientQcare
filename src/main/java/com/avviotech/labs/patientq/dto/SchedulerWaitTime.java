package com.avviotech.labs.patientq.dto;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document
public class SchedulerWaitTime {

	@Id
	private Long id;

	@DateTimeFormat(iso=ISO.DATE_TIME)
	private Date currentdate;
	private Long schedulerid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCurrentdate() {
		return currentdate;
	}

	public void setCurrentdate(Date currentdate) {
		this.currentdate = currentdate;
	}

	public Long getSchedulerid() {
		return schedulerid;
	}

	public void setSchedulerid(Long schedulerid) {
		this.schedulerid = schedulerid;
	}

	

}
