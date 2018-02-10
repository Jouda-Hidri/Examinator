package examinator.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.springframework.stereotype.Component;

import examinator.entity.Choice;

@Component
public class ChoiceDao {
	
	public Choice findById(long choice_id) {
		
		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		entityManager.getTransaction().begin();

		// find the choice by id
		@SuppressWarnings("unchecked")
		List<Choice> listChoices = entityManager.createQuery("SELECT c FROM Choice c WHERE choice_id = :choice_id")
				.setParameter("choice_id", choice_id)
				.getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();
		return listChoices.get(0);
		
		
		
	}

}
