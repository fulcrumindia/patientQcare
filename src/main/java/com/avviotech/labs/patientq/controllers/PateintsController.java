package com.avviotech.labs.patientq.controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.avviotech.labs.patientq.dto.Pateint;
import com.avviotech.labs.patientq.repository.PateintsRepository;
import com.avviotech.labs.patientq.service.CounterService;

@Controller
public class PateintsController {
	
	@Autowired
	private PateintsRepository patientReporsitory;
	
	@Autowired
	private CounterService counterService;
	
	@RequestMapping("/patients")
	public String home(ModelMap model) {
		addModelData(model);
		model.addAttribute("pageName","patient");
		return "layout";
	}
	
	@RequestMapping("/addPatients")
	public String addPatients(ModelMap model) {
		addModelData(model);
		model.addAttribute("pageName","patient");
		return "layout";
	}
	
	
	@RequestMapping("/editPatients")
	public String editPatients(ModelMap model) {
		addModelData(model);
		model.addAttribute("pageName","patient");
		return "layout";
	}
	
	@RequestMapping("/addPatient")
	public String addPatient(@Valid @ModelAttribute("patient")Pateint patient,BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			System.out.println(result.hasErrors());
            return "error";
        }
		
		if(patient.getId() == null)
			patient.setId(counterService.getNext());
		if(!patient.getFirstname().isEmpty())
			patientReporsitory.save(patient);
		return "redirect:/patients";
	}
	
	@RequestMapping("/editPatient")
	public String editPatient(@Valid @ModelAttribute("patient")Pateint patient,BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			System.out.println(result.hasErrors());
            return "error";
        }
		if(patient != null)
			patient = patientReporsitory.findByid(patient.getId().longValue());
		
		model.addAttribute("patient", patient);
		addModelData(model);
		model.addAttribute("pageName","patient");
		return "layout";
	}
	
	
	public void addModelData(ModelMap model)
	{
		Collection<Pateint> patients = patientReporsitory.findAll();
		model.addAttribute("patients",patients);
	}
	
	
}
