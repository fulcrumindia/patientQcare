package com.avviotech.labs.patientq.model;

import com.avviotech.labs.patientq.dto.QueueData;

public class UrgentVisitModel {

	private String date;
	private int count;
	private int ercount;
	private int nonercount;
	private int waittime;
	private int erwaittime;
	private int nonerwaittime;
	private QueueData qdata;
	
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getErcount() {
		return ercount;
	}

	public void setErcount(int ercount) {
		this.ercount = ercount;
	}

	public int getNonercount() {
		return nonercount;
	}

	public void setNonercount(int nonercount) {
		this.nonercount = nonercount;
	}

	public int getWaittime() {
		return waittime;
	}

	public void setWaittime(int waittime) {
		this.waittime = waittime;
	}

	public int getErwaittime() {
		return erwaittime;
	}

	public void setErwaittime(int erwaittime) {
		this.erwaittime = erwaittime;
	}

	public int getNonerwaittime() {
		return nonerwaittime;
	}

	public void setNonerwaittime(int nonerwaittime) {
		this.nonerwaittime = nonerwaittime;
	}

	public QueueData getQdata() {
		return qdata;
	}

	public void setQdata(QueueData qdata) {
		this.qdata = qdata;
	}

	

	
}
