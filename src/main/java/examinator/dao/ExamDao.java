package examinator.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import examinator.entity.Exam;

public class ExamDao {

	public List<Exam> findAll() {
		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		List<Exam> listExams = new ArrayList<Exam>();
		try {
			entityManager.getTransaction().begin();
			listExams = entityManager.createQuery("SELECT e FROM Exam e").getResultList();
			entityManager.getTransaction().commit();
			entityManager.close();

		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		}
		return listExams;
	}

	public Exam findById(long exam_id) {

		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		entityManager.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<Exam> listExams = entityManager.createQuery("SELECT e FROM Exam e WHERE exam_id = :exam_id")
		.setParameter("exam_id", exam_id)
		.getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();
		return listExams.get(0);
	}

	public void save(Exam exam) {
		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(exam);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

}
