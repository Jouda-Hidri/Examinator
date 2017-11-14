package examinator.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import examinator.entity.Evaluation;
import examinator.entity.Student;

public class EvaluationDao {

	public Evaluation createNewEvaluation(Student student) {

		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		entityManager.getTransaction().begin();

		Evaluation evaluation = new Evaluation();
		evaluation.setStudent(student);

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

	public void finish(Evaluation evaluation) {
		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		entityManager.getTransaction().begin();
		evaluation.setFinished();
		entityManager.merge(evaluation);
		entityManager.getTransaction().commit();
		entityManager.close();

	}
}
