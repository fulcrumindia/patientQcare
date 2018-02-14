package com.avviotech.labs.patientq.controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.avviotech.labs.patientq.dto.SMSTemplate;
import com.avviotech.labs.patientq.repository.SMSRepository;
import com.avviotech.labs.patientq.service.CounterService;

@Controller
public class SMSTemplateController {
	
	@Autowired
	private SMSRepository sReporsitory;
	
	@Autowired
	private CounterService counterService;
	
	@RequestMapping("/smstemplate")
	public String home(ModelMap model) {
		addModelData(model);
		model.addAttribute("pageName","smstemplate");
		return "layout";
	}
	
	@RequestMapping("/addSMSTemplate")
	public String addSMSTemplate(@Valid @ModelAttribute("smstemplate")SMSTemplate smstemplate,BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			System.out.println(result.hasErrors());
            return "error";
        }
		
		if(smstemplate.getId() == null)
			smstemplate.setId(counterService.getNext());
		if(!smstemplate.getName().isEmpty())
			sReporsitory.save(smstemplate);
		return "redirect:/smstemplate";
	}
	
	@RequestMapping("/editSMSTemplate")
	public String editSMSTemplate(@Valid @ModelAttribute("smstemplate")SMSTemplate smstemplate,BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			System.out.println(result.hasErrors());
            return "error";
        }
		if(smstemplate != null)
			smstemplate = sReporsitory.findByid(smstemplate.getId().longValue());
		
		model.addAttribute("smstemplate", smstemplate);
		addModelData(model);
		model.addAttribute("pageName","smstemplate");
		return "layout";
	}
	
	
	public void addModelData(ModelMap model)
	{
		Collection<SMSTemplate> smstemplates = sReporsitory.findAll();
		model.addAttribute("smstemplates",smstemplates);
	}
	
	
}
