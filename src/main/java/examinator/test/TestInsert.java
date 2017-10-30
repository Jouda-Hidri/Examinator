package examinator.test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import examinator.entity.Choice;
import examinator.entity.Exam;
import examinator.entity.Question;

public class TestInsert {
	public static void createOneExamWithfiveQuestions() {

		Exam exam = new Exam();
		Choice choice1 = new Choice();
		Choice choice2 = new Choice();
		Choice choice3 = new Choice();
		Choice choice21 = new Choice();
		Choice choice22 = new Choice();
		Choice choice23 = new Choice();
		Choice choice31 = new Choice();
		Choice choice32 = new Choice();
		Choice choice33 = new Choice();

		Question question = new Question();
		Question question2 = new Question();
		Question question3 = new Question();

		choice1.setTitle("c1");
		choice1.setCorrect(true);
		choice2.setTitle("c2");
		choice2.setCorrect(false);
		choice3.setTitle("c3");
		choice3.setCorrect(false);
		choice21.setTitle("c21");
		choice21.setCorrect(false);
		choice22.setTitle("c22");
		choice22.setCorrect(true);
		choice23.setTitle("c23");
		choice23.setCorrect(false);
		choice31.setTitle("c31");
		choice31.setCorrect(false);
		choice32.setTitle("c32");
		choice32.setCorrect(false);
		choice33.setTitle("c33");
		choice33.setCorrect(true);
		
		question.setTitle("q");
		question2.setTitle("q2");
		question3.setTitle("q3");
		
		exam.setTitle("e_qc");
		
		question.addChoice(choice1);
		question.addChoice(choice2);
		question.addChoice(choice3);
		question2.addChoice(choice21);
		question2.addChoice(choice22);
		question2.addChoice(choice23);
		question3.addChoice(choice31);
		question3.addChoice(choice32);
		question3.addChoice(choice33);
		
		exam.addQuestion(question);
		exam.addQuestion(question2);
		exam.addQuestion(question3);
		
		EntityManager entityManager = Persistence.createEntityManagerFactory("examinatorpu").createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(exam);
		entityManager.getTransaction().commit();

	}

}
