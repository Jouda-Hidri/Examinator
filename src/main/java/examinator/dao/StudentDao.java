package examinator.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.springframework.stereotype.Component;

import examinator.entity.Student;

@Component
public class StudentDao {
	
	public List<Student> findAll() {

		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		entityManager.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<Student> listStudents = entityManager.createQuery("SELECT s FROM Student s").getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();
		return listStudents;
	}
	
	public Student findByUsernameAndPassword(String username, String password) {

		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		entityManager.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<Student> listStudents = entityManager.createQuery("SELECT s FROM Student s WHERE username = :username AND password = :password")
		.setParameter("username", username)
		.setParameter("password", password)
		.getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(listStudents.isEmpty()) {
			return null;
		}
		return listStudents.get(0);
	}

}
