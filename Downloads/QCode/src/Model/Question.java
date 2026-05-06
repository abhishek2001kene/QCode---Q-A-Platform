package Model;

import java.util.*;
import Model.User;

public class Question {
	
	
	private Scanner sc = new Scanner(System.in);
	
	private static int idCounter;
	private int id;
	private String title;
	private User author;
	private ArrayList<Answer> answers = new ArrayList<Answer>();
	private boolean published;
	

	{
		this.id = ++idCounter ;
	}
	
	
	
	public Question(String title, User author) {
		super();
		this.title = title;
		this.author = author;
		
	}
	
	
	public void displayQuestion() {
	    System.out.printf("%dQ (%s) %s%n", id,author.getName() , title );
//	    if(answers.size() > 0) System.out.println("A:" + answers.size());
	   
	}
	
	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public ArrayList<Answer> getAnswer() {
		return this.answers;
	}
	
	

	public void addAnswer(Answer a) {
		this.answers.add(a);
	}
	
	public void publish(boolean published) {
		this.published = published;
	}
	
	public void setquestion(String title ) {
		this.title = title;
	}
	
	
	
	
	
	
	
	
	
	
	
}
