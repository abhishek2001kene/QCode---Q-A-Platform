package App;

import java.util.*;

import DAO.AnswerDAO;
import DAO.QuestionDAO;
import Model.Answer;
import Model.Question;
import Model.User;


import Service.UserService;
import Service.QuestionService;
import Service.AnswerService;

public class Qcode {

    private Scanner sc = new Scanner(System.in);


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



    public void createAccount() {

        System.out.println("\n--- Create Account ---");
        sc.nextLine();

        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.next();

        System.out.print("Enter Password: ");
        String password = sc.next();

   
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


        UserService userService = new UserService();
        User user = userService.login(email, password);

        if (user != null) {
            homepage(user);
        } else {
            System.out.println("Invalid credentials ❌");
        }
    }



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



   private void createQuestion(User user) {

    sc.nextLine();
    System.out.print("Enter Question: ");
    String title = sc.nextLine();

    Question newQuestion = new Question(title, user);


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

         int qid = qdao.saveQuestionReturnId(newQuestion, user.getId(), false);

      
        if (answerText != null && !answerText.isEmpty()) {
            adao.saveAnswer(new Answer(answerText, user), user.getId(), qid);
        }

        System.out.println("Question posted successfully ✅");

    } 
    else if (choice.equals("2")) {


        int qid = qdao.saveQuestionReturnId(newQuestion, user.getId(), true);

    
        if (answerText != null && !answerText.isEmpty()) {
            adao.saveAnswer(new Answer(answerText, user), user.getId(), qid);
        }

        System.out.println("Saved as draft ✅");

    } 
    else {
        System.out.println("Invalid option");
    }
}


    private void goToFeed() {

        System.out.println("\n--- FEED ---");

     
        QuestionService qs = new QuestionService();
        qs.showFeed();
    }



    private void viewMyQuestions(User user) {

        System.out.println("\n--- My Questions ---");

        QuestionService qs = new QuestionService();
        qs.showMyQuestions(user.getId());
    }

 

    private void addAnswer(User user) {

        System.out.println("\n--- ADD ANSWER ---");

        System.out.print("Enter Question ID: ");
        int qid = sc.nextInt();

        sc.nextLine();
        System.out.print("Enter your answer: ");
        String text = sc.nextLine();


        AnswerService as = new AnswerService();
        as.addOrUpdateAnswer(text, user.getId(), qid);
    }



    private void search() {

        System.out.println("\n--- Search Question ---");

        sc.nextLine();
        System.out.print("Search: ");
        String search = sc.nextLine();


        QuestionService qs = new QuestionService();
        qs.search(search);
    }



    private void drafts(User user) {

        System.out.println("\n--- YOUR DRAFTS ---");

      
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
        qdao.getQuestionsByUser(user.getId());  

        System.out.print("\nEnter Question ID to update: ");
        int qid = sc.nextInt();

        sc.nextLine();
        System.out.print("Enter new title: ");
        String newTitle = sc.nextLine();

   
        if (newTitle.trim().isEmpty()) {
            System.out.println("❌ Title cannot be empty");
            return;
        }

        qdao.updateQuestion(qid, user.getId(), newTitle);
    }
    
    
    private void deleteQuestionUI(User user) {

        QuestionDAO qdao = new QuestionDAO();

        System.out.println("\n--- YOUR QUESTIONS ---");
        qdao.getQuestionsByUser(user.getId());  

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

