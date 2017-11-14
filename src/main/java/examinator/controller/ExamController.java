package examinator.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import examinator.dao.AnswerDao;
import examinator.dao.ChoiceDao;
import examinator.dao.EvaluationDao;
import examinator.dao.ExamDao;
import examinator.dao.QuestionDao;
import examinator.dao.StudentDao;
import examinator.entity.Answer;
import examinator.entity.Evaluation;
import examinator.entity.Exam;
import examinator.entity.Question;
import examinator.entity.Student;
import examinator.test.TestInsert;

@Controller
public class ExamController {
	ExamDao examDao = new ExamDao();
	QuestionDao questionDao = new QuestionDao();
	EvaluationDao evaluationDao = new EvaluationDao();
	ChoiceDao choiceDao = new ChoiceDao();
	AnswerDao answerDao = new AnswerDao();
	StudentDao studentDao = new StudentDao();

	@GetMapping("/")
	public String hello(ModelMap model) {
		//in case the database is empty add a test student
		Student student = studentDao.findAll();
		if(student == null) {
			TestInsert.createStudent();
		}
		return "welcome";
	}
	
	@PostMapping("/login")
	public String login(HttpServletRequest request, ModelMap model,
			@ModelAttribute("username") String username, @ModelAttribute("username") String password) {
		Student student = studentDao.findByUsernameAndPassword(username, password);
		if(student == null) {
			model.addAttribute("message", "Username or password is uncorrect.");
			return "error";
		}
		request.getSession().setAttribute("student", student);
		return "welcome";
	}
	
	@GetMapping("/list")
	public String getList(ModelMap model) {
		List<Exam> listExams = examDao.findAll();
		//in case the database is empty, add a test exam
		if(listExams.isEmpty()) {
			listExams = new ArrayList<Exam>();
			listExams.add(TestInsert.createOneExamWithfiveQuestions());
		}
		List<Evaluation> listEvaluations = evaluationDao.findAll();
		model.put("listExams", listExams);
		model.addAttribute("listEvaluations", listEvaluations);
		return "list";
	}

	@GetMapping("/first/{id}")
	public String getFirstQuestion(HttpServletRequest request, ModelMap model,
			@PathVariable(value = "id") String exam_id) {
		Question question = questionDao.findFirstQuestionByExamId(exam_id);
		Student student = (Student) request.getSession().getAttribute("student");
		if(student == null) {
			model.addAttribute("message", "You have been disconnected!");
			return "error";
		}
		Evaluation evaluation = evaluationDao.createNewEvaluation(student);
		request.getSession().setAttribute("evaluation", evaluation);
		model.addAttribute("question", question);

		return "question";
	}

	@PostMapping("/next/{current_question_id}")
	public String getNextQuestion(HttpServletRequest request, ModelMap model,
			@PathVariable(value = "current_question_id") String current_question_id,
			@ModelAttribute("choice_id") String choice_id) {
		
		if(choice_id.equals("")) {
			String message = "No choice was selected.";
			model.addAttribute("message", message);
			return "error";
		}

		Evaluation evaluation = (Evaluation) request.getSession().getAttribute("evaluation");
		Answer answer = answerDao.save(choice_id, evaluation);

		Question question = questionDao.findNextQuestionByCurrentQuestionId(current_question_id);
		if (question == null) {
			evaluationDao.finish(evaluation);
		}
		model.addAttribute("question", question);

		return "question";
	}

	@GetMapping("/evaluation/{evaluation_id}")
	public String getEvaluation(HttpServletRequest request, ModelMap model, @PathVariable(value = "evaluation_id") String evaluation_id) {
		Evaluation evaluation = evaluationDao.findByID(evaluation_id);
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
		Question question = questionDao
				.findNextQuestionByCurrentQuestionId(evaluation.getLastAnswer().getChoice().getQuestion().getId() + "");
		if (question == null) {
			evaluationDao.finish(evaluation);
		}
		model.addAttribute("question", question);
		return "question";
	}

	@GetMapping("/result")
	public String getResult(HttpServletRequest request, ModelMap model) {
		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		entityManager.getTransaction().begin();

		Evaluation evaluation = (Evaluation) request.getSession().getAttribute("evaluation");
		List<Answer> answerList = answerDao.findByEvaluation(evaluation);

		model.put("answerList", answerList);

		return "result";
	}
}
