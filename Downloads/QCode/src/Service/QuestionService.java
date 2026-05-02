package Service;

import DAO.QuestionDAO;
import Model.Question;
import Model.User;

public class QuestionService {

    private QuestionDAO qdao = new QuestionDAO();

    public void createQuestion(String title, User user, boolean isDraft) {
        Question q = new Question(title, user);
        qdao.saveQuestionReturnId(q, user.getId(), isDraft);
    }

    public void showFeed() {
        qdao.getAllQuestionsWithAnswers();
    }

    public void showMyQuestions(int userId) {
        qdao.getQuestionsByUser(userId);
    }

    public void showDrafts(int userId) {
        qdao.getDraftsByUser(userId);
    }

    public void publishDraft(int qid) {
        qdao.publishDraft(qid);
    }

    public void updateDraft(int qid, String title) {
        qdao.updateQuestionTitle(qid, title);
    }

    public void search(String text) {
        qdao.searchQuestions(text);
    }
}