package DB;

import DAO.*;

public class TestFetch {
	  public static void main(String[] args) {

	        QuestionDAO dao = new QuestionDAO();
	        dao.getAllQuestionsWithAnswers();
	    }
}
