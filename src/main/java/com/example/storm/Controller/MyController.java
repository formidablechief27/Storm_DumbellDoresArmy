package com.example.storm.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {
	
	@GetMapping("/index")
	public String home(Model model) {
		return "index.html";
	}
	
	@GetMapping("/start")
	public String start(Model model) {
		return "login.html";
	}
	
	@GetMapping("/contact")
	public String contact(Model model) {
		return "contact.html";
	}
	
	@GetMapping("/deaf01")
	public String df01(Model model) {
		return "deaf01.html";
	}
	
	@GetMapping("/index2")
	public String i2(Model model) {
		return "index-2.html";
	}
	
	@GetMapping("/services")
	public String service(Model model) {
		return "services.html";
	}
	
	@GetMapping("/about")
	public String about(Model model) {
		return "about.html";
	}
	
	@GetMapping("/choose")
	public String choose(Model model) {
		return "choose.html";
	}
	
	@GetMapping("/team")
	public String team(Model model) {
		return "team.html";
	}
	
	@GetMapping("/videocards")
	public String vc(Model model) {
		return "videocards.html";
	}
	
	@GetMapping("/quiz-start")
	public String quiz(Model model) {
		return "quiz-choose.html";
	}
	
	@GetMapping("/dashboard")
	public String dash(Model model) {
		return "dashboard.html";
	}
	
}
