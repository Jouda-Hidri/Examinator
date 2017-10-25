package examinator.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

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

		} catch (

		Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		}
		return "welcome";
	}

	@GetMapping("/exam/{id}")
	public String getExam(Model model) {
		Exam exam = new Exam();
		exam.setTitle("example title");

		model.addAttribute("exam", exam);

		return "exam";
	}

}
