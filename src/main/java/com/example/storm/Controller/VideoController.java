package com.example.storm.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.storm.Server;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

@Controller
public class VideoController {
	
	@GetMapping("/lol")
	public String st(Model model) {
		return "upload.html";
	}
	
	@GetMapping("/view")
	public String st1(Model model) {
		ArrayList<String> urls = new ArrayList<>();
		int count = 0;
		while(true) {
			String key = add(count++);
			if(key == null) break;
            urls.add(key);
		}
		model.addAttribute("videoUrls", urls);
		return "videos.html";
	}
	
	@PostMapping("/uploadfile")
	public String video(@RequestParam("file") MultipartFile file, Model model) {
		if(!file.isEmpty()) {
			String link = generateDownloadUrl(file);
			int keys = keys();
			Map<String, Object> userData = new HashMap<>();
			userData.put(Integer.toString(keys), link);
			DatabaseReference databaseReference = FirebaseDatabase.getInstance()
	                .getReference("videos").child("p1");
			databaseReference.updateChildren(userData, new DatabaseReference.CompletionListener() {
				@Override
				public void onComplete(DatabaseError error, DatabaseReference ref) {
					if (error != null) {
                        error.toException().printStackTrace();
                    } else {
                    }
				}
            });
		}
		ArrayList<String> urls = new ArrayList<>();
		int count = 0;
		while(true) {
			String key = add(count++);
			if(key == null) break;
            urls.add(key);
		}
		model.addAttribute("videoUrls", urls);
		return "videos.html";
	}
	
	public String generateDownloadUrl(MultipartFile multipartFile) {
		String storagePath = "videos/p1/" + multipartFile.getOriginalFilename();
	    try {
	        String mediaLink = uploadFileToFirebaseStorage(multipartFile);
	        if (mediaLink != null) {
	            return mediaLink;
	        } else {
	            // Handle the case where the upload failed
	            return null;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	public String uploadFileToFirebaseStorage(MultipartFile multipartFile) {
	    try {
	        // Initialize Google Cloud Storage with your service account key JSON
	        GoogleCredentials credentials = GoogleCredentials.fromStream(
	                new FileInputStream("C:/Users/Dhrumil/OneDrive/Desktop/kaam/Storm/src/main/resources/key.json"));
	        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
	        String storagePath = "videos/p1/" + multipartFile.getOriginalFilename();
	        BlobId blobId = BlobId.of("dumbell-76468.appspot.com", storagePath); // Replace with your storage bucket name
	        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

	        // Read the file's content as bytes
	        byte[] fileBytes = multipartFile.getBytes();

	        // Upload the file to Google Cloud Storage
	        storage.create(blobInfo, fileBytes);

	        // Get the public download URL for the uploaded file
	        return storage.get(blobId).signUrl(3, TimeUnit.DAYS).toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	public int keys() {
		CompletableFuture<Integer> fu = new CompletableFuture<>();
		FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("videos").child("p1"); // Replace with your actual node path

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
		DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("videos").child("p1").child(Integer.toString(number));
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
