package Model;

public class Answer {

    private int id;          // DB ID
    private String answer;
    private User author;

    // ✅ Constructor (for creating new answer)
    public Answer(String answer, User author) {
        this.answer = answer;
        this.author = author;
    }

    // ✅ Constructor (for fetching from DB later)
    public Answer(int id, String answer, User author) {
        this.id = id;
        this.answer = answer;
        this.author = author;
    }

    // ✅ GETTERS
    public int getId() {
        return id;
    }

    public String getAnswer() {
        return answer;
    }

    public User getAuthor() {
        return author;
    }

    // ✅ SETTER
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    // ✅ DISPLAY
    public void displayAnswer() {
        System.out.printf("--> (%s) %s%n", author.getName(), answer);
    }
}


//package Model;
//
//public class Answer {
//	private static int idCounter;
//	private int id;
//	private String answer;
//	private User author;
////	private boolean published;
//	
//
//	{
//		this.id = ++idCounter ;
//	}
//	
//	
//	public Answer(String answer, User author) {
//		super();
//		
//		this.answer = answer;
//		this.author = author;
//		
//	} 
//	
//	
//	
//	public void displayAnswer() {
//		 System.out.printf("-->%dA (%s) = %s %n", id, author.getName(), answer);
//			
//			
//	}
//	
//
//	
//	public void setAnswer(String answer) {
//		this.answer = answer;
//	}
//	
//}
