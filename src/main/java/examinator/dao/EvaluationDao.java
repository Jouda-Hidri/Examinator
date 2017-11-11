package examinator.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import examinator.entity.Evaluation;

public class EvaluationDao {

	public Evaluation createNewEvaluation() {

		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		entityManager.getTransaction().begin();
		Evaluation evaluation = new Evaluation();
		entityManager.persist(evaluation);
		entityManager.getTransaction().commit();
		entityManager.close();
		return evaluation;

	}

	public Evaluation findByID(String evaluation_id) {
		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		/* TODO get evaluation by id */
		entityManager.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<Evaluation> evaluationList = entityManager
				.createQuery("SELECT v FROM Evaluation v WHERE evaluation_id=" + evaluation_id).getResultList();

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
		List<Evaluation> evaluationList = entityManager.createQuery("SELECT v FROM Evaluation v").getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();
		return evaluationList;
	}

	public void finish(Evaluation evaluation) {
		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		entityManager.getTransaction().begin();
		evaluation.setFinished();
		entityManager.merge(evaluation);
		entityManager.getTransaction().commit();
		entityManager.close();

	}

	public void delete(Evaluation evaluation) {
		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		Evaluation evaluation_retrieved = entityManager.find(Evaluation.class, evaluation.getId());
		entityManager.getTransaction().begin();
		entityManager.remove(evaluation_retrieved);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

}
