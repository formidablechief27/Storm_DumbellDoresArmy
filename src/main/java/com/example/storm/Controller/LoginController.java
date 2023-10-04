package com.example.storm.Controller;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import com.example.storm.*;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.springframework.http.HttpStatus;


@Controller
public class LoginController {
	
    @GetMapping("/start")
    public String launch(Model model) {
    	return "login.html";
    }
	
    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String email, @RequestParam String password, Model model) {
    	if(password.length() < 6) {
    		 return "registration-error";
    	}
    	try {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setPassword(password);
            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(userRecord.getUid());
            Map<String, Object> userData = new HashMap<>();
            userData.put("username", username);
            userData.put("email", email);
            userData.put("password", password);
            //userData.put("disable", disable);
            
            databaseReference.setValue(userData, new DatabaseReference.CompletionListener() {
				@Override
				public void onComplete(DatabaseError error, DatabaseReference ref) {
					if (error != null) {
                        error.toException().printStackTrace();
                    } else {
                    }
				}
            });
            model.addAttribute("username", username);
            model.addAttribute("email", email);
            return "index.html";
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            return "registration-error";
        }
    }
    
    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password, Model model) {
    	try {
            UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
            if (userRecord != null) {
                String uid = userRecord.getUid();
                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users").child(uid).child("password");
                CompletableFuture<String> future = new CompletableFuture<>();
                databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        String value = snapshot.getValue(String.class);
                        if (value != null && value.equals(password)) {
                            Server.ID = uid;
                            future.complete(uid);
                        } else {
                            future.completeExceptionally(new RuntimeException("Incorrect password"));
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        future.completeExceptionally(error.toException());
                    }
                });
                String uidResult = future.get();
                model.addAttribute("UID", uidResult);
                return "index.html";
            } else {
            	return "login-failure.html";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return "login-failure.html";
    }
    
    public String find(String key) {
		String ans = "";
		DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users").child(key).child("username");
        CompletableFuture<String> future = new CompletableFuture<>();
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                future.complete(value);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(error.toException());
            }
        });
        try {
			return future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
	}
	
}
