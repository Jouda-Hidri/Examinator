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

import examinator.entity.Answer;
import examinator.entity.Choice;
import examinator.entity.Evaluation;
import examinator.entity.Exam;
import examinator.entity.Question;
import examinator.test.TestInsert;

@Controller
public class ExamController {

	@GetMapping("/")
	public String hello(ModelMap model) {
		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		try {

			entityManager.getTransaction().begin();
			@SuppressWarnings("unchecked")
			List<Exam> listExams = entityManager.createQuery("SELECT e FROM Exam e").getResultList();
			model.put("listExams", listExams);
			entityManager.getTransaction().commit();
			entityManager.close();

		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		}
		return "welcome";
	}

	@GetMapping("/init")
	public String initExamAndQuestions(ModelMap model) {
		TestInsert.createOneExamWithfiveQuestions();
		return "welcome";
	}

	@GetMapping("/exam/{id}")
	public String getExam(ModelMap model, @PathVariable(value = "id") String id) {
		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		/* TODO get exam by id */
		entityManager.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<Exam> listExams = entityManager.createQuery("SELECT e FROM Exam e WHERE exam_id=" + id).getResultList();
		model.put("listExams", listExams);
		entityManager.getTransaction().commit();
		entityManager.close();
		model.addAttribute("exam", listExams.get(0));

		return "exam";
	}

	@GetMapping("/first/{id}")
	public String getFirstQuestion(HttpServletRequest request, ModelMap model,
			@PathVariable(value = "id") String exam_id) {
		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		entityManager.getTransaction().begin();
		// get 1st question
		@SuppressWarnings("unchecked")
		List<Question> listQuestions = entityManager.createQuery("SELECT q FROM Question q WHERE exam_id=" + exam_id)
				.getResultList();
		model.addAttribute("question", listQuestions.get(0));
		// persist a new evaluation
		Evaluation evaluation = new Evaluation();
		entityManager.persist(evaluation);
		request.getSession().setAttribute("evaluation", evaluation);

		entityManager.getTransaction().commit();
		entityManager.close();

		return "question";
	}

	@PostMapping("/next/{question_id}")
	public String getNextQuestion(HttpServletRequest request, ModelMap model,
			@PathVariable(value = "question_id") String question_id, @ModelAttribute("choice_id") String choice_id) {
		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		entityManager.getTransaction().begin();
		// save answer
		@SuppressWarnings("unchecked")
		List<Choice> listChoices = entityManager.createQuery("SELECT c FROM Choice c WHERE choice_id =" + choice_id)
				.getResultList();
		Choice choice = listChoices.get(0);
		// TODO check if one at least one choice is selected
		Answer answer = new Answer();
		answer.setChoice(choice);
		Evaluation evaluation = (Evaluation) request.getSession().getAttribute("evaluation");
		answer.setEvaluation(evaluation);
		entityManager.persist(answer);

		// get next question
		@SuppressWarnings("unchecked")
		List<Question> listQuestions = entityManager
				.createQuery("SELECT q FROM Question q WHERE question_id > " + question_id).getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();
		if (listQuestions.isEmpty()) {
			model.addAttribute("question", "");
		} else {
			model.addAttribute("question", listQuestions.get(0));
		}
		model.addAttribute("previous_question_id", question_id);

		return "question";
	}

	@GetMapping("/result/{last_question_id}")
	public String getResult(HttpServletRequest request, ModelMap model,
			@PathVariable(value = "last_question_id") String last_question_id) {
		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		entityManager.getTransaction().begin();

		Evaluation evaluation = (Evaluation) request.getSession().getAttribute("evaluation");
		@SuppressWarnings("unchecked")
		List<Answer> answerList = entityManager.createQuery(
				"SELECT a From Evaluation v JOIN v.answers a JOIN a.choice c WHERE v.id="
						+ evaluation.getId())
				.getResultList();

		model.put("answerList", answerList);

		return "result";
	}

}
