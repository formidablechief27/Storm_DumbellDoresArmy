package com.example.storm.Controller;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    		model.addAttribute("num", sr_no);
    		model.addAttribute("sub", answered);
    		model.addAttribute("ans", ans[index]);
    		model.addAttribute("points", points);
    		model.addAttribute("subject", subject);
    		model.addAttribute("pts", score);
    		return "quiz-end.html";
    	}
    }
}

