package com.avviotech.labs.patientq.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.avviotech.labs.patientq.dto.User;
import com.avviotech.labs.patientq.repository.UserRepository;

@Controller
public class LoginController {

	@Autowired
	private UserRepository userReporsitory;
	
	@RequestMapping("/loginAction")
	public String login() {
		return "login";
	}

	@RequestMapping("/registerUser")
	public String register() {
		return "registeruser";
	}

	@RequestMapping("/submitUser")
	public String registerUser(@Valid @ModelAttribute("user") User user,
			BindingResult result, ModelMap model) {

		if (result.hasErrors()) {
			System.out.println(result.hasErrors());
            return "error";
        }
		if (!user.getFirstname().isEmpty()) {
			user.setUserName(user.getEmailAddress());
			userReporsitory.save(user);
			
		}
		return "login";
	}

}
