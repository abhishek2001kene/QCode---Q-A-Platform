package Model;

import java.util.ArrayList;

public class User {

    private int id;
    private String name;
    private String email;
    private String password;

    private ArrayList<Question> myQuestions = new ArrayList<>();
    private ArrayList<Question> myDrafts = new ArrayList<>();
    private ArrayList<Question> myBookmarks = new ArrayList<>();

    // ✅ Normal constructor
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // ✅ DB constructor
    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Question> getMyQuestins() {
        return myQuestions;
    }

    public void addQuestion(Question q) {
        myQuestions.add(q);
    }

    public void addDraft(Question q) {
        myDrafts.add(q);
    }

    public ArrayList<Question> getMyDrafts() {
        return new ArrayList<>(myDrafts);
    }

    public void addBookmark(Question q) {
        myBookmarks.add(q);
    }

    public ArrayList<Question> getMyBookmarks() {
        return new ArrayList<>(myBookmarks);
    }
}




//package Model;
//
//import java.util.ArrayList;
//import Model.Question;
//
//
//public class User {
//	
//	
////	private static int idCounter;
//	private int id;
//	
//	private String name;
//	private String email;
//	private String password;
//	
//	private ArrayList<Question>  myQuestions = new ArrayList<Question>();
//	private ArrayList<Question> myDrafts = new ArrayList<Question>();
//	private ArrayList<Question> myBookmarks = new ArrayList<Question>();
//	
//	
////	{
////		this.id = ++idCounter ;
////	}
//	
//	
////	public User(String name, String email, String password) {
////		super();
////		
////		this.name = name;
////		this.email = email;
////		this.password = password;
////	}
////	
//	public User(int id, String name, String email, String password) {
//	    this.id = id;
//	    this.name = name;
//	    this.email = email;
//	    this.password = password;
//	}
//	
//	public void displayuser() {
//		System.out.println("id :" + id);
//		System.out.println("name :" + name);
//		System.out.println("email :" + email);
//		System.out.println("password:" +password );
//		System.out.println("myQuestion :" + myQuestions);
//		System.out.println("mydraft :" + myDrafts);
//		System.out.println("myBookmark :" + myBookmarks);
//		System.out.println("------------------------------------------------");
//		
//	}
//	
//	
//	
//	public int getId() {
//	    return id;
//	}
//	
////Get static fields 	
//	public String getName() {
//		return this.name;
//	}
//	
//	public String getEmail() {
//		return this.email;
//	}
//	
//	public String getPassword() {
//		return this.password;
//	}
//	
//	
//	
//// getttres for list 
//	
//	public ArrayList<Question> getMyQuestins() {
//		return this.myQuestions;
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//// CURD operation All question List 
//	
//	public void addQuestion(Question q) {
//		this.myQuestions.add(q);
//	}
//	
//	public void removeQuestion(Question q) {
//		this.myQuestions.remove(q);
//	}
//	
//	public ArrayList<Question> getMyQuestions(){
//		return new ArrayList<>(myQuestions);
//	}
//	
//	
//	
//
//	
//	
////CORD Operation on draft question list 
//	
//	public void addDraft(Question q) {
//		this.myDrafts.add(q);
//	}
//	
//	public void removeDraft(Question q) {
//		this.myDrafts.remove(q);
//	}
//	
//	public ArrayList<Question> getMyDrafts(){
//		return new ArrayList<>(myDrafts);
//	}
//	
//	
//	
//	
//	
//	
//	
//	
////CURD operation on bookmark question list 	
//	
//	public void addBookmark(Question q) {
//		this.myBookmarks.add(q);
//	}
//	
//	public void removeBookmark(Question q) {
//		this.myBookmarks.remove(q);
//	}
//	
//	public ArrayList<Question> getMyBookmarks(){
//		return new ArrayList<>(myBookmarks);
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	
//}
