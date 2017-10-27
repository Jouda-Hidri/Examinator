package examinator.test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import examinator.entity.Exam;
import examinator.entity.Question;

public class TestInsert {
	public static void createOneExamWithfiveQuestions() {

		Exam exam = new Exam();
		Question question = new Question();
		exam.addQuestion(question);
		Question question2 = new Question();
		exam.addQuestion(question2);
		Question question3 = new Question();
		exam.addQuestion(question3);

		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();

		entityManager.getTransaction().begin();
		entityManager.persist(exam);
		question.setTitle("q");
		question2.setTitle("q2");
		question3.setTitle("q3");
		exam.setTitle("e");
		entityManager.getTransaction().commit();
		
		exam.addQuestion(question);
		
	}

}
