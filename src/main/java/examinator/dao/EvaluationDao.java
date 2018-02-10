package examinator.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.springframework.stereotype.Component;

import examinator.entity.Evaluation;

@Component
public class EvaluationDao {

	public Evaluation save(Evaluation evaluation) {

		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		entityManager.getTransaction().begin();

		entityManager.persist(evaluation);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		return evaluation;

	}

	public Evaluation findByID(long evaluation_id) {
		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		entityManager.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<Evaluation> evaluationList = entityManager
				.createQuery("SELECT v FROM Evaluation v WHERE evaluation_id = :evaluation_id")
				.setParameter("evaluation_id", evaluation_id).getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();
		if (evaluationList.isEmpty()) {
			return null;
		}
		return evaluationList.get(0);
	}

	public List<Evaluation> findAll() {
		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		entityManager.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<Evaluation> listEvaluations = entityManager.createQuery("SELECT v FROM Evaluation v").getResultList();

		List<Evaluation> listNonEmptyEvaluations = new ArrayList<Evaluation>();
		for (Evaluation evaluation : listEvaluations) {
			if (!evaluation.getAnswers().isEmpty()) {
				listNonEmptyEvaluations.add(evaluation);
			}
		}

		entityManager.getTransaction().commit();
		entityManager.close();

		return listNonEmptyEvaluations;
	}

	public void update(Evaluation evaluation) {
		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		entityManager.getTransaction().begin();
		
		entityManager.merge(evaluation);
		
		entityManager.getTransaction().commit();
		entityManager.close();

	}
}
