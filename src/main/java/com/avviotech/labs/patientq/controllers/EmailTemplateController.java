package com.avviotech.labs.patientq.controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.avviotech.labs.patientq.dto.EmailTemplate;
import com.avviotech.labs.patientq.repository.EmailRepository;
import com.avviotech.labs.patientq.service.CounterService;

@Controller
public class EmailTemplateController {
	
	@Autowired
	private EmailRepository sReporsitory;
	
	@Autowired
	private CounterService counterService;
	
	@RequestMapping("/emailtemplate")
	public String home(ModelMap model) {
		addModelData(model);
		model.addAttribute("pageName","emailtemplate");
		return "layout";
	}
	
	@RequestMapping("/addEmailTemplate")
	public String addSMSTemplate(@Valid @ModelAttribute("emailtemplate")EmailTemplate emailtemplate,BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			System.out.println(result.hasErrors());
            return "error";
        }
		
		if(emailtemplate.getId() == null)
			emailtemplate.setId(counterService.getNext());
		if(!emailtemplate.getName().isEmpty())
			sReporsitory.save(emailtemplate);
		return "redirect:/emailtemplate";
	}
	
	@RequestMapping("/editEmailTemplate")
	public String editSMSTemplate(@Valid @ModelAttribute("emailtemplate")EmailTemplate emailtemplate,BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			System.out.println(result.hasErrors());
            return "error";
        }
		if(emailtemplate != null)
			emailtemplate = sReporsitory.findByid(emailtemplate.getId().longValue());
		
		model.addAttribute("emailtemplate", emailtemplate);
		addModelData(model);
		model.addAttribute("pageName","emailtemplate");
		return "layout";
	}
	
	
	public void addModelData(ModelMap model)
	{
		Collection<EmailTemplate> emailtemplates = sReporsitory.findAll();
		model.addAttribute("emailtemplates",emailtemplates);
	}
	
	
}
