package examinator.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import examinator.entity.Choice;
import examinator.entity.Question;

public class QuestionController {

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

	@PostMapping("/choice_result") // see the result of a selected choice
	public String getChoiceResult(ModelMap model, @ModelAttribute("choiceId") String id) {
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

}
