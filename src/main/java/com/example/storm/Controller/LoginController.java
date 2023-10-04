package com.example.storm.Controller;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpStatus;


@Controller
public class LoginController {
	
	String supabaseUrl = "https://vermiinqbutcpiqqhlfe.supabase.co"; // Replace with your Supabase URL
	String supabaseApiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZlcm1paW5xYnV0Y3BpcXFobGZlIiwicm9sZSI6ImFub24iLCJpYXQiOjE2OTYzMTQ3ODYsImV4cCI6MjAxMTg5MDc4Nn0.8Uvjp_ZrHDPUJ3yAkjOFS4hPh2RAnF-1UX3EXLT6fIw";
	String tableName = "UserData"; // Replace with your Supabase table name
    
    @GetMapping("/start")
    public String launch(Model model) {
    	return "login.html";
    }
	
	@PostMapping("/login")
	public String submit(@RequestParam String username, @RequestParam String email, @RequestParam String password, Model model){
		return null;
	}
	
}
