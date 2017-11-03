package examinator.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import examinator.entity.Answer;
import examinator.entity.Choice;
import examinator.entity.Evaluation;

public class AnswerDao {

	public Answer save(String choice_id, Evaluation evaluation) {
		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		entityManager.getTransaction().begin();

		@SuppressWarnings("unchecked")
		// find the choice by id
		List<Choice> listChoices = entityManager.createQuery("SELECT c FROM Choice c WHERE choice_id =" + choice_id)
				.getResultList();
		Choice choice = listChoices.get(0);
		// create a new answer instance
		Answer answer = new Answer();
		// set the choice and the current evaluation
		answer.setChoice(choice);
		answer.setEvaluation(evaluation);

		entityManager.getTransaction().commit();
		entityManager.close();
		return answer;
	}

	public List<Answer> findByEvaluation(Evaluation evaluation){
		
		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		entityManager.getTransaction().begin();
		
		@SuppressWarnings("unchecked")
		List<Answer> answerList = entityManager.createQuery(
				"SELECT a From Evaluation v JOIN v.answers a JOIN a.choice c WHERE v.id="
						+ evaluation.getId())
				.getResultList();
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return answerList;
	}

}
