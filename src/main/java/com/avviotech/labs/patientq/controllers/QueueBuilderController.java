package com.avviotech.labs.patientq.controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.avviotech.labs.patientq.dto.Counter;
import com.avviotech.labs.patientq.dto.Queue;
import com.avviotech.labs.patientq.repository.QueueRepository;
import com.avviotech.labs.patientq.service.CounterService;

@Controller
public class QueueBuilderController {
	
	@Autowired
	private QueueRepository queueReporsitory;
	
	@Autowired
	private CounterService counterService;
	
	@RequestMapping("/queuebuilder")
	public String home(ModelMap model) {
		addModelData(model);
		model.addAttribute("pageName","queuebuilder");
		return "layout";
	}
	
	@RequestMapping("/addQueue")
	public String addQueue(@Valid @ModelAttribute("queue")Queue queue,BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			System.out.println(result.hasErrors());
            return "error";
        }
		if(queue.getId() == null)
			queue.setId(counterService.getNext());
		if(!queue.getName().isEmpty())
			queueReporsitory.save(queue);
		return "redirect:/queuebuilder";
	}
	
	@RequestMapping("/editQueue")
	public String editQueue(@Valid @ModelAttribute("queue")Queue queue,BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			System.out.println(result.hasErrors());
            return "error";
        }
		if(queue != null)
			queue = queueReporsitory.findByid(queue.getId().longValue());
		
		model.addAttribute("queue", queue);
		
		addModelData(model);
		model.addAttribute("pageName","queuebuilder");
		return "layout";
	}
	
	
	public void addModelData(ModelMap model)
	{
		Collection<Queue> queues = queueReporsitory.findAll();
		model.addAttribute("queues",queues);
	}
	
	
}
