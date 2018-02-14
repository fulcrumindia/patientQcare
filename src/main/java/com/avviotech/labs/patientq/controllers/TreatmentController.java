package com.avviotech.labs.patientq.controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.avviotech.labs.patientq.dto.Treatments;
import com.avviotech.labs.patientq.repository.TreatmentRepository;
import com.avviotech.labs.patientq.service.CounterService;

@Controller
public class TreatmentController {
	
	@Autowired
	private TreatmentRepository tReporsitory;
	
	@Autowired
	private CounterService counterService;
	
	@RequestMapping("/treatments")
	public String home(ModelMap model) {
		addModelData(model);
		model.addAttribute("pageName","treatment");
		return "layout";
	}
	
	@RequestMapping("/addTreatment")
	public String addTreatment(@Valid @ModelAttribute("treatment")Treatments treatment,BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			System.out.println(result.hasErrors());
            return "error";
        }
		
		if(treatment.getId() == null)
			treatment.setId(counterService.getNext());
		if(!treatment.getName().isEmpty())
			tReporsitory.save(treatment);
		return "redirect:/treatments";
	}
	
	@RequestMapping("/editTreatment")
	public String editTreatment(@Valid @ModelAttribute("treatment")Treatments treatment,BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			System.out.println(result.hasErrors());
            return "error";
        }
		if(treatment != null)
			treatment = tReporsitory.findByid(treatment.getId());
		
		model.addAttribute("treatment", treatment);
		addModelData(model);
		model.addAttribute("pageName","treatment");
		return "layout";
	}
	
	
	public void addModelData(ModelMap model)
	{
		Collection<Treatments> treatments = tReporsitory.findAll();
		model.addAttribute("treatments",treatments);
	}
	
	
}
