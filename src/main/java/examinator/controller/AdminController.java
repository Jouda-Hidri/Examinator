package examinator.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import examinator.entity.Exam;
import examinator.entity.Question;
import examinator.service.ExamService;

@Controller
public class AdminController {
	
	@Autowired
	ExamService examService;

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView hello(ModelAndView modelAndView) {
		return new ModelAndView("redirect:/exam");
	}

	@GetMapping("/exam")
	public String showForm() {
		return "create";
	}
	
	@PostMapping("/create")
	public String createExam(HttpServletRequest request, ModelMap model, @ModelAttribute("title") String title) {
		Exam exam = examService.createExam(title);
		if (exam == null) {
			String message = "An exam cannot have an empty title!";
			model.addAttribute("message", message);
			return "error";
		}
		request.getSession().setAttribute("exam", exam);
		model.addAttribute("exam", exam);
		return "create";
	}

	@PostMapping("/add")
	public String addQuestion(HttpServletRequest request, ModelMap model, @ModelAttribute("title") String title,
			@ModelAttribute("choice_1") String choice_1, @ModelAttribute("choice_2") String choice_2,
			@ModelAttribute("choice_3") String choice_3, @ModelAttribute("choice_id") String choice_id) {
		Question question = examService.addQuestion(title, choice_1, choice_2, choice_3, choice_id);
		if(question == null) {
			String message = "The question and the choices cannot be empty!";
			model.addAttribute("message", message);
			return "error";
		}
		Exam exam = (Exam) request.getSession().getAttribute("exam");
		exam.addQuestion(question);
		model.addAttribute("exam", exam);
		return "create";
	}

	@PostMapping("/save")
	public String saveExam(HttpServletRequest request, ModelMap model) {
		Exam exam = (Exam) request.getSession().getAttribute("exam");
		examService.save(exam);
		request.getSession().removeAttribute("exam");
		return "welcome";
	}

}
