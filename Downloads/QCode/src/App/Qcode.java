package App;

import java.util.*;

import DAO.AnswerDAO;
import DAO.QuestionDAO;
import Model.Answer;
import Model.Question;
import Model.User;

// ✅ NEW: import services (instead of DAO)
import Service.UserService;
import Service.QuestionService;
import Service.AnswerService;

public class Qcode {

    private Scanner sc = new Scanner(System.in);

    // ================= MAIN =================
    public void launchApp() {

        while (true) {

            System.out.println("\n⫸⫸⫸⫸⫸  WELCOME TO Qcode  ⫷⫷⫷⫷⫷");
            System.out.println("1. Create account");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            System.out.print("Enter your option : ");
            int res = sc.nextInt();

            switch (res) {
                case 1 -> createAccount();
                case 2 -> login();
                case 3 -> {
                    System.out.println("THANK YOU FOR USING Q-CODE");
                    System.exit(0);
                }
                default -> System.out.println("Invalid Option");
            }
        }
    }

    // ================= USER =================

    public void createAccount() {

        System.out.println("\n--- Create Account ---");
        sc.nextLine();

        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.next();

        System.out.print("Enter Password: ");
        String password = sc.next();

        // ✅ CHANGED: DAO → Service
        UserService userService = new UserService();
        userService.register(name, email, password);

        System.out.println("Account created successfully ✅");
    }

    public void login() {

        System.out.println("\n--- Login ---");
        sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.next();

        System.out.print("Enter Password: ");
        String password = sc.next();

        // ✅ CHANGED: DAO → Service
        UserService userService = new UserService();
        User user = userService.login(email, password);

        if (user != null) {
            homepage(user);
        } else {
            System.out.println("Invalid credentials ❌");
        }
    }

    // ================= HOME =================

    public void homepage(User user) {

        while (true) {

            System.out.println("\n--- HOME ---");
            System.out.println("1. Post Question");
            System.out.println("2. Search Question");
            System.out.println("3. Add Answer");
            System.out.println("4. Go To Feed");
            System.out.println("5. My Questions");
            System.out.println("6. My Drafts");
            System.out.println("7. Update Question");
            System.out.println("8. Delete Question");
            System.out.println("9. Logout");

            System.out.print("Enter option: ");
            int res = sc.nextInt();

            switch (res) {
                case 1 -> createQuestion(user);
                case 2 -> search();
                case 3 -> addAnswer(user);
                case 4 -> goToFeed();
                case 5 -> viewMyQuestions(user);
                case 6 -> drafts(user);
                case 7 -> updateQuestionUI(user);  
                case 8 -> deleteQuestionUI(user);   
                case 9 -> {
                    System.out.println("Logged out.");
                    return;
                }
                default -> System.out.println("Invalid option");
            }
        }
    }

    // ================= QUESTION =================

   private void createQuestion(User user) {

    sc.nextLine();
    System.out.print("Enter Question: ");
    String title = sc.nextLine();

    Question newQuestion = new Question(title, user);

    // ✅ ASK FOR ANSWER (NEW FIX)
    System.out.println("Do you want to add answer now?");
    System.out.println("1. Yes    2. No");

    String ansChoice = sc.next();

    String answerText = null;

    if (ansChoice.equals("1")) {
        sc.nextLine();
        System.out.print("Enter Answer: ");
        answerText = sc.nextLine();
    }

    System.out.println("1. Post Question");
    System.out.println("2. Save as Draft");
    System.out.print("Choose option: ");

    String choice = sc.next();

    QuestionDAO qdao = new QuestionDAO();
    AnswerDAO adao = new AnswerDAO();

    if (choice.equals("1")) {

        // ✅ Save question as published
        int qid = qdao.saveQuestionReturnId(newQuestion, user.getId(), false);

        // ✅ Save answer if exists
        if (answerText != null && !answerText.isEmpty()) {
            adao.saveAnswer(new Answer(answerText, user), user.getId(), qid);
        }

        System.out.println("Question posted successfully ✅");

    } 
    else if (choice.equals("2")) {

        // ✅ Save as draft
        int qid = qdao.saveQuestionReturnId(newQuestion, user.getId(), true);

        // ✅ Save answer if exists
        if (answerText != null && !answerText.isEmpty()) {
            adao.saveAnswer(new Answer(answerText, user), user.getId(), qid);
        }

        System.out.println("Saved as draft ✅");

    } 
    else {
        System.out.println("Invalid option");
    }
}

