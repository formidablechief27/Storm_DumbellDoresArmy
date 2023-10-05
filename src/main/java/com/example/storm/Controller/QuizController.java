package com.example.storm.Controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.storm.Server;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

@Controller
public class QuizController {
	
	String Questions[][] = {{"What is the ISL sign for \"addition\"?", "How do you sign \"subtraction\" in ISL?", "What does the ISL sign for \"multiplication\" look like?", "How do you sign \"division\" in ISL?", "What is the ISL sign for \"equal\"?", "How do you sign the number \"10\" in ISL?", "What is the ISL sign for \"plus\" (in the context of mathematics)?", "How do you sign the number \"5\" in ISL?", "What is the ISL sign for \"minus\" (in the context of mathematics)?", " How do you sign \"times\" (in the context of multiplication) in ISL?"}, {}, {}, {}};
	
	String OpA[][] = {{"A hand motion representing combining two items", "A hand motion representing taking away", "A repeated forward movement of the dominant hand", "A hand motion representing separating or sharing", "Bringing both hands together and touching fingertips", "A closed fist with the thumb extended", "A forward motion with the dominant hand", "The extended fingers of one hand", "A backward motion with the dominant hand", "A circular motion of the dominant hand"}, {}, {}, {}};
	
	String OpB[][] = {{"A motion of bringing fingers together", "A motion of spreading fingers apart", "A circular movement of the dominant hand", "A downward motion with the dominant hand", "A single hand motion representing balance", "An open hand with all fingers extended", "Touching the tips of the fingers of both hands together", "A closed fist with the pinky finger extended", "Touching the index fingers of both hands and moving them apart", "A back-and-forth movement of the dominant hand"}, {}, {}, {}};
	
	String ans[][] = {{"A", "A", "B", "A", "A", "A", "A", "A", "A", "A"}, {}, {}, {}};

	String answered[];
	
	@GetMapping("/maths-quiz")
	public String start_quiz(Model model) {
		answered = new String[10];
		ArrayList<String> questions = new ArrayList<>();
    	ArrayList<String> optionA = new ArrayList<>();
    	ArrayList<String> optionB = new ArrayList<>();
    	int index = 0;
    	for(int i=0;i<5;i++) {
    		questions.add(Questions[index][i]);
			optionA.add(OpA[index][i]);
			optionB.add(OpB[index][i]);
    	}
    	String section = "1";
    	String subject = "maths";
    	model.addAttribute("value", section);
		model.addAttribute("questions", questions);
    	model.addAttribute("optionsA", optionA);
    	model.addAttribute("optionsB", optionB);
    	model.addAttribute("val", subject);
        return "quiz.html";
	}
	
	@GetMapping("/general-knowledge-quiz")
	public String start_quiz_2(Model model) {
		answered = new String[10];
		ArrayList<String> questions = new ArrayList<>();
    	ArrayList<String> optionA = new ArrayList<>();
    	ArrayList<String> optionB = new ArrayList<>();
    	int index = 1;
    	for(int i=0;i<5;i++) {
    		questions.add(Questions[index][i]);
			optionA.add(OpA[index][i]);
			optionB.add(OpB[index][i]);
    	}
    	String section = "1";
    	String subject = "general_knowledge";
    	model.addAttribute("value", section);
		model.addAttribute("questions", questions);
    	model.addAttribute("optionsA", optionA);
    	model.addAttribute("optionsB", optionB);
    	model.addAttribute("val", subject);
        return "quiz.html";
	}
	
	@GetMapping("/english-quiz")
	public String start_quiz_3(Model model) {
		answered = new String[10];
		ArrayList<String> questions = new ArrayList<>();
    	ArrayList<String> optionA = new ArrayList<>();
    	ArrayList<String> optionB = new ArrayList<>();
    	int index = 2;
    	for(int i=0;i<5;i++) {
    		questions.add(Questions[index][i]);
			optionA.add(OpA[index][i]);
			optionB.add(OpB[index][i]);
    	}
    	String section = "1";
    	String subject = "english";
    	model.addAttribute("value", section);
		model.addAttribute("questions", questions);
    	model.addAttribute("optionsA", optionA);
    	model.addAttribute("optionsB", optionB);
    	model.addAttribute("val", subject);
        return "quiz.html";
	}
	
	@GetMapping("/sign-language-quiz")
	public String start_quiz_4(Model model) {
		answered = new String[10];
		ArrayList<String> questions = new ArrayList<>();
    	ArrayList<String> optionA = new ArrayList<>();
    	ArrayList<String> optionB = new ArrayList<>();
    	int index = 3;
    	for(int i=0;i<5;i++) {
    		questions.add(Questions[index][i]);
			optionA.add(OpA[index][i]);
			optionB.add(OpB[index][i]);
    	}
    	String section = "1";
    	String subject = "sign";
    	model.addAttribute("value", section);
		model.addAttribute("questions", questions);
    	model.addAttribute("optionsA", optionA);
    	model.addAttribute("optionsB", optionB);
    	model.addAttribute("val", subject);
        return "quiz.html";
	}
	
