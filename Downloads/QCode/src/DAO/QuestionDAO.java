package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DB.DBConnection;
import Model.Question;
import Model.User;

public class QuestionDAO {

    // ✅ SAVE QUESTION
public int saveQuestionReturnId(Question q, int userId, boolean isDraft) {

    String sql = "INSERT INTO questions(title, user_id, is_draft) VALUES(?, ?, ?)";

    try {
        Connection con = DBConnection.getConnection();

        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, q.getTitle());
        ps.setInt(2, userId);
        ps.setBoolean(3, isDraft);

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();

        if (rs.next()) {
            return rs.getInt(1); // ✅ RETURN ID
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return -1;
}


    // ✅ FETCH ALL QUESTIONS (with USER NAME using JOIN)
  public void getAllQuestionsWithAnswers() {

    String sql = """
        SELECT q.id AS qid, q.title, u.name AS uname,
               a.answer, u2.name AS answerUser
        FROM questions q
        JOIN users u ON q.user_id = u.id
        LEFT JOIN answers a ON q.id = a.question_id
        LEFT JOIN users u2 ON a.user_id = u2.id
        ORDER BY q.id
    """;

    try (Connection con = DBConnection.getConnection();
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(sql)) {

        int currentQid = -1;

        while (rs.next()) {

            int qid = rs.getInt("qid");
            String title = rs.getString("title");
            String uname = rs.getString("uname");

            // ✅ Print question ONLY once
            if (qid != currentQid) {
                System.out.println("\n" + qid + "Q (" + uname + ") " + title);
                currentQid = qid;
            }

            // ✅ Print answer if exists
            String answer = rs.getString("answer");
            String answerUser = rs.getString("answerUser");

            if (answer != null) {
                System.out.println("   --> (" + answerUser + ") " + answer);
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}
  
  
  
  public void searchQuestions(String searchText) {

	    String sql = """
	        SELECT q.id, q.title, u.name
	        FROM questions q
	        JOIN users u ON q.user_id = u.id
	        WHERE LOWER(q.title) LIKE ?
	    """;

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, "%" + searchText.toLowerCase() + "%");

	        ResultSet rs = ps.executeQuery();

	        boolean found = false;

	        while (rs.next()) {
	            found = true;
	            int id = rs.getInt("id");
	            String title = rs.getString("title");
	            String user = rs.getString("name");

	            System.out.println(id + "Q (" + user + ") " + title);
	        }

	        if (!found) {
	            System.out.println("No results found.");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
  
  public void publishDraft(int qid) {

	    String sql = "UPDATE questions SET is_draft = false WHERE id = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, qid);
	        ps.executeUpdate();

	        System.out.println("Draft published ✅");

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
  
  public void updateQuestionTitle(int qid, String title) {

	    String sql = "UPDATE questions SET title = ? WHERE id = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, title);
	        ps.setInt(2, qid);

	        ps.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
  
  public void getDraftsByUser(int userId) {

	    String sql = "SELECT id, title FROM questions WHERE user_id = ? AND is_draft = true";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, userId);

	        ResultSet rs = ps.executeQuery();

	        boolean found = false;

	        while (rs.next()) {
	            found = true;
	            int id = rs.getInt("id");
	            String title = rs.getString("title");

	            System.out.println(id + "Q (Draft) " + title);
	        }

	        if (!found) {
	            System.out.println("No drafts found.");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
  
  
  public void updateQuestion(int qid, int userId, String newTitle) {

	    String sql = "UPDATE questions SET title = ? WHERE id = ? AND user_id = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, newTitle);
	        ps.setInt(2, qid);
	        ps.setInt(3, userId);

	        int rows = ps.executeUpdate();

	        if (rows > 0) {
	            System.out.println("✅ Question updated");
	        } else {
	            System.out.println("❌ Update failed (Invalid ID or not your question)");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
  

  public void deleteQuestion(int qid, int userId) {

	    String sql = "DELETE FROM questions WHERE id = ? AND user_id = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, qid);
	        ps.setInt(2, userId);

	        int rows = ps.executeUpdate();

	        if (rows > 0) {
	            System.out.println("✅ Question deleted");
	        } else {
	            System.out.println("❌ Delete failed (Invalid ID or not your question)");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
  
  
    // ✅ FETCH QUESTIONS BY USER (for "My Questions")
   public void getQuestionsByUser(int userId) {

    String sql = "SELECT * FROM questions WHERE user_id = ? AND is_draft = false";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();

        boolean found = false;

        while (rs.next()) {
            found = true;
            int id = rs.getInt("id");
            String title = rs.getString("title");

            System.out.println(id + "Q " + title);
        }

        if (!found) {
            System.out.println("No questions found.");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}
}