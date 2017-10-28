package examinator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Choice {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "choice_id")
	private long id;

	private String title;

	@ManyToOne
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "question_id")
	private Question question;

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
}