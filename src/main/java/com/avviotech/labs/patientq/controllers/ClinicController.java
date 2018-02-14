package com.avviotech.labs.patientq.controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.avviotech.labs.patientq.dto.Clinic;
import com.avviotech.labs.patientq.repository.ClinicRepository;
import com.avviotech.labs.patientq.service.CounterService;

@Controller
public class ClinicController {
	
	@Autowired
	private ClinicRepository clinicReporsitory;
	
	@Autowired
	private CounterService counterService;
	
	@RequestMapping("/clinic")
	public String home(ModelMap model) {
		addModelData(model);
		model.addAttribute("pageName","clinic");
		return "layout";
	}
	
	@RequestMapping("/addClinic")
	public String addClinic(@Valid @ModelAttribute("clinic")Clinic clinic,BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			System.out.println(result.hasErrors());
            return "error";
        }
		
		if(clinic.getId() == null)
			clinic.setId(counterService.getNext());
		if(!clinic.getName().isEmpty())
			clinicReporsitory.save(clinic);
		return "redirect:/clinic";
	}
	
	@RequestMapping("/editClinic")
	public String editClinic(@Valid @ModelAttribute("clinic")Clinic clinic,BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			System.out.println(result.hasErrors());
            return "error";
        }
		if(clinic != null)
			clinic = clinicReporsitory.findByid(clinic.getId().longValue());
		
		model.addAttribute("clinic", clinic);
		addModelData(model);
		model.addAttribute("pageName","clinic");
		return "layout";
	}
	
	
	public void addModelData(ModelMap model)
	{
		Collection<Clinic> clinics = clinicReporsitory.findAll();
		model.addAttribute("clinics",clinics);
	}
	
	
}
