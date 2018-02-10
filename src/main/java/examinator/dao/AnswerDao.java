package examinator.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.springframework.stereotype.Component;

import examinator.entity.Answer;
import examinator.entity.Evaluation;
import examinator.entity.Exam;

@Component
public class AnswerDao {

	public List<Answer> findByEvaluation(Evaluation evaluation){
		
		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		entityManager.getTransaction().begin();
		
		@SuppressWarnings("unchecked")
		List<Answer> answerList = entityManager.createQuery(
				"SELECT a From Evaluation v JOIN v.answers a JOIN a.choice c WHERE v.id= :evaluation_id")
				.setParameter("evaluation_id", evaluation.getId())
				.getResultList();
		
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return answerList;
	}
	
	public Answer save(Answer answer) {
		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(answer);
		entityManager.getTransaction().commit();
		entityManager.close();
		return answer;
	}

}
