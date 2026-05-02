package Service;

import DAO.AnswerDAO;

public class AnswerService {

    private AnswerDAO adao = new AnswerDAO();

    public void addOrUpdateAnswer(String text, int userId, int qid) {
        adao.upsertAnswer(text, userId, qid);
    }
}