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
	
	@GetMapping("/deaf_eng_videos")
	public String d1(Model model) {
		return "deaf_eng_videos.html";
	}
	
	@GetMapping("/deaf_gk_videos")
	public String d2(Model model) {
		return "deaf_gk_videos.html";
	}
	
	@GetMapping("/deaf_math_videos")
	public String d3(Model model) {
		return "deaf_math_videos.html";
	}
	
	@GetMapping("/deaf_signlang_videos")
	public String d4(Model model) {
		return "deaf_signlang_videos.html";
	}
	
	@GetMapping("/mentors")
	public String mentors(Model model) {
		return "mentors.html";
	}
	
	@GetMapping("/games")
	public String games(Model model) {
		return "games.html";
	}
}
