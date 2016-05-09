package org.spring.exam.controller;

import org.spring.exam.config.Layout;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@Layout(value = "layouts/public")
public class HomeController {

	@RequestMapping("")
	public String home(Model model) {
		return "index";
	}

}
