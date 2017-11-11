package examinator.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import examinator.entity.Question;

public class QuestionDao {

	public Question findFirstQuestionByExamId(String exam_id) {
		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		entityManager.getTransaction().begin();
		// get 1st question
		//TODO order by
		@SuppressWarnings("unchecked")
		List<Question> listQuestions = entityManager.createQuery("SELECT q FROM Question q WHERE exam_id=" + exam_id)
				.getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();
		return listQuestions.get(0);
	}

	public Question findNextQuestionByCurrentQuestionId(String current_question_id) {
		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		entityManager.getTransaction().begin();
		// get next question
		//TODO order by
		@SuppressWarnings("unchecked")
		List<Question> listQuestions = entityManager
				.createQuery("SELECT q FROM Question q WHERE question_id > " + current_question_id).getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();
		if (listQuestions.isEmpty()) {
			return null;
		} else {
			return listQuestions.get(0);
		}
	}

}
