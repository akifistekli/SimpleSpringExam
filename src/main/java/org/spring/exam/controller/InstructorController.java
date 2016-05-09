package org.spring.exam.controller;

import java.util.List;

import javax.validation.Valid;

import org.spring.exam.config.Layout;
import org.spring.exam.model.Exam;
import org.spring.exam.model.Question;
import org.spring.exam.model.Result;
import org.spring.exam.model.User;
import org.spring.exam.service.BranchService;
import org.spring.exam.service.ExamService;
import org.spring.exam.service.QuestionService;
import org.spring.exam.service.ResultService;
import org.spring.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/instructor")
@Layout(value = "layouts/instructor")
public class InstructorController {

	@Autowired
	private BranchService branchService;
	
	@Autowired
	private ExamService examService;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ResultService resultService;

	@RequestMapping("")
	public String home(Model model) {
		List<Exam> exams = examService.getAllActive();
		List<Question> questions = questionService.getAllActive();
		List<User> users = userService.getAll();
		model.addAttribute("no_exam", exams.size());
		model.addAttribute("no_question", questions.size());
		model.addAttribute("no_user", users.size());
		return "instructor/home";
	}

	@RequestMapping(value = "/add-exam", method = RequestMethod.GET)
	public String addExamGET(@ModelAttribute(value = "exam") Exam exam, Model model,
			@RequestParam(value = "message", required = false) String message) {
		model.addAttribute("branches", branchService.getAllActive());
		model.addAttribute("message", message);
		return "instructor/add-exam";
	}
	
	@RequestMapping(value = "/add-exam", method = RequestMethod.POST)
	public String addExamPOST(@ModelAttribute @Valid Exam exam, @RequestParam("branch_name") String branch_name,
			Model model, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "instructor/add-exam";
		}

