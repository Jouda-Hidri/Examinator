package examinator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExamController {
	
	@GetMapping("/hello")
	public String hello(Model model) {
		

		model.addAttribute("examId", "1");

		return "welcome";
	}
	
	@GetMapping("/exam/${examId}")
	public String getExam(Model model) {
		

		model.addAttribute("examId", "1");

		return "exam";
	}

}
