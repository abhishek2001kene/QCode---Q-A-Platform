package Model;

public class Answer {

    private int id;          
    private String answer;
    private User author;

 
    public Answer(String answer, User author) {
        this.answer = answer;
        this.author = author;
    }


    public Answer(int id, String answer, User author) {
        this.id = id;
        this.answer = answer;
        this.author = author;
    }


    public int getId() {
        return id;
    }

    public String getAnswer() {
        return answer;
    }

    public User getAuthor() {
        return author;
    }

 
    public void setAnswer(String answer) {
        this.answer = answer;
    }


    public void displayAnswer() {
        System.out.printf("--> (%s) %s%n", author.getName(), answer);
    }
}



//	
//}
