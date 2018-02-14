package com.avviotech.labs.patientq.model;

import java.util.ArrayList;
import java.util.Collection;

import com.avviotech.labs.patientq.dto.QueueData;

public class QueueFormData {

	private Collection<QueueData> nqueuelist;
	private Collection<QueueData> tqueuelist;
	private Collection<QueueData> cqueuelist;
	
	public QueueFormData()
	{
		nqueuelist = new ArrayList<QueueData>();
		tqueuelist = new ArrayList<QueueData>();
		cqueuelist = new ArrayList<QueueData>();
	}
	
	public Collection<QueueData> getNqueuelist() {
		return nqueuelist;
	}
	public void setNqueuelist(Collection<QueueData> nqueuelist) {
		this.nqueuelist = nqueuelist;
	}
	public Collection<QueueData> getTqueuelist() {
		return tqueuelist;
	}
	public void setTqueuelist(Collection<QueueData> tqueuelist) {
		this.tqueuelist = tqueuelist;
	}

	public Collection<QueueData> getCqueuelist() {
		return cqueuelist;
	}

	public void setCqueuelist(Collection<QueueData> cqueuelist) {
		this.cqueuelist = cqueuelist;
	}
	
	
	
	
}
