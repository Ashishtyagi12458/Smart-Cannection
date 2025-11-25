package com.Smart.controller;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Smart.dao.UserRepository;
import com.Smart.dao.contactRepository;
import com.Smart.entites.Contact;
import com.Smart.entites.User;
import com.Smart.helper.Message;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository userrepository;
	@Autowired
	private contactRepository contactrepository;
	
	@ModelAttribute
	public void AddCommanData(Model model, Principal principal){
		 String username = principal.getName();
		    System.out.println("Logged in user: " + username);

		    User user = userrepository.getUserDetails(username);
		    System.out.println("DB user: " + user);

		    org.springframework.security.core.Authentication auth =
		            (org.springframework.security.core.Authentication) principal;
		    System.out.println("Authorities from Spring Security: " + auth.getAuthorities());

		    model.addAttribute("user", user);
		
	}
	
	@GetMapping("/index")
	public String deshboard(Model model, Principal principal) {
	   
		model.addAttribute("title","User Deshboard");
	    return "normal/deshboard";
	}
	
	// open add content form
	@GetMapping("/add-contact")
	public String AddContactForm(Model model) {
		
		model.addAttribute("title","Add Contact");
		model.addAttribute("contact", new Contact());
		return"normal/add_contact";
	}
	
	//processing add cantact form
	@PostMapping("/process-contact")
	public String processContact(@ModelAttribute Contact contact,
			@RequestParam("profileImage") MultipartFile file ,
			Principal principal,HttpSession session) {
		try {
		String name = principal.getName();
		User user = this.userrepository.getUserDetails(name);
		
		if(file.isEmpty()) {
			System.out.println("file is empity");
			contact.setCimage("contact.svg");
		}else {
			contact.setCimage(file.getOriginalFilename());
			File savefile = new ClassPathResource("static/image").getFile();
			Path path = Paths.get(savefile.getAbsolutePath()+file.getOriginalFilename());
			
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		}
		
		contact.setUser(user);
		user.getContect().add(contact);
		this.userrepository.save(user);
		System.out.println("DATA"+contact);
		System.out.println("add to data base");
		
		session.setAttribute("message",new Message("your contect add succefully","success"));

		}catch(Exception e) {
			System.out.println("ERROR"+e.getMessage());
			e.printStackTrace();
			session.setAttribute("message",new Message("your contect add succefully","danger"));

		}
		return"normal/add_contact";
	}

	@GetMapping("/show_contact/{page}")
	public String showContact(@PathVariable("page") Integer page, Model m, Principal principal) {
		m.addAttribute("title", "show Contact view");
		//contact ko contact veiw page pa dikana ha
		//String userName = principal.getName();
		//User userDetails = this.userrepository.getUserDetails(userName);
		//List<Contact> contect = userDetails.getContect();
		
		String username = principal.getName();
		User user = this.userrepository.getUserDetails(username);
		
		Pageable pegable = PageRequest.of(page, 5);
		Page<Contact> contect = this.contactrepository.findContectByName(user.getId(),pegable);
		
		m.addAttribute("contact", contect);
		m.addAttribute("currentPage", page);
		m.addAttribute("totalpage", contect.getTotalPages());
		return"normal/show_contact";
	}
	
	// Showing Particuler contact details
	@RequestMapping("/{cid}/contact")
	public String ShowDetails(@PathVariable("cid") Integer cid, Model model,Principal principal) {
		
		Optional<Contact> ct = this.contactrepository.findById(cid);
		Contact contact = ct.get();
		
		String name = principal.getName();
		User user = this.userrepository.getUserDetails(name);
		
		if(user.getId()==contact.getCid()) 
		{
			model.addAttribute("contact", contact);
		    model.addAttribute("title", contact.getCname());
		}
		
		return"normal/contact_detail";
	}

}
