package examinator.controller;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import examinator.entity.Exam;
import examinator.jpa.EntityManagerUtil;

@Controller
public class ExamController {

	@GetMapping("/hello")
	public String hello(Model model) {

		// ------------------------------------------
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		Exam exam = new Exam();
		try {
			entityManager.getTransaction().begin();
			// exam.setStudentName(studentName);
			exam = entityManager.merge(exam);
			entityManager.getTransaction().commit();
			System.out.println("_exam_ok_");
		} catch (Exception e) {
			// entityManager.getTransaction().rollback();
			e.printStackTrace();
			System.out.println("_exam_ko_");
		}
		// return student;
		// ------------------------------------------

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
