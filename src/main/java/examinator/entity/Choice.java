package examinator.entity;

import java.util.Collection;
import java.util.LinkedHashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Choice {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "choice_id")
	private long id;

	private String title;
	
	private boolean correct;

	@ManyToOne
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "question_id")
	private Question question;
	
	@OneToMany(mappedBy = "choice", cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Collection<Answer> answers = new LinkedHashSet<Answer>();

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
		if (!question.getChoices().contains(this)) { 
			question.getChoices().add(this);
			// warning this may cause performance issues if you have a large
			// data
			// set since this operation is O(n)
		}
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	public Collection<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Collection<Answer> answers) {
		this.answers = answers;
	}
	
	public void addAnswer(Answer answer) {
		if(answers == null) {
			answers = new LinkedHashSet<Answer>();
		}
        this.answers.add(answer);
        if (answer.getChoice() != this) {
            answer.setChoice(this);
        }
    }
}