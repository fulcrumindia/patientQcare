package com.avviotech.labs.patientq.controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.avviotech.labs.patientq.dto.Symtoms;
import com.avviotech.labs.patientq.repository.SymtompsRepository;
import com.avviotech.labs.patientq.service.CounterService;

@Controller
public class SymtompController {
	
	@Autowired
	private SymtompsRepository sReporsitory;
	
	@Autowired
	private CounterService counterService;
	
	@RequestMapping("/symtomps")
	public String home(ModelMap model) {
		addModelData(model);
		model.addAttribute("pageName","symtomp");
		return "layout";
	}
	
	@RequestMapping("/addSymtomp")
	public String addSymtomp(@Valid @ModelAttribute("symtomp")Symtoms symtomp,BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			System.out.println(result.hasErrors());
            return "error";
        }
		
		if(symtomp.getId() == null)
			symtomp.setId(counterService.getNext());
		if(!symtomp.getName().isEmpty())
			sReporsitory.save(symtomp);
		return "redirect:/symtomps";
	}
	
	@RequestMapping("/editSymtomp")
	public String editSymtomp(@Valid @ModelAttribute("symtomp")Symtoms symtomp,BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			System.out.println(result.hasErrors());
            return "error";
        }
		if(symtomp != null)
			symtomp = sReporsitory.findByid(symtomp.getId().longValue());
		
		model.addAttribute("symtomp", symtomp);
		addModelData(model);
		model.addAttribute("pageName","symtomp");
		return "layout";
	}
	
	
	public void addModelData(ModelMap model)
	{
		Collection<Symtoms> symtomps = sReporsitory.findAll();
		model.addAttribute("symtomps",symtomps);
	}
	
	
}
