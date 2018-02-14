package com.avviotech.labs.patientq.controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.avviotech.labs.patientq.dto.Provider;
import com.avviotech.labs.patientq.repository.ProviderRepository;
import com.avviotech.labs.patientq.service.CounterService;

@Controller
public class ProviderController {
	
	@Autowired
	private ProviderRepository providerReporsitory;
	
	@Autowired
	private CounterService counterService;
	
	@RequestMapping("/providers")
	public String home(ModelMap model) {
		addModelData(model);
		model.addAttribute("pageName","provider");
		return "layout";
	}
	
	@RequestMapping("/addProviders")
	public String addProviders(ModelMap model) {
		addModelData(model);
		model.addAttribute("pageName","provider");
		return "layout";
	}
	
	
	@RequestMapping("/editProviders")
	public String editProviders(ModelMap model) {
		addModelData(model);
		model.addAttribute("pageName","provider");
		return "layout";
	}
	
	@RequestMapping("/addProvider")
	public String addProvider(@Valid @ModelAttribute("provider")Provider provider,BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			System.out.println(result.hasErrors());
            return "error";
        }
		
		if(provider.getId() == null)
			provider.setId(counterService.getNext());
		if(!provider.getFirstname().isEmpty())
			providerReporsitory.save(provider);
		return "redirect:/providers";
	}
	
	@RequestMapping("/editProvider")
	public String editProvider(@Valid @ModelAttribute("provider")Provider provider,BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			System.out.println(result.hasErrors());
            return "error";
        }
		if(provider != null)
			provider = providerReporsitory.findByid(provider.getId().longValue());
		
		model.addAttribute("provider", provider);
		addModelData(model);
		model.addAttribute("pageName","provider");
		return "layout";
	}
	
	
	public void addModelData(ModelMap model)
	{
		Collection<Provider> providers = providerReporsitory.findAll();
		model.addAttribute("providers",providers);
	}
	
	
}
