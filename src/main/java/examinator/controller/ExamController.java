package examinator.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import examinator.entity.Exam;

@Controller
public class ExamController {

	@GetMapping("/")
	public String hello(ModelMap model) {
		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		try {

			entityManager.getTransaction().begin();
			@SuppressWarnings("unchecked")
			List<Exam> listExams = entityManager.createQuery("SELECT e FROM Exam e").getResultList();
			model.put("listExams", listExams);
			entityManager.getTransaction().commit();
			entityManager.close();

		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		}
		return "welcome";
	}

	@GetMapping("/exam/{id}")
	public String getExam(ModelMap model, @PathVariable(value = "id") String id) {
		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		/* TODO get exam by id */
		entityManager.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<Exam> listExams = entityManager.createQuery("SELECT e FROM Exam e WHERE exam_id=" + id).getResultList();
		model.put("listExams", listExams);
		entityManager.getTransaction().commit();
		entityManager.close();
		model.addAttribute("exam", listExams.get(0));

		return "exam";
	}

}
