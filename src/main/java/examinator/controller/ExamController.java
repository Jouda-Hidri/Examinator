package examinator.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import examinator.entity.Answer;
import examinator.entity.Evaluation;
import examinator.entity.Exam;
import examinator.entity.Question;
import examinator.entity.Student;
import examinator.service.ExamService;
import examinator.service.StudentService;

@Controller
public class ExamController {
	@Autowired
	StudentService studentService;
	
	@Autowired
	ExamService examService;
	
	@GetMapping("/")
	public String hello(ModelMap model) {
		studentService.creatIfDoesntExist();
		return "welcome";
	}
	
	@PostMapping("/login")
	public String login(HttpServletRequest request, ModelMap model,
			@ModelAttribute("username") String username, @ModelAttribute("username") String password) {
		Student student = studentService.getStudent(username, password);
		if(student == null) {
			model.addAttribute("message", "Username or password is uncorrect.");
			return "error";
		}
		request.getSession().setAttribute("student", student);
		return "welcome";
	}
	
	@GetMapping("/list")
	public String getList(ModelMap model) {
		List<Exam> listExams = examService.getListExams();
		model.put("listExams", listExams);
		List<Evaluation> listEvaluations = examService.getListEvaluations();
		model.addAttribute("listEvaluations", listEvaluations);
		return "list";
	}

	@GetMapping("/first/{id}")
	public String getFirstQuestion(HttpServletRequest request, ModelMap model,
			@PathVariable(value = "id") long exam_id) {
		Question question = examService.getFirstQuestionByExamId(exam_id);
		Student student = (Student) request.getSession().getAttribute("student");
		if(student == null) {
			model.addAttribute("message", "You have been disconnected!");
			return "error";
		}
		Evaluation evaluation = examService.createNewEvaluation(student);
		request.getSession().setAttribute("evaluation", evaluation);
		request.getSession().setAttribute("exam_id", exam_id);
		model.addAttribute("question", question);

		return "question";
	}

	@PostMapping("/next/{current_question_id}")
	public String getNextQuestion(HttpServletRequest request, ModelMap model,
			@PathVariable(value = "current_question_id") long current_question_id,
			@ModelAttribute("choice_id") String choice_id_str) {
		
		if(choice_id_str.equals("")) {
			String message = "No choice was selected.";
			model.addAttribute("message", message);
			return "error";
		}

		long choice_id = Long.parseLong(choice_id_str, 10);
		
		
		Evaluation evaluation = (Evaluation) request.getSession().getAttribute("evaluation");
		if(evaluation == null) {
			model.addAttribute("message", "You have been disconnected!");
			return "error";
		}
		examService.save(choice_id, evaluation);

		if(request.getSession().getAttribute("exam_id") == null) {
			model.addAttribute("message", "You have been disconnected!");
			return "error";
		}
		long exam_id = (long) request.getSession().getAttribute("exam_id");

		Question question = examService.findNextQuestionByCurrentExamIdAndQuestionId(exam_id, current_question_id);
		if (question == null) {
			examService.finish(evaluation);
		}
		model.addAttribute("question", question);

		return "question";
	}

	@GetMapping("/evaluation/{evaluation_id}")
	public String getEvaluation(HttpServletRequest request, ModelMap model, @PathVariable(value = "evaluation_id") long evaluation_id) {
		Evaluation evaluation = examService.findByID(evaluation_id);
		request.getSession().setAttribute("evaluation", evaluation);
		if (evaluation == null || evaluation.getAnswers().isEmpty()) {
			String message = "This evalution does not exist.";
			model.addAttribute("message", message);
			return "error";
		}
		if (evaluation.isFinished()) {
			model.addAttribute("answerList", evaluation.getAnswers());
			return "result";
		}
		Question current_question = evaluation.getLastAnswer().getChoice().getQuestion();
		long exam_id = current_question.getExam().getId();
		long question_id = current_question.getId();
		Question question = examService.findNextQuestionByCurrentExamIdAndQuestionId(exam_id, question_id);
		if (question == null) {
			examService.finish(evaluation);
		}
		model.addAttribute("question", question);
		return "question";
	}

	@GetMapping("/result")
	public String getResult(HttpServletRequest request, ModelMap model) {
		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		entityManager.getTransaction().begin();

		Evaluation evaluation = (Evaluation) request.getSession().getAttribute("evaluation");
		List<Answer> answerList = examService.findByEvaluation(evaluation);

		model.put("answerList", answerList);

		return "result";
	}
}
