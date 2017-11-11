package examinator.controller;

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
import examinator.entity.Answer;
import examinator.entity.Evaluation;
import examinator.entity.Exam;
import examinator.entity.Question;
import examinator.test.TestInsert;

@Controller
public class ExamController {
	ExamDao examDao = new ExamDao();
	QuestionDao questionDao = new QuestionDao();
	EvaluationDao evaluationDao = new EvaluationDao();
	ChoiceDao choiceDao = new ChoiceDao();
	AnswerDao answerDao = new AnswerDao();

	@GetMapping("/")
	public String hello(ModelMap model) {
		List<Exam> listExams = examDao.findAll();
		List<Evaluation> listEvaluations = evaluationDao.findAll();
		model.put("listExams", listExams);
		model.addAttribute("listEvaluations", listEvaluations);
		return "welcome";
	}

	@GetMapping("/init")
	public String initExamAndQuestions(ModelMap model) {
		TestInsert.createOneExamWithfiveQuestions();
		return "welcome";
	}

	@GetMapping("/first/{id}")
	public String getFirstQuestion(HttpServletRequest request, ModelMap model,
			@PathVariable(value = "id") String exam_id) {
		Question question = questionDao.findFirstQuestionByExamId(exam_id);
		Evaluation evaluation = evaluationDao.createNewEvaluation();
		model.addAttribute("question", question);
		request.getSession().setAttribute("evaluation", evaluation);

		return "question";
	}

	@PostMapping("/next/{current_question_id}")
	public String getNextQuestion(HttpServletRequest request, ModelMap model,
			@PathVariable(value = "current_question_id") String current_question_id, @ModelAttribute("choice_id") String choice_id) {
		
		Evaluation evaluation = (Evaluation) request.getSession().getAttribute("evaluation");
		Answer answer = answerDao.save(choice_id, evaluation);
		
		Question question = questionDao.findNextQuestionByCurrentQuestionId(current_question_id);
		if (question == null) {
			model.addAttribute("question", "");
		} else {
			model.addAttribute("question", question);
		}
		model.addAttribute("previous_question_id", current_question_id);

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
	
	@GetMapping("/evaluation/{evaluation_id}")
	public String getEvaluation(ModelMap model, @PathVariable(value = "evaluation_id") String evaluation_id) {
		Evaluation evaluation = evaluationDao.findByID(evaluation_id);
		if(evaluation == null) {
			model.addAttribute("answerList", null);
		}else {
			model.addAttribute("answerList", evaluation.getAnswers());
		}

		return "result";
	}
}
