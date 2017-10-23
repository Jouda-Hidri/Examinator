package examinator.controller;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import examinator.entity.Exam;

@Controller
public class ExamController {

	@GetMapping("/hello")
	public String hello(Model model) {

		// EntityManager entityManager = EntityManagerUtil.getEntityManager();

		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		try {

			entityManager.getTransaction().begin();
			entityManager.persist(new Exam());
			entityManager.getTransaction().commit();
			entityManager.close();
			System.out.println("_exam_ok_");

		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			System.out.println("_exam_ko_");
		}

		model.addAttribute("examId", "1");

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
