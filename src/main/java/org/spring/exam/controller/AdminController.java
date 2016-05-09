package org.spring.exam.controller;

import java.util.List;

import javax.validation.Valid;

import org.spring.exam.config.Layout;
import org.spring.exam.model.Branch;
import org.spring.exam.model.Instructor;
import org.spring.exam.service.BranchService;
import org.spring.exam.service.InstructorService;
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
@RequestMapping("/admin")
@Layout(value = "layouts/admin")
public class AdminController {
	
	@Autowired
	private BranchService branchService;
	
	@Autowired
	private InstructorService instructorService;

	@RequestMapping("")
	public String home(Model model) {
//		ModelAndView mav = new ModelAndView("layouts/admin");
//	    mav.addObject("message", "Hello from controller!");
		return "admin/home";
	}

	@RequestMapping(value = "/add-branch", method = RequestMethod.GET)
	public String addbranchGET(@ModelAttribute(value = "branch") Branch branch, Model model,
			@RequestParam(value = "message", required = false) String message) {
		model.addAttribute("message", message);
		return "admin/add-branch";
	}

	@RequestMapping(value = "/add-branch", method = RequestMethod.POST)
	public String addbranchPOST(@ModelAttribute @Valid Branch branch, Model model, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "admin/add-branch";
		}

		branchService.create(branch);
		model.addAttribute("message", "Success!");
		return "redirect:/admin/add-branch";
	}

	@RequestMapping(value = "/branches", method = RequestMethod.GET)
	public String branches(Model model) {
		List<Branch> branchs = branchService.getAll();
		model.addAttribute("branchs", branchs);
		return "admin/branches";
	}

	@RequestMapping(value = "/add-instructor", method = RequestMethod.GET)
	public String addinstructorGET(@ModelAttribute(value = "instructor") Instructor instructor, Model model,
			@RequestParam(value = "message", required = false) String message) {
		model.addAttribute("branches", branchService.getAllActive());
		model.addAttribute("message", message);
		return "admin/add-instructor";
	}

	@RequestMapping(value = "/add-instructor", method = RequestMethod.POST)
	public String addinstructorPOST(@ModelAttribute @Valid Instructor instructor,
			@RequestParam("password_confirm") String password_confirm, @RequestParam("branch_name") String branch_name,
			Model model, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "admin/add-instructor";
		}

		if (!password_confirm.equals(instructor.getPassword())) {
			//
		}

		instructorService.create(instructor, branch_name);
		model.addAttribute("message", "Success!");

		return "redirect:/admin/add-instructor";
	}

	@RequestMapping(value = "/instructors", method = RequestMethod.GET)
	public String instructorsGET(Model model) {
		List<Instructor> instructors = instructorService.getAll();
		model.addAttribute("instructors", instructors);
		return "admin/instructors";
	}

	@RequestMapping(value = "/update-branch/{id}", method = RequestMethod.GET)
	public String update_branch_GET(@PathVariable("id") Long id, Model model,
			@RequestParam(value = "message", required = false) String message) {
		Branch branch = branchService.getById(id);
		model.addAttribute("branch", branch);
		model.addAttribute("message", message);
		return "admin/update-branch";
	}

	@RequestMapping(value = "/update-branch/{id}", method = RequestMethod.POST)
	public String update_branch_POST(@ModelAttribute Branch branch, Model model) {
		branchService.update(branch);
		model.addAttribute("message", "Success!");
		return "redirect:/admin/update-branch/" + String.valueOf(branch.getId());
	}
	
	@RequestMapping(value = "/update-instructor/{id}", method = RequestMethod.GET)
	public String update_instructor_GET(@PathVariable("id") Long id, Model model,
			@RequestParam(value = "message", required = false) String message) {
		Instructor instructor = instructorService.getById(id);
		model.addAttribute("branches", branchService.getAllActive());
		model.addAttribute("instructor", instructor);
		model.addAttribute("message", message);
		return "admin/update-instructor";
	}

	@RequestMapping(value = "/update-instructor/{id}", method = RequestMethod.POST)
	public String update_instructor_POST(@ModelAttribute Instructor instructor, @RequestParam("branch_name") String branch_name, Model model) {
		instructorService.update(instructor,branch_name);
		model.addAttribute("message", "Success!");
		return "redirect:/admin/update-instructor/" + String.valueOf(instructor.getId());
	}
	
	@RequestMapping("/active-branch/{id}")
	public String activeBranch(@PathVariable("id") Long id, Model model) {
		branchService.changeActiveStatus(id);
		return "redirect:/admin/branches";
	}
	
	@RequestMapping("/active-instructor/{id}")
	public String activeInstructor(@PathVariable("id") Long id, Model model) {
		instructorService.changeActiveStatus(id);
		return "redirect:/admin/instructors";
	}


	@RequestMapping(value = "/settings")
	public String settingsGET(Model model) {
		return "admin/settings";
	}

	@RequestMapping("/profile")
	public String profile(Model model) {
		return "admin/profile";
	}
}
