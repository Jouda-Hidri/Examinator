package examinator.test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import examinator.entity.Choice;
import examinator.entity.Exam;
import examinator.entity.Question;

public class TestInsert {
	public static void createOneExamWithfiveQuestions() {

		Exam exam = new Exam();
		Question question = new Question();
		Choice choice1 = new Choice();
		question.addChoice(choice1);
		Choice choice2 = new Choice();
		question.addChoice(choice2);
		Choice choice3 = new Choice();
		question.addChoice(choice3);
		exam.addQuestion(question);
		Question question2 = new Question();
		Choice choice21 = new Choice();
		question2.addChoice(choice21);
		exam.addQuestion(question2);
		Question question3 = new Question();
		Choice choice31 = new Choice();
		question3.addChoice(choice31);
		exam.addQuestion(question3);

		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();

		entityManager.getTransaction().begin();
		entityManager.persist(exam);
		choice1.setTitle("c1");
		choice1.setCorrect(true);
		choice2.setTitle("c2");
		choice2.setCorrect(false);
		choice3.setTitle("c3");
		choice3.setCorrect(false);
		question.setTitle("q");
		question2.setTitle("q2");
		question3.setTitle("q3");
		exam.setTitle("e_qc");
		entityManager.getTransaction().commit();

		exam.addQuestion(question);

	}

}
