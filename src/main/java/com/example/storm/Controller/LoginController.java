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
	
    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password, @RequestParam String userType, @RequestParam String disType, Model model) {
    	int keys = keys();
    	DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users").child(Integer.toString(keys));
    	Map<String, Object> userData = new HashMap<>();
		userData.put("username", username);
		userData.put("password", password);
		userData.put("type", userType);
		userData.put("disable", disType);
		databaseRef.updateChildren(userData, new DatabaseReference.CompletionListener() {
			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				if (error != null) {
                    error.toException().printStackTrace();
                } else {
                }
			}
        });
		if(userType.equals("student")) {
			return "index.html";
		}
		return "teacher.html";
    }
    
    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model) {
    	int count = 0;
    	boolean flag = true;
    	while(true) {
    		String s1 = add(count);
    		String s2 = add2(count);
    		if(s1 == null || s2 == null) break;
    		if(s1.equals(password) && s2.equals(username)) {
    			flag = false;
    			break;
    		}
    		count++;
    	}
    	if(!flag) {
    		if(add3(count).equals("student")) return "index.html";
    		else return "teacher.html";
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
    
    public int keys() {
		CompletableFuture<Integer> fu = new CompletableFuture<>();
		FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("users"); // Replace with your actual node path

        // Add a ValueEventListener to count the child keys
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int numberOfKeys = (int) dataSnapshot.getChildrenCount();
                fu.complete(numberOfKeys);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getMessage());
            }
        });
        try {
			return fu.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return 0;
	}
    
    public String add(int number) {
		String ans = "";
		DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users").child(Integer.toString(number)).child("password");
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
		return "done";
	}
    
    public String add2(int number) {
		String ans = "";
		DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users").child(Integer.toString(number)).child("username");
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
		return "done";
	}
    
    public String add3(int number) {
		String ans = "";
		DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users").child(Integer.toString(number)).child("type");
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
		return "done";
	}
	
}
