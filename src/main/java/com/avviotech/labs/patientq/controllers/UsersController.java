package com.avviotech.labs.patientq.controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.avviotech.labs.patientq.dto.User;
import com.avviotech.labs.patientq.repository.UserRepository;

@Controller
public class UsersController {
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/users")
	public String home(ModelMap model) {
		model.addAttribute("pageName","users");
		addModelData(model);
		return "layout";
	}
	
	@RequestMapping(value = "/addUser", method=RequestMethod.POST)
	public String addUser(@Valid @ModelAttribute("user")User user,BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			System.out.println(result.hasErrors());
            return "error";
        }
		
		if(!user.getUserName().isEmpty())
			userRepository.save(user);
		
		addModelData(model);
		return "redirect:/users";
	}
	
	@RequestMapping(value = "/editUser", method=RequestMethod.POST)
	public String editUser(@Valid @ModelAttribute("user")User user,BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			System.out.println(result.hasErrors());
            return "error";
        }
		if(user != null)
			user = userRepository.findByid(user.getId());
		
		model.addAttribute("user", user);
		addModelData(model);
		model.addAttribute("pageName","users");
		return "layout";
	}
	
	
	public void addModelData(ModelMap model)
	{
		Collection<User> users = userRepository.findAll();
		model.addAttribute("users",users);
	}
	
}
