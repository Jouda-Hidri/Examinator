package examinator.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import examinator.entity.Student;

public class StudentDao {
	
	public Student findByUsernameAndPassword(String username, String password) {

		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		/* TODO get exam by id */
		entityManager.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<Student> listStudents = entityManager.createQuery("SELECT s FROM Student s WHERE name='" + username + "' AND password='"+password+"'").getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(listStudents.isEmpty()) {
			return null;
		}
		return listStudents.get(0);
	}

}