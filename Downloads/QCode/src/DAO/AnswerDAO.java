package DAO;

import java.sql.*;

import DB.DBConnection;
import Model.Answer;

public class AnswerDAO {

    // ✅ Save Answer
    public void saveAnswer(Answer a, int userId, int questionId) {

        String sql = "INSERT INTO answers(answer, user_id, question_id) VALUES(?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, a.getAnswer());
            ps.setInt(2, userId);
            ps.setInt(3, questionId);

            ps.executeUpdate();

            System.out.println("Answer saved in DB ✅");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void upsertAnswer(String text, int userId, int qid) {

	    String check = "SELECT id FROM answers WHERE question_id = ? AND user_id = ?";
	    String insert = "INSERT INTO answers(answer, user_id, question_id) VALUES(?,?,?)";
	    String update = "UPDATE answers SET answer = ? WHERE question_id = ? AND user_id = ?";

	    try (Connection con = DBConnection.getConnection()) {

	        PreparedStatement ps = con.prepareStatement(check);
	        ps.setInt(1, qid);
	        ps.setInt(2, userId);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            PreparedStatement ups = con.prepareStatement(update);
	            ups.setString(1, text);
	            ups.setInt(2, qid);
	            ups.setInt(3, userId);
	            ups.executeUpdate();
	        } else {
	            PreparedStatement ins = con.prepareStatement(insert);
	            ins.setString(1, text);
	            ins.setInt(2, userId);
	            ins.setInt(3, qid);
	            ins.executeUpdate();
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}