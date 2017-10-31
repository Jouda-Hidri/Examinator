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
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "answer_id")
	private long id;

	@ManyToOne
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "choice_id")
	private Choice choice;

	@ManyToOne
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "evaluation_id")
	private Evaluation evaluation;

	public Choice getChoice() {
		return choice;
	}

	public void setChoice(Choice choice) {
		this.choice = choice;
		if (!choice.getAnswers().contains(this)) {
			choice.getAnswers().add(this);
			// warning this may cause performance issues if you have a large
			// data
			// set since this operation is O(n)
		}
	}

	public Evaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
		if (!evaluation.getAnswers().contains(this)) {
			evaluation.getAnswers().add(this);
			// warning this may cause performance issues if you have a large
			// data
			// set since this operation is O(n)
		}
	}
	
	public long getId() {
		return id;
	}

}
