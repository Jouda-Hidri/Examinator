package examinator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import examinator.entity.Exam;

@Controller
public class ExamController {

	@GetMapping("/hello")
	public String hello(Model model) {

		model.addAttribute("examId", "1");

		return "welcome";
	}

	@GetMapping("/exam/{id}")
	public String getExam(Model model) {
		Exam exam = new Exam();
		exam.setTitle("example title");

		model.addAttribute("exam", exam);

		return "exam";
	}

}
