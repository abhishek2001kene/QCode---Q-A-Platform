package Model;


import java.util.*;


public class SearchQuestion {
	
	
	public ArrayList<Question> search(ArrayList<Question> allQuestion, String searchText){
		
		if (searchText == null || searchText.trim().isEmpty()) {
		    return new ArrayList<>();
		}

		
		ArrayList<QuestionScore> temp = new ArrayList<>();
		
		for(Question q : allQuestion) {
			
			 int score = scoreCal(q.getTitle(), searchText );
			 
			 if(score > 0) {
					temp.add(new QuestionScore(q, score));
				}
				
		}
		
		
		
		temp.sort((a, b) -> b.score - a.score);
		
		
		ArrayList<Question> result = new ArrayList<>();
		for(QuestionScore sq : temp) {
			result.add(sq.question);
		}
		
		
		return result;
		
	}
	
	
	 private int scoreCal(String questionTitle, String searchText) {

	        int score = 0;

	        String title = questionTitle.toLowerCase();
	        String search = searchText.toLowerCase();

	       
	        if (title.contains(search)) {
	            score += 5;
	        }

	      
	        if (title.startsWith(search)) {
	            score += 10;
	        }


	        String[] words = search.split(" ");
	        for (String w : words) {
	            if (title.contains(w)) {
	                score += 15;
	            }
	        }

	        return score;
	    }
	
	
	
	
	
	
	
	private static class QuestionScore{
		Question question;
		int score;
		
		
		public QuestionScore(Question question, int score) {
			super();
			this.question = question;
			this.score = score;
		}
		
		
	}
	
}
