package examinator.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import examinator.dao.ExamDao;
import examinator.entity.Choice;
import examinator.entity.Exam;
import examinator.entity.Question;

@Controller
public class AdminController {
	ExamDao examDao = new ExamDao();

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
		Exam exam = new Exam();
		exam.setTitle(title);
		request.getSession().setAttribute("exam", exam);
		model.addAttribute("exam", exam);
		return "create";
	}

	@PostMapping("/add")
	public String addQuestion(HttpServletRequest request, ModelMap model, @ModelAttribute("title") String title,
			@ModelAttribute("choice_1") String choice_1, @ModelAttribute("choice_2") String choice_2,
			@ModelAttribute("choice_3") String choice_3, @ModelAttribute("choice_id") String choice_id) {
		Question question = new Question();
		question.setTitle(title);
		Choice c1 = new Choice();
		Choice c2 = new Choice();
		Choice c3 = new Choice();
		c1.setTitle(choice_1);
		c2.setTitle(choice_2);
		c3.setTitle(choice_3);
		switch (choice_id) {
		case "choice_1":
			c1.setCorrect(true);
			break;
		case "choice_2":
			c2.setCorrect(true);
			break;
		case "choice_3":
			c3.setCorrect(true);
			break;
		}
		question.addChoice(c1);
		question.addChoice(c2);
		question.addChoice(c3);
		Exam exam = (Exam) request.getSession().getAttribute("exam");
		exam.addQuestion(question);
		model.addAttribute("exam", exam);
		return "create";
	}

	@PostMapping("/save")
	public String saveExam(HttpServletRequest request, ModelMap model) {
		Exam exam = (Exam) request.getSession().getAttribute("exam");
		examDao.save(exam);
		return "welcome";
	}

}
