package examinator.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import examinator.entity.Choice;
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

	// TODO to delete after test
	@GetMapping("/question/{id}")
	public String getQuestion(ModelMap model, @PathVariable(value = "id") String id) {
		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		/* TODO get question by id */
		entityManager.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<Question> listQuestions = entityManager.createQuery("SELECT q FROM Question q WHERE question_id=" + id)
				.getResultList();
		@SuppressWarnings("unchecked")
		List<Choice> listChoices = entityManager.createQuery("SELECT c FROM Choice c WHERE question_id=" + id)
				.getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();
		model.addAttribute("question", listQuestions.get(0));
		model.put("listChoices", listChoices);

		return "question";
	}

	@GetMapping("/first/{id}")
	public String getFirstQuestion(ModelMap model, @PathVariable(value = "id") String exam_id) {
		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		entityManager.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<Question> listQuestions = entityManager.createQuery("SELECT q FROM Question q WHERE exam_id="+exam_id)
				.getResultList();
		
		entityManager.getTransaction().commit();
		entityManager.close();
		model.addAttribute("question", listQuestions.get(0));
		return "question";
	}

	@PostMapping("/next/{id}")
	public String getNextQuestion(ModelMap model, @PathVariable(value = "id") String id) {
		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		entityManager.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<Question> listQuestions = entityManager.createQuery("SELECT q FROM Question q WHERE question_id > "+id)
				.getResultList();
		
		entityManager.getTransaction().commit();
		entityManager.close();
		if(listQuestions.isEmpty()) {
			model.addAttribute("question", "");
		}else {
			model.addAttribute("question", listQuestions.get(0));
		}
		return "question";
	}
	// TODO save the choice here
	@PostMapping("/choice")
	public String saveChoice(ModelMap model, @ModelAttribute("choiceId") String id) {
		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		/* TODO get choice by id */
		entityManager.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<Choice> listChoices = entityManager.createQuery("SELECT c FROM Choice c WHERE choice_id=" + id)
				.getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();

		Choice choice = listChoices.get(0);
		String result = "wrong";
		if (choice.isCorrect()) {
			result = "correct";
		}
		model.put("title", choice.getTitle());
		model.put("result", result);
		return "result";
	}
	//TODO show the result here
	@GetMapping("/result")
	public String seeResult(ModelMap model, @PathVariable(value = "id") String exam_id) {

		return "result";
	}

}
