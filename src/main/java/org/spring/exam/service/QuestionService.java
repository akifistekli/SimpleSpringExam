package org.spring.exam.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.spring.exam.dao.QuestionDao;
import org.spring.exam.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class QuestionService extends GenericService<Question> {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private ResultService resultService;
	
	public void create(Question question, Long exam_id) {
		question.setExam_id(exam_id);
		this.questionDao.create(question);
	}
	
	public void update(Question question, Long exam_id) {
		question.setExam_id(exam_id);
		this.questionDao.update(question);
	}
	
	public List<Question> getAllActive() {
		return this.questionDao.getAllActive();
	}
	
	public List<Question> getQuestionsByExamId(Long exam_id){
		return this.questionDao.getQuestionsByExamId(exam_id);
	}
	
	public static List<Question> questions = new ArrayList<Question>();
	public HashMap<String, String> user_answers =  new HashMap<String, String>(); 
	public static int no_of_question = 0;
	public static int no_of_correct = 0;
	public static int no_of_wrong = 0;
	public static int no_of_blank = 0;

	public Question getQuestion(Long exam_id, int id){
		questions = getQuestionsByExamId(exam_id);
		user_answers = new HashMap<String, String>(); 
		return questions.get(id);
	}
	
	public Question getNextQuestion(int id, String answer){
		if (id == questions.size()) {
			user_answers.put("q_" + id, answer);
			return null;
		}
		else {
			user_answers.put("q_" + id, answer);
			return questions.get(id);
		}
	}
	
	public Question getPreviousQuestion(int id){
		return questions.get(id);
	}
	
	public String compareAnswer(){
		String result = "";
		for (int i = 1; i <= user_answers.size(); i++) {
			Question question = questions.get(i - 1);
			if (user_answers.get("q_" + i).equals(question.getCorrect())) {
				result = "correct";
				no_of_correct++;
			}
			else if(user_answers.get("q_" + i).equals("Blank")) {
				no_of_blank++;
			}
			else {
				result = "wrong";
				no_of_wrong++;
			}
		}
		no_of_question = questions.size(); 
//		no_of_blank = no_of_question - no_of_correct - no_of_wrong;
		resultService.create(no_of_question, no_of_correct, no_of_wrong, no_of_blank);
		return result;
	}
	
}