    // ================= FEED =================

    private void goToFeed() {

        System.out.println("\n--- FEED ---");

        // ✅ CHANGED: DAO → Service
        QuestionService qs = new QuestionService();
        qs.showFeed();
    }

    // ================= MY QUESTIONS =================

    private void viewMyQuestions(User user) {

        System.out.println("\n--- My Questions ---");

        // ✅ CHANGED: DAO → Service
        QuestionService qs = new QuestionService();
        qs.showMyQuestions(user.getId());
    }

    // ================= ANSWER =================

    private void addAnswer(User user) {

        System.out.println("\n--- ADD ANSWER ---");

        System.out.print("Enter Question ID: ");
        int qid = sc.nextInt();

        sc.nextLine();
        System.out.print("Enter your answer: ");
        String text = sc.nextLine();

        // ✅ CHANGED: DAO → Service
        AnswerService as = new AnswerService();
        as.addOrUpdateAnswer(text, user.getId(), qid);
    }

    // ================= SEARCH =================

    private void search() {

        System.out.println("\n--- Search Question ---");

        sc.nextLine();
        System.out.print("Search: ");
        String search = sc.nextLine();

        // ✅ CHANGED: DAO → Service
        QuestionService qs = new QuestionService();
        qs.search(search);
    }

    // ================= DRAFT =================

    private void drafts(User user) {

        System.out.println("\n--- YOUR DRAFTS ---");

        // ✅ CHANGED: DAO → Service
        QuestionService qs = new QuestionService();
        AnswerService as = new AnswerService();

        qs.showDrafts(user.getId());

        System.out.println("\n1. Edit Draft");
        System.out.println("2. Publish Draft");
        System.out.println("3. Back");

        String choice = sc.next();

        if (choice.equals("1")) {

            System.out.print("Enter Draft Question ID: ");
            int qid = sc.nextInt();

            sc.nextLine();
            System.out.print("Enter new title: ");
            String newTitle = sc.nextLine();

            qs.updateDraft(qid, newTitle);

            System.out.print("Add/Update Answer: ");
            String ansText = sc.nextLine();

            as.addOrUpdateAnswer(ansText, user.getId(), qid);

            System.out.println("Draft updated ✅");

        } 
        else if (choice.equals("2")) {

            System.out.print("Enter Draft Question ID: ");
            int qid = sc.nextInt();

            qs.publishDraft(qid);
        }
    }
    
    private void updateQuestionUI(User user) {

        QuestionDAO qdao = new QuestionDAO();

        System.out.println("\n--- YOUR QUESTIONS ---");
        qdao.getQuestionsByUser(user.getId());  // ✅ SHOW FIRST

        System.out.print("\nEnter Question ID to update: ");
        int qid = sc.nextInt();

        sc.nextLine();
        System.out.print("Enter new title: ");
        String newTitle = sc.nextLine();

        // ✅ VALIDATION
        if (newTitle.trim().isEmpty()) {
            System.out.println("❌ Title cannot be empty");
            return;
        }

        qdao.updateQuestion(qid, user.getId(), newTitle);
    }
    
    
    private void deleteQuestionUI(User user) {

        QuestionDAO qdao = new QuestionDAO();

        System.out.println("\n--- YOUR QUESTIONS ---");
        qdao.getQuestionsByUser(user.getId());  // ✅ SHOW FIRST

        System.out.print("\nEnter Question ID to delete: ");
        int qid = sc.nextInt();

        System.out.print("Are you sure? (yes/no): ");
        String confirm = sc.next().toLowerCase();

        if (!confirm.equals("yes")) {
            System.out.println("Cancelled.");
            return;
        }

        qdao.deleteQuestion(qid, user.getId());
    }
    
    
    
}