		examService.create(exam, branch_name);
		model.addAttribute("message", "Success!");
		return "redirect:/instructor/add-exam";

	}

	@RequestMapping(value = "/exams", method = RequestMethod.GET)
	public String examsGET(Model model) {
		List<Exam> exams = examService.getAll();
		model.addAttribute("exams", exams);
		return "instructor/exams";
	}
	
	@RequestMapping("/active-exam/{id}")
	public String active_exam(@PathVariable("id") Long id, Model model) {
		examService.changeActiveStatus(id);
		return "redirect:/instructor/exams";
	}
	
	@RequestMapping(value = "/update-exam/{id}", method = RequestMethod.GET)
	public String update_exam_GET(@PathVariable("id") Long id, Model model,
			@RequestParam(value = "message", required = false) String message) {
		Exam exam = examService.getById(id);
		model.addAttribute("branches", branchService.getAllActive());
		model.addAttribute("exam", exam);
		model.addAttribute("message", message);
		return "instructor/update-exam";
	}

	@RequestMapping(value = "/update-exam/{id}", method = RequestMethod.POST)
	public String update_exam_POST(@ModelAttribute Exam exam, @RequestParam("branch_name") String branch_name, Model model) {
		examService.update(exam, branch_name);
		model.addAttribute("message", "Success!");
		return "redirect:/instructor/update-exam/" + String.valueOf(exam.getId());
	}

	@RequestMapping(value = "/add-question", method = RequestMethod.GET)
	public String addQuestionGET(@ModelAttribute(value = "question") Question question, Model model,
			@RequestParam(value = "message", required = false) String message) {
		List<Exam> exams = examService.getAllActive();
		model.addAttribute("exams", exams);
		model.addAttribute("message", message);
		return "instructor/add-question";
	}

	@RequestMapping(value = "/add-question", method = RequestMethod.POST)
	public String addQuestionPOST(@ModelAttribute @Valid Question question, @RequestParam("exam_id") Long exam_id, 
			@RequestParam("correct") String correct, Model model, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "instructor/add-question";
		}
		
		if (correct.equals("SelectCorrectAnswer")) {
			model.addAttribute("message", "Please Select Correct Answer!");
			return "instructor/add-question";
		}
		else {
			questionService.create(question, exam_id);
			model.addAttribute("message", "Success!");
			return "redirect:/instructor/add-question";
		}	

	}

	@RequestMapping(value = "/questions", method = RequestMethod.GET)
	public String questionsGET(Model model) {
		List<Question> questions = questionService.getAll();
		List<Exam> exams = examService.getAllActive();
		model.addAttribute("questions", questions);
		model.addAttribute("exams", exams);
		return "instructor/questions";
	}
	
	@RequestMapping(value = "/questions", method = RequestMethod.POST)
	public String question_by_exam_id(@RequestParam("exam_id") Long exam_id, Model model) {
		if (exam_id == -1) {
			List<Question> questions = questionService.getAll();
			List<Exam> exams = examService.getAllActive();
			model.addAttribute("questions", questions);
			model.addAttribute("exams", exams);
		}
		else {
			List<Question> questions = questionService.getQuestionsByExamId(exam_id);
			List<Exam> exams = examService.getAllActive();
			model.addAttribute("questions", questions);
			model.addAttribute("exams", exams);
		}
		
		return "instructor/questions";
	}
	
	@RequestMapping("/active-question/{id}")
	public String active_question(@PathVariable("id") Long id, Model model) {
		questionService.changeActiveStatus(id);
		return "redirect:/instructor/questions";
	}

	@RequestMapping(value = "/update-question/{id}", method = RequestMethod.GET)
	public String update_question_GET(@PathVariable("id") Long id, Model model,
			@RequestParam(value = "message", required = false) String message) {
		Question question = questionService.getById(id);
		Exam exam_by_id = examService.getById(question.getExam_id());
		model.addAttribute("exam_by_id", exam_by_id);
		model.addAttribute("exams", examService.getAllActive());
		model.addAttribute("question", question);
		model.addAttribute("message", message);
		return "instructor/update-question";
	}

	@RequestMapping(value = "/update-question/{id}", method = RequestMethod.POST)
	public String update_question_POST(@ModelAttribute Question question, @RequestParam("exam_id") Long exam_id, 
			@RequestParam("correct") String correct, Model model) {
				
		if (correct.equals("SelectCorrectAnswer")) {
			model.addAttribute("message", "Please Select Correct Answer!");
			return "redirect:/instructor/update-question/" + String.valueOf(question.getId());
		}
		else {
			questionService.update(question, exam_id);
			model.addAttribute("message", "Success!");
			return "redirect:/instructor/update-question/" + String.valueOf(question.getId());
		}		
	}
	
	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public String reportGET(Model model) {
		List<Exam> exams = examService.getAllActive();
		model.addAttribute("exams", exams);
		return "instructor/report";
	}
	
	@RequestMapping(value = "/report", method = RequestMethod.POST)
	public String reportPOST(@RequestParam("exam_id") Long exam_id, Model model) {
		if (exam_id == -1) {
			List<Exam> exams = examService.getAllActive();
			model.addAttribute("exams", exams);
			model.addAttribute("message", "Please Select an Exam!");
		}
		else {
			List<Exam> exams = examService.getAllActive();
			model.addAttribute("exams", exams);
			
			Exam exam = examService.getById(exam_id);
			
			int no_correct = 0;
			int no_wrong = 0;
			int no_blank = 0;
			List<Result> results = resultService.getResultByExamId(exam_id);
			if (results.size() > 0) {
				for (Result result : results) {
					no_correct += result.getNo_correct();
					no_wrong += result.getNo_wrong();
					no_blank += result.getNo_blank();
				}
				model.addAttribute("exam_name", exam.getName());
				model.addAttribute("no_correct", (float)no_correct / results.size());
				model.addAttribute("no_wrong", (float)no_wrong / results.size());
				model.addAttribute("no_blank", (float)no_blank / results.size());
			}
			else {
				model.addAttribute("message", "No Result!");
			}
		}
		
		return "instructor/report";
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String usersGET(Model model) {
		List<User> users = userService.getAll();
		model.addAttribute("users", users);
		return "instructor/users";
	}
	
	@RequestMapping(value = "/users/report/{id}", method = RequestMethod.GET)
	public String usersReportGET(@PathVariable("id") Long id, Model model) {
		Result result = resultService.getByUserId(id);
		model.addAttribute("result", result);
		return "instructor/users";
	}

	@RequestMapping(value = "/settings", method = RequestMethod.GET)
	public String settingsGET(Model model) {
		return "instructor/settings";
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String profileGET(Model model) {
		return "instructor/profile";
	}

}
