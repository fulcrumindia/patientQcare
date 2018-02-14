package com.avviotech.labs.patientq.controllers;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.avviotech.labs.patientq.dto.Clinic;
import com.avviotech.labs.patientq.dto.Pateint;
import com.avviotech.labs.patientq.dto.PatientReservationForm;
import com.avviotech.labs.patientq.dto.Queue;
import com.avviotech.labs.patientq.dto.QueueData;
import com.avviotech.labs.patientq.dto.Symtoms;
import com.avviotech.labs.patientq.repository.ClinicRepository;
import com.avviotech.labs.patientq.repository.PateintReservationRepository;
import com.avviotech.labs.patientq.repository.PateintsRepository;
import com.avviotech.labs.patientq.repository.QueueDataRepository;
import com.avviotech.labs.patientq.repository.QueueRepository;
import com.avviotech.labs.patientq.repository.SymtompsRepository;
import com.avviotech.labs.patientq.service.CounterService;

@Controller
public class PateintsReservationController {
	
	@Autowired
	private PateintsRepository patientReporsitory;
	
	@Autowired
	private PateintReservationRepository patientFormReporsitory;
	
	@Autowired
	private QueueDataRepository queuedataReporsitory;
	
	@Autowired
	private SymtompsRepository symptomsReporsitory;
	
	@Autowired
	private ClinicRepository clinicReporsitory;
	
	@Autowired
	private QueueRepository queueReporsitory;
	
	@Autowired
	private CounterService counterService;
	
	@RequestMapping("/patient-reservation")
	public String home(ModelMap model) {
		addModelData(model);
		return "pateintreservation";
	}
	
	@RequestMapping("/ertabs")
	public String reservationconfirmation(ModelMap model) {
		
		return "ertabs";
	}
	
	@RequestMapping("/ertabs-search")
	public String ertabsSearch(ModelMap model,@RequestParam("search") String address) {
		addModelData(model);
		return "ertabs";
	}
	
	@RequestMapping("/ertabs-click")
	public String ertabsClick(ModelMap model,@RequestParam("time") String time) {
		addModelData(model);
		PatientReservationForm form = new PatientReservationForm();
		
		if(!time.isEmpty())
		{
			if(time.indexOf(":") != -1)
			{
				String hour = time.substring(0,time.indexOf(":"));
				String min = time.substring(time.indexOf(":")+1);
				
				Calendar c = Calendar.getInstance();
				c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
				c.set(Calendar.MINUTE, Integer.parseInt(min));
				
				form.setTime(c.getTime());
				model.addAttribute("patientreservation",form);
				
			}
		}
		return "pateintreservation";
	}
	
	@RequestMapping("/book-appointment")
	public String addPatient(@Valid @ModelAttribute("patientreservation")PatientReservationForm patientreservation,BindingResult result, ModelMap model
			, HttpServletRequest request) {
		if (result.hasErrors()) {
			System.out.println(result.hasErrors());
            return "error";
        }
		patientreservation.setId(counterService.getNext());
		patientreservation.setIpaddress(request.getRemoteAddr());
		patientreservation.setTime(new Date());
		
		QueueData queue = new QueueData();
		queue.setId(counterService.getNext());
		Pateint pateint = new Pateint();
		pateint.setId(counterService.getNext());
		pateint.setAddress(patientreservation.getAddress());
		pateint.setCity(patientreservation.getCity());
		pateint.setDob(patientreservation.getDob());
		pateint.setEmail(patientreservation.getEmail());
		pateint.setEmeregencycontact(patientreservation.getEmergecycontact());
		pateint.setEmeregencycontactnumber(patientreservation.getEmergecynumber());
		pateint.setFirstname(patientreservation.getFirstname());
		pateint.setGender(patientreservation.getGender());
		pateint.setIspcp(patientreservation.isIspcp());
		pateint.setLastname(patientreservation.getLastname());
		pateint.setMobile(patientreservation.getMobile());
		pateint.setParentcontactnumber(patientreservation.getParentcontact());
		pateint.setParentname(patientreservation.getParentname());
		pateint.setPcp(patientreservation.getPcp());
		pateint.setZipcode(patientreservation.getZip());
		pateint.setAllergies(patientreservation.getAllergies());
		patientReporsitory.save(pateint);
		
		queue.setAssignedwaittime("30");
		queue.setStatus("YetToVisit");
		queue.setTraige("LevelV");
		queue.setPateintid(pateint.getId());
		queue.setPateintname(pateint.getFirstname() + " " + pateint.getLastname());
		queue.setPatient(pateint);
		queue.setQueueid(getQueueId("Normal"));
		queue.setSymptom(patientreservation.getReason() + ", " + patientreservation.getSymptoms());
		queue.setVisittime(patientreservation.getTime());
		patientreservation.setReference(String.valueOf(new Date().getTime()));
		queue.setUniquereference(patientreservation.getReference());
		queuedataReporsitory.save(queue);
		
		
		patientFormReporsitory.save(patientreservation);
		
		Clinic clinic = clinicReporsitory.findAll().get(0);
		model.addAttribute("clinic",clinic);
		
		
		model.addAttribute("patientreservation",patientreservation);
		model.addAttribute("clinic",clinic);
		return "reservationconfirmation";
	}
	
	
	
	
	public void addModelData(ModelMap model)
	{
		Collection<Pateint> patients = patientReporsitory.findAll();
		model.addAttribute("patients",patients);
		
		Collection<Clinic> clinics = clinicReporsitory.findAll();
		model.addAttribute("clinics",clinics);
		
		Collection<Symtoms> symptoms = symptomsReporsitory.findAll();
		model.addAttribute("symptoms",symptoms);
		
	}
	
	public Long getQueueId(String type) {
		Queue q = queueReporsitory.findBytype(type);
		return q.getId();
	}
	
	
}