//package App;
//
//import java.util.*;
//import DAO.*;
//import Model.User;
//import Model.Question;
//import Model.Answer;
//import Model.SearchQuestion;
//
//
//public class Qcode {
//
////	private ArrayList<User> allUsers = new ArrayList<User>();
////	private ArrayList<Question> allQuestion = new ArrayList<Question>();
//	
//	private Scanner sc = new Scanner(System.in);
//	
//		
//	public void launchApp() {
//			
//			for(; ;) {
//			System.out.println("");
//				
//				System.out.println("⫸⫸⫸⫸⫸⫸⫸⫸⫸⫸⫸⫸  WELCOME TO Qcode  ⫷⫷⫷⫷⫷⫷⫷⫷⫷⫷⫷⫷");
//				System.out.println("");
//				System.out.println("1. Create account");
//				System.out.println("2. Login");
//				System.out.println("3. Exit");
//				System.out.println("");
//				System.out.print("Enter your option : ");
//				int res = sc.nextInt();
//				
//				switch (res) {
//			    case 1 -> createAccount();
//			    case 2 -> login();
//			    case 3 -> {
//			    		System.out.println("");
//			    		System.out.println("😊😊😊😊😊😊😊  THANK YOU FOR USING Q-CODE   😊😊😊❤️😊😊😊😊😊 ");
//			    		System.exit(0);
//			    	}
//			    default -> System.out.println("Invalid Option");
//			}
//
//		}
//	
//	}
//	
//	
////	public void addUser(User newUser) {
////		allUsers.add(newUser);
////	}
//
//	public void createAccount() {
//		System.out.println("");
//		System.out.println("◈◈◈◈◈◈◈◈◈◈◈◈  Create your account  ◈◈◈◈◈◈◈◈◈◈◈◈");
//		System.out.println("");
//		sc.nextLine();
//		
//		System.out.print("Enter your name : ");
//		String name = sc.nextLine();
//		
//		System.out.print("Enter your Email : ");
//		String email = sc.next();
//		
//		System.out.print("Enter your Password : ");
//		String password = sc.next();
//		
//		
//		 UserDAO userDAO = new UserDAO();
//	        User newUser = new User(name, email, password);
//
//	        userDAO.saveUser(newUser);
//		
////		for(User u: allUsers) {
////			if(email.equals(u.getEmail())) {
////				System.out.println("");
////				System.out.println("The user already exist!!! Try to login.");
////				return;
////			}
////		}
////		
////		User newUser = new User(name, email, password);
////		addUser(newUser);
////		System.out.println("");
//		
//		System.out.println("====== New user Created ======");
//		System.out.println("");
//		
//		loginloop :
//			for(;;) {
//				System.out.println("1. Do you want to Login ?");
//				System.out.println("2. Not now.");
//				System.out.println("");
//				System.out.print("Enter Option : ");
//				int res = sc.nextInt();
//				
//				if(res == 1){   
//					login();
//					break loginloop;
//				
//				}
//				else if(res == 2) {
//					return;
//				}
//				else {
//					System.out.println("invalid Option");
//					continue loginloop ;
//				}
//					
//				
//			}
//		
//	}
//
//
//	public void login() {
//		
//		System.out.println("");
//		System.out.println("◈◈◈◈◈◈◈◈◈◈◈◈  Login to your account  ◈◈◈◈◈◈◈◈◈◈◈◈");
//		sc.nextLine();
//		System.out.println("");
//		System.out.print("Enter your Email : ");
//		String email = sc.next();
//		
//		System.out.print("Enter your Password : ");
//		String password = sc.next();
//		System.out.println("");
//		
//		   UserDAO userDAO = new UserDAO();
//	        User user = userDAO.login(email, password);
//		
//		  if (user != null) {
//	            homepage(user);
//	        } else {
//	            System.out.println("Invalid credentials ❌");
//	        }
//		
////		for(User ele : allUsers) {
////			
////			if(ele.getEmail().equals(email)  &&  ele.getPassword().equals(password)) {
////				homepage(ele);
////				return;
////			}	
////		}
////		
////		System.out.println("");
////		
////		System.out.println("User not found !!!");
////		
//		
//		
//		System.out.println("------------------------------------------------------");
//	}
//		
//	
//	public void homepage(User user) {
//
//	    while (true) {
//	        System.out.println("◈◈◈◈◈◈◈◈◈◈◈◈  HOME  ◈◈◈◈◈◈◈◈◈◈◈◈");
//	        System.out.println("");
//	        System.out.println("1. Post Question");
//	        System.out.println("2. Search Question");
//	        System.out.println("3. Add Answer");
//	        System.out.println("4. Go To Feed");
//	        System.out.println("5. My Questions");
//	        System.out.println("6. My Drafts");
//	        System.out.println("7. Logout");
//	        System.out.println("");
//	        System.out.print("Enter option: ");
//	        int res = sc.nextInt();
//	 
//
//	        switch (res) {
//	            case 1 -> createQuestion(user);
//	            case 2 -> search();
//	            case 3 -> addAnswer(user);
//	            case 4 -> goToFeed();
//	            case 5 -> viewMyQuestions(user);
//	            case 6 -> drafts(user);
//	            case 7 -> {
//	                System.out.println("Logged out.");
//	                return;
//	            }
//	            default -> System.out.println("Invalid option");
//	        }
//	    }
//	}
//
//	
//	private void createQuestion(User user ) {
//		
//		sc.nextLine();
//			System.out.print("Enter Question : ");
//			String title = sc.nextLine();
//			
//			
//			Question newQuestion = new Question(title, user );
//			
//			
//			System.out.println("Do you want to add an answer now?");
//		    System.out.println("1. Yes     2.No");
//		    System.out.print("Enter option: ");
//		    
//		 String ansChoice = sc.next();
//		 sc.nextLine();
//
//			if (ansChoice.equals("1")) {
//				System.out.println("");
//			    System.out.print("Enter answer : ");
//			    String answerText = sc.nextLine();
//			    
//			    Answer newAns = new Answer(answerText, user);
//			    newQuestion.addAnswer(newAns);
//			}
//
//			
//			
//			stoploop :
//				for(;;) {
//					System.out.println("");System.out.println("1. Post Question       2. Add to draft");
//					System.out.println("");
//					System.out.print("Enter option : ");
//					
//					String res = sc.next();
//
//					if (res.equals("1")) {
//					    user.addQuestion(newQuestion);
//					    allQuestion.add(newQuestion);
//					    System.out.println("");
//					    System.out.println("Question Posted successfully ✅✅✅.");
//					    System.out.println("");
//					    System.out.println("------------------------------------------------------");
//					    System.out.println("");
//					    System.out.println("1. Add Question     2. Homepage");
//					    System.out.print("Enter option : ");
//					    int res2 = sc.nextInt();
//
//					    if (res2 == 1) {
//					        createQuestion(user);
//					    } else {
//					    	 System.out.println("");
//							   
//					        return;
//					    }
//					}
//					else if (res.equals("2")) {
//					    user.addDraft(newQuestion);
//					    System.out.println("Question added to draft successfully ✅✅✅.");
//					    System.out.println("");
//					    System.out.println("------------------------------------------------------");
//					    System.out.println("");
//					    
//					    return;
//					}
//
//					
//					else {
//						System.out.println("Invalid Option .");
//					    continue stoploop;
//					}
//					
//				}			
//		
//	}
//	
//	
//	private void viewMyQuestions(User user) {
//		System.out.println("*************** Your Questions **************");
//		System.out.println("");
//		if(user.getMyQuestins().isEmpty()) {
//			 System.out.println("");
//			 System.out.print("                 Oops sorry !!! \nYou have not posted any question yet! Post a question.");
//			 System.out.println("");
//
//			
//			 System.out.println("");
//
//		
//			 return;
//		}
//		
//		 System.out.println("----------------------------------------");
//		for(Question ele : user.getMyQuestins()) {
//			
//			ele.displayQuestion();
//			
//			if(ele.getAnswer().isEmpty()) {
//				System.out.println("No answers yet.");
//			} else {
//				for(Answer a : ele.getAnswer()) {
//					a.displayAnswer();
//					 System.out.println("");
//					    
//					 
//					 
//					 
//					 
//				}
//			}
//		}
//		
//		 System.out.println("--------------------------------------------");
//	}
//	
//	
//	private void goToFeed() {
//		System.out.println("");
//		System.out.println("*************** Welcom to Feed **************");
//		
//		System.out.println("");
//		if(allQuestion.isEmpty()) {
//			System.out.println("");
//			System.out.println("Sorry buddy , No one has Posted any Question yet  !!!!!");
//			System.out.println("");
//		}
//		
//		
//		
//		for(Question q : allQuestion) {
//			System.out.println("");
//			q.displayQuestion();
//			for(Answer a: q.getAnswer()) {
//				a.displayAnswer();
//			}
//		}
//		
//	}
//
//	
//	private void addAnswer(User user) {
//		
//		System.out.println("");
//		System.out.println("************** ADD YOUR ANSWER ************");
//		System.out.println("");
//		
//		System.out.print("Enter Question no. : ");
//		int id = sc.nextInt();
//		
//	
//		for(Question q : allQuestion) {
//			
//			if(q.getId() == id) {
//				q.displayQuestion();
//				for(Answer a : q.getAnswer()) {
//					a.displayAnswer();
//				}
//				
//				System.out.print("Add youe answer : ");
//				sc.nextLine();
//				String  text = sc.nextLine();
//				 Answer newAns = new Answer(text, user);
//				
//				System.out.print("Do you want to add answer(Yes/No) ? ");
//				String  res = sc.next().toLowerCase();
//				if("yes".equals(res)) {
//					q.addAnswer(newAns);
//					System.out.println("");
//					System.out.println("Anwer added succesfully ✅✅✅4 .");
//					System.out.println("");
//					return;
//				}
//				else if("no".equals(res)) {
//					return;
//				}
//			}
//			
//		}
//		
//		
//		
//	}
//	
//	
//	private void  search() {
//		System.out.println("");
//		System.out.println("************** Search Question ************");
//		System.out.println("");
//		
//		System.out.print("Search : ");
//		sc.nextLine();
//		String search = sc.nextLine();
//		
//		SearchQuestion service = new SearchQuestion();
//		
//		ArrayList<Question> result =   service.search(allQuestion, search);
//		
//		
//		
//		if(result.isEmpty()) {
//			
//			System.out.println("\nNo relevant questions found.");
//			System.out.println("");
//	        return;
//	        
//		}
//		
//		
//		
//		   System.out.println("\n------ Search Results ------\n");
//		    for (Question q : result) {
//		        q.displayQuestion();
//		        for (Answer a : q.getAnswer()) {
//		            a.displayAnswer();
//		            System.out.println("");
//		        }
//		    }
//		
//		    System.out.println("");
//		
//	}
//	
//	
//	private void drafts(User user) {
//		System.out.println("************** Draft Question ************");
//		System.out.println("");
//		if(user.getMyDrafts().isEmpty()) {
//			 System.out.println("");
//			 System.out.print("You do not have any draft question yet !!!");
//			 System.out.println("");
//
//			
//			 System.out.println("");
//
//		
//			 return;
//		}
//		System.out.println("");
//		
//		for(Question  q : user.getMyDrafts()) {
//			
//			q.displayQuestion();
//			for(Answer a : q.getAnswer()) {
//				a.displayAnswer();
//			}
//			
//		}
//		
//		
//		System.out.println("1.Edit Draft      2.Post Draft  ");
//		String res = sc.next();
//		
//		if(res.equals("1")) {
//			
//			System.out.println("Enter Q no. : ");
//			String res2 = sc.next();
//			
//			
//			
//			for(Question  q : user.getMyDrafts()) {
//				
//				if(Integer.toString(q.getId()).equals(res2)) {
//					
//					q.displayQuestion();
//					  q.getAnswer().clear();
//					System.out.println("");
//					System.out.print("Add youe answer : ");
//					sc.nextLine();
//					String  text = sc.nextLine();
//					 Answer newAns = new Answer(text, user);
//					
//					System.out.print("Do you want to Post Question(Yes/No) ? ");
//					String  res3 = sc.next().toLowerCase();
//					if("yes".equals(res3)) {
//						q.addAnswer(newAns);
//						user.getMyDrafts().remove(q);
//						user.addQuestion(q);
//						System.out.println("");
//						System.out.println("Posted succesfully ✅✅✅ .");
//						System.out.println("");
//					
//						break;
//					}
//					else if("no".equals(res3)) {
//						break;
//					}
//					else {
//						break;
//					}
//					
//				}
//				
//			}
//			
//			
//			
//		}
//		
//	}
//	
//	
//}	
//	
//
//
//	
//
//
