package examinator.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import examinator.dao.AnswerDao;
import examinator.dao.ChoiceDao;
import examinator.dao.EvaluationDao;
import examinator.dao.ExamDao;
import examinator.dao.QuestionDao;
import examinator.entity.Answer;
import examinator.entity.Choice;
import examinator.entity.Evaluation;
import examinator.entity.Exam;
import examinator.entity.Question;
import examinator.entity.Student;
import examinator.test.TestInsert;

@Service
public class ExamService {

	@Autowired
	ExamDao examDao;
	@Autowired
	QuestionDao questionDao;
	@Autowired
	EvaluationDao evaluationDao;
	@Autowired
	AnswerDao answerDao;
	@Autowired
	ChoiceDao choiceDao;

	public List<Exam> getListExams() {
		List<Exam> listExams = examDao.findAll();
		// in case the database is empty, add a test exam
		if (listExams.isEmpty()) {
			listExams = new ArrayList<Exam>();
			listExams.add(TestInsert.createOneExamWithfiveQuestions());
		}
		return listExams;
	}

	public List<Evaluation> getListEvaluations() {
		return evaluationDao.findAll();
	}

	public Question getFirstQuestionByExamId(long exam_id) {
		List<Question> listQuestions = questionDao.findQuestionByExamId(exam_id);
		if (listQuestions != null && !listQuestions.isEmpty()) {
			return listQuestions.get(0);
		}
		return null;
	}

	public Evaluation createNewEvaluation(Student student) {
		Evaluation evaluation = new Evaluation();
		evaluation.setStudent(student);
		return evaluationDao.save(evaluation);
	}

	public Answer save(long choice_id, Evaluation evaluation) {
		Choice choice = choiceDao.findById(choice_id);
		Answer answer = new Answer();
		answer.setChoice(choice);
		answer.setEvaluation(evaluation);
		return answerDao.save(answer);
	}

	public Question findNextQuestionByCurrentExamIdAndQuestionId(long exam_id, long current_question_id) {
		return questionDao.findNextQuestionByCurrentExamIdAndQuestionId(exam_id, current_question_id);
	}

	public void finish(Evaluation evaluation) {
		evaluation.setFinished();
		evaluationDao.update(evaluation);
	}

	public Evaluation findByID(long evaluation_id) {
		return evaluationDao.findByID(evaluation_id);
	}

	public List<Answer> findByEvaluation(Evaluation evaluation) {
		return answerDao.findByEvaluation(evaluation);
	}

	public void save(Exam exam) {
		examDao.save(exam);
	}

	public Exam createExam(String title) {
		if (title.equals("")) {
			return null;
		}
		Exam exam = new Exam();
		exam.setTitle(title);
		return exam;
	}

	public Question addQuestion(String title, String choice_1, String choice_2, String choice_3, String choice_id) {
		if (title.equals("") || choice_1.equals("") || choice_2.equals("") || choice_3.equals("")
				|| choice_id.equals("")) {

			return null;
		}
		Question question = new Question();
		question.setTitle(title);
		Choice c1 = new Choice();
		Choice c2 = new Choice();
		Choice c3 = new Choice();
		c1.setTitle(choice_1);
		c2.setTitle(choice_2);
		c3.setTitle(choice_3);
		switch (choice_id) {
		case "choice_1":
			c1.setCorrect(true);
			break;
		case "choice_2":
			c2.setCorrect(true);
			break;
		case "choice_3":
			c3.setCorrect(true);
			break;
		}
		question.addChoice(c1);
		question.addChoice(c2);
		question.addChoice(c3);
		return question;
	}

}
