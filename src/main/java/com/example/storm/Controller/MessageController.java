package com.example.storm.Controller;

import com.example.storm.Server;
import com.example.storm.Controller.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MessageController {


    @MessageMapping("/message")
    @SendTo("/topic/return-to")
    public Message getContent(@RequestBody Message message) {
    	int keys = keys();
		Map<String, Object> userData = new HashMap<>();
		userData.put(Integer.toString(keys), message.getName() + " " + message.getContent());
		DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("comments");
		databaseReference.updateChildren(userData, new DatabaseReference.CompletionListener() {
			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				if (error != null) {
                    error.toException().printStackTrace();
                } else {
                }
			}
        });
		message.setName(find(message.getName()));
        return message;
    }
    
    @GetMapping("/chat-room-start")
    public String start(Model model){
    	model.addAttribute("ID", Server.ID);
    	model.addAttribute("uname", find(Server.ID));
    	return "community.html";
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = new ArrayList<>(); // Replace with your service method to fetch messages
		int count = 0;
		while(true) {
			String key = add(count++);
			if(key == null) break;
            String name = find(key.substring(0, key.indexOf(' ')));
            String msg = key.substring(key.indexOf(' ') + 1, key.length());
            messages.add(new Message(name, msg));
		}
        return ResponseEntity.ok(messages);
    }
    
    public String add(int number) {
		String ans = "";
		DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("comments").child(Integer.toString(number));
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
        DatabaseReference reference = database.getReference("comments"); // Replace with your actual node path

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

}
