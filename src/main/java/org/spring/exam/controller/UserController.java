package org.spring.exam.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.spring.exam.config.Layout;
import org.spring.exam.model.Exam;
import org.spring.exam.model.Question;
import org.spring.exam.model.User;
import org.spring.exam.service.BranchService;
import org.spring.exam.service.ExamService;
import org.spring.exam.service.QuestionService;
import org.spring.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
@Layout(value = "layouts/public")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private BranchService branchService;
	
	@Autowired
	private ExamService examService;
	
	@Autowired
	private QuestionService questionService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginGET(Model model) {
		return "user/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginPOST(Model model) {
		return "user/login";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerGET(@ModelAttribute(value="user") User user, Model model, @RequestParam(value="message", required=false) String message) {
		model.addAttribute("message", message);
		return "user/register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerPOST(@ModelAttribute @Valid User user,
			@RequestParam("password_confirm") String password_confirm, Model model) {

		if (!password_confirm.equals(user.getPassword())) {
			//
		}

		userService.create(user);
		model.addAttribute("message", "Success!");

		return "redirect:/user/register";

	}
	
	@RequestMapping(value = "/exam", method = RequestMethod.GET)
	public String examGET(@ModelAttribute(value="exam") Exam exam, @RequestParam(value="exam_id", required=false) Long exam_id, Model model){
		model.addAttribute("branches", branchService.getAllActive());
		model.addAttribute("exams", examService.getAllActive());
		model.addAttribute("display_questions", "display:none;");
		return "user/exam";
	}
	
	Integer id = null;
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/exam", method = RequestMethod.POST)
	public String examPOST(@RequestParam(value="exam_id", required=false) Long exam_id, Model model){
		id = 0;		
		Question question = questionService.getQuestion(exam_id, id);
		model.addAttribute("questions", question);			
		model.addAttribute("display", "display:none;");
		model.addAttribute("display_questions", "display:block;");
		model.addAttribute("btn_previous", "btn btn-default disabled");
		model.addAttribute("btn_next", "Next");
		model.addAttribute("btn_next_action", "/user/exam/next");
		model.addAttribute("question_number", id + 1);

		Exam exam = examService.getById(exam_id);
		model.addAttribute("branch", exam.getBranch());
		model.addAttribute("exam", exam.getName());
		model.addAttribute("time", "00:00");		
		model.addAttribute("date", new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date()));
		model.addAttribute("no_question", questionService.questions.size());
		return "user/exam";
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/exam/next", method = RequestMethod.POST)
	public String questionNextGET(@RequestParam(value="optradio", required=false, defaultValue = "Blank") String optradio, Model model){
		id++;
		if (id == (questionService.questions.size() - 1)) {
			model.addAttribute("btn_next", "Finish");
			model.addAttribute("btn_next_action", "/user/exam/result");
		}
		else {
			model.addAttribute("btn_next", "Next");
			model.addAttribute("btn_next_action", "/user/exam/next");
		}
//		if (optradio.equals("Blank")) {
//			model.addAttribute("blank_warning", "You did not answer this question! Are you sure to skip this?");
//			return "user/exam";
//		}
//		else {
			model.addAttribute("questions", questionService.getNextQuestion(id, optradio));
			model.addAttribute("display", "display:none;");
			model.addAttribute("display_questions", "display:block;");
			model.addAttribute("btn_previous", "btn btn-default");
			model.addAttribute("question_number", id + 1);
			
			Question question = questionService.questions.get(0);
			Exam exam = examService.getById(question.getExam_id());
			model.addAttribute("branch", exam.getBranch());
			model.addAttribute("exam", exam.getName());
			model.addAttribute("time", "00:00");		
			model.addAttribute("date", new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date()));
			model.addAttribute("no_question", questionService.questions.size());
			return "user/exam";
//		}		
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/exam/previous", method = RequestMethod.GET)
	public String questionPreviousGET(Model model){
		id--;
		if (id == 0) {
			model.addAttribute("btn_previous", "btn btn-default disabled");
		}
		else {
			model.addAttribute("btn_previous", "btn btn-default");
		}
		model.addAttribute("questions", questionService.getPreviousQuestion(id));
		model.addAttribute("display", "display:none;");
		model.addAttribute("display_questions", "display:block;");
		model.addAttribute("btn_next", "Next");
		model.addAttribute("btn_next_action", "/user/exam/next");
		model.addAttribute("question_number", id + 1);
		
		Question question = questionService.questions.get(0);
		Exam exam = examService.getById(question.getExam_id());
		model.addAttribute("branch", exam.getBranch());
		model.addAttribute("exam", exam.getName());
		model.addAttribute("time", "00:00");		
		model.addAttribute("date", new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date()));
		model.addAttribute("no_question", questionService.questions.size());
		return "user/exam";
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/exam/result", method = RequestMethod.POST)
	public String examFinish(@RequestParam(value="optradio", required=false, defaultValue = "Blank") String optradio, Model model){
		id++;
		questionService.getNextQuestion(id, optradio);
		questionService.compareAnswer();
		model.addAttribute("q", questionService.no_of_question);
		model.addAttribute("c", questionService.no_of_correct);
		model.addAttribute("w", questionService.no_of_wrong);
		model.addAttribute("b", questionService.no_of_blank);
//		@SuppressWarnings("static-access")
//		List<Question> questions = questionService.questions;
//		HashMap<String, String> answers = questionService.user_answers;
//		model.addAttribute("questions", questions);
//		model.addAttribute("answers", answers);
		return "user/result";
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/exam/cancel", method = RequestMethod.GET)
	public String examCancelGET(Model model){
		id = null;
		questionService.questions = null; 
		questionService.user_answers = null;
		model.addAttribute("branches", branchService.getAllActive());
		model.addAttribute("exams", examService.getAllActive());
		model.addAttribute("display_questions", "display:none;");
		return "redirect:/user/exam";
	}
}
