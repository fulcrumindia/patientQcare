package com.avviotech.labs.patientq.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.avviotech.labs.patientq.dto.Queue;
import com.avviotech.labs.patientq.dto.QueueData;
import com.avviotech.labs.patientq.model.QueueFormData;
import com.avviotech.labs.patientq.repository.QueueDataRepository;
import com.avviotech.labs.patientq.repository.QueueRepository;

@Controller
public class PatientRecordsController {

	@Autowired
	private QueueDataRepository queueDataRepository;
	
	@Autowired
	private QueueRepository queueRepository;

	
	@RequestMapping("/patientrecords")
	public String home(ModelMap model) {
		addModelData(model);
		model.addAttribute("pageName", "patientrecords");
		return "layout";
	}

	
	public void addModelData(ModelMap model) {
		Collection<QueueData> completedqueuedatas = queueDataRepository
				.findByqueueid(getQueueId("Completed"));
		QueueFormData qform = new QueueFormData();

		qform.setCqueuelist(completedqueuedatas);

		model.addAttribute("qform", qform);

	}

	public Long getQueueId(String type) {
		Queue q = queueRepository.findBytype(type);
		return q.getId();
	}

}
