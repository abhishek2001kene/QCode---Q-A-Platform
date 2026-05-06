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

    
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

   
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