    @PostMapping("/submit-answers")
    public String submitAnswersSection1(@RequestParam("q0") String q1, @RequestParam("q1") String q2, @RequestParam("q2") String q3, @RequestParam("q3") String q4, @RequestParam("q4") String q5, @RequestParam("subject") String subject, @RequestParam("section") String section, Model model) {
    	ArrayList<String> questions = new ArrayList<>();
    	ArrayList<String> optionA = new ArrayList<>();
    	ArrayList<String> optionB = new ArrayList<>();
    	int index = -1;
    	if(subject.equals("maths")) {
    		index = 0;
    	}
    	else if (subject.equals("general_knowledge")) {
    		index = 1;
    	}
    	else if (subject.equals("english")) {
    		index = 2;
    	}
    	else {
    		index = 3;
    	}
    	if(section.equals("1")) {
    		answered[0] = q1;
    		answered[1] = q2;
    		answered[2] = q3;
    		answered[3] = q4;
    		answered[4] = q5;
    		for(int i=5;i<10;i++) {
    			questions.add(Questions[index][i]);
    			optionA.add(OpA[index][i]);
    			optionB.add(OpB[index][i]);
    		}
    		section = "2";
    		model.addAttribute("value", section);
    		model.addAttribute("questions", questions);
        	model.addAttribute("optionsA", optionA);
        	model.addAttribute("optionsB", optionB);
        	model.addAttribute("val", subject);
            return "quiz.html";
    	}
    	else {
    		answered[5] = q1;
    		answered[6] = q2;
    		answered[7] = q3;
    		answered[8] = q4;
    		answered[9] = q5;
    		int score = 0;
    		ArrayList<Integer> points = new ArrayList<>();
    		ArrayList<Integer> sr_no = new ArrayList<>();
    		for(int i=0;i<10;i++) {
    			sr_no.add(i+1);
    			if(answered[i].equals(ans[index][i])) {
    				score++;
    				points.add(1);
    			}
    			else {
    				points.add(0);
    			}
    		}
    		DataEntry(subject, points);
    		model.addAttribute("num", sr_no);
    		model.addAttribute("sub", answered);
    		model.addAttribute("ans", ans[index]);
    		model.addAttribute("points", points);
    		model.addAttribute("subject", subject);
    		model.addAttribute("pts", score);
    		return "quiz-end.html";
    	}
    }
    
    public PriorityQueue<long[]> queue(int index){
		PriorityQueue<long[]> queue = new PriorityQueue<long[]>((a,b)->{
			if (a[index] < b[index]){
                return 1;
        	}
        	else if (a[index] == b[index] && a[index+1] > b[index+1]) {
        		return 1;
        	}
        	else {
        		return -1;
        	}
		});
        return queue;
	}
    
    @GetMapping("/leaderboard1")
	public String leaderboard1(Model model) {
		String answer = "maths";
		HashMap<String, Integer> keys = new HashMap<>();
		TreeMap<Integer, int[]> arr[] = new TreeMap[(int)1e5];
		String keynames[] = new String[(int)1e5];
		PriorityQueue<long[]> queue = queue(0);
		CompletableFuture<Boolean> future = new CompletableFuture<>();
		DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(answer);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String key = childSnapshot.getKey();
                    arr[keys.size()+1] = new TreeMap<>();
                    keys.put(key, keys.size()+1);
                }
                future.complete(true);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getMessage());
            }
        });
        try {
			if(future.get()) {
				for(Map.Entry<String, Integer> entry : keys.entrySet()) {
					TreeMap<Integer, int[]> map = get(answer, entry.getKey());
					arr[entry.getValue()] = map;
					keynames[entry.getValue()] = entry.getKey();
					int pts = 0;
					int time = 0;
					for(Map.Entry<Integer, int[]> ent : map.entrySet()) {
						int a[] = ent.getValue();
						pts += a[0];
						if(a[1] != -1) time += a[1];
					}
					queue.add(new long[] {pts, time, entry.getValue()});
				}
				ArrayList<Integer> rank = new ArrayList<>();
				ArrayList<String> users = new ArrayList<>();
				ArrayList<Integer> points = new ArrayList<>();
				ArrayList<Integer> penalty = new ArrayList<>();
				ArrayList<Integer> score[] = new ArrayList[11];
				for(int i=1;i<=10;i++) score[i] = new ArrayList<>();
				int r = 1;
				while(!queue.isEmpty()) {
					long curr[] = queue.poll();
					int index = (int)curr[2];
					String getname = find(keynames[index]);
					users.add(getname);
					points.add((int)curr[0]);
					penalty.add((int)curr[1]);
					rank.add(r++);
					TreeMap<Integer, int[]> cmap = arr[index];
					for(Map.Entry<Integer, int[]> entry : cmap.entrySet()) {
						int pt = entry.getValue()[0];
						score[entry.getKey()].add(pt);
					}
				}
				model.addAttribute("subject", answer);
				model.addAttribute("rank", rank);
				model.addAttribute("names", users);
				model.addAttribute("pts", points);
				for(int i=1;i<=10;i++) model.addAttribute("score"+i, score[i]);
				return "leaderboard.html";
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
    
    public TreeMap<Integer, int[]> get(String subject, String key){
		TreeMap<Integer, int[]> map = new TreeMap<>();
		CompletableFuture<Boolean> future = new CompletableFuture<>();
		DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(subject).child(key);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                	String val = childSnapshot.getValue(String.class);
                	int pts = Integer.parseInt(val.substring(0, val.indexOf(' ')));
                	int time = Integer.parseInt(val.substring(val.indexOf(' ')+1, val.length()));
                	map.put(Integer.parseInt(childSnapshot.getKey()), new int[] {pts, time});
                }
                future.complete(true);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getMessage());
            }
        });
        try {
			if(future.get()) {
				return map;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
	}
    
    public void DataEntry(String subject, ArrayList<Integer> pts) {
		Map<String, Object> userData = new HashMap<>();
        for(int i=0;i<10;i++) {
        	userData.put(Integer.toString(i+1), pts.get(i) + " " + 0);
        }
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference(subject)
                .child(Server.ID);
        databaseReference.setValue(userData, new DatabaseReference.CompletionListener() {
			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				if (error != null) {
                    error.toException().printStackTrace();
                } else {
                }
			}
        });
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

