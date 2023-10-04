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
	
}
