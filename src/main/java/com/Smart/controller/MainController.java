package com.Smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.Smart.dao.UserRepository;
import com.Smart.entites.User;
import com.Smart.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;




@Controller
public class MainController {
	@Autowired
	private BCryptPasswordEncoder bcpe;
	
	@Autowired
	private UserRepository userrep;
	
	
	//this is handler od home page
	
	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("title","home-Smart Content Manager");
		return"home";
	}
	
	//this is handler of about page
	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("title","about-Smart Content Manager");
		model.addAttribute("user",new User());
		return"about";
	}
	
    //this is handler of sign page
	@RequestMapping("/sign")
	public String Signup(Model model) {
		model.addAttribute("title","Rigester-Smart Content Manager");
		model.addAttribute("user",new User());
		return"sign";
	}
	
	//this is handler of register user
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public String RegisterUser(@Valid @ModelAttribute("user") User user,
			BindingResult result1,
			@RequestParam(value="agreement",defaultValue = "false")
	          boolean agreement,Model model, HttpSession session ) {
		try {
		
		if(!agreement) {
			System.out.println("You have not agree term and condition");
			throw new Exception("You have not agree term and condition");
		}
		if(result1.hasErrors()) {
			
			System.out.println("error"+result1.toString());
			model.addAttribute("user",user);
            return"sign";
		}
		
		user.setRole("ROLE_USER");
		user.setEnabled(true);
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		
		User result = this.userrep.save(user);
		
		model.addAttribute("user",new User());
		session.setAttribute("message", new Message("Successfully register!!","success"));
		//session.removeAttribute("message");
		return"sign";
	
		}
		catch( Exception e) {
			e.printStackTrace();
			model.addAttribute("user",user);
			session.setAttribute("message", new Message("Something went wrong"+e.getMessage(),"alert-danger"));
			return"sign";
		}
		
	}
	
	//this is handal login page
	@GetMapping("/signin")
	public String loginPage() {
		return"login";
	}

}
