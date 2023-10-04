package com.example.storm.Controller;

import com.example.storm.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

@Controller
public class CommunityController {
	
	ArrayList<String> comments = new ArrayList<>();
	ArrayList<String> names = new ArrayList<>();
	HashMap<Integer, String> map = new HashMap<>();
	
	@MessageMapping("/chat")
    @SendTo("/topic/messages")
    public String sendMessage(String reply) {
		String name = find(Server.ID);
		Map<String, Object> userData = new HashMap<>();
		reply = reply.substring(reply.indexOf(":") + 2, reply.length() - 2);
		userData.put(Integer.toString(keys()), Server.ID + " " + reply);
        //userData.put(Server.ID + " " + reply, comments.size());
		String fname = "";
		fname = "Self:" + name + " " + reply;
        //comments.add(fname);
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
        return fname;
    }
	
	@GetMapping("/comment")
	public String comm1(Model model) {
		comments = new ArrayList<>();
		names = new ArrayList<>();
		map = new HashMap<>();
		int count = 0;
		while(true) {
			String key = add(count++);
			if(key == null) break;
            String userId = key.substring(0, key.indexOf(' '));
            String name = find(userId);
            key = key.substring(key.indexOf(' '), key.length());
            key = name + " " + key;
            if (userId.equals(Server.ID)) {
                key = "Self:" + key;
            }
            comments.add(key);
		}
		model.addAttribute("list", comments);
		return "community.html";
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
}
