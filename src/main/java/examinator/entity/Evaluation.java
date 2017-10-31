package examinator.entity;

import java.util.Collection;
import java.util.LinkedHashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Evaluation {
	// date
	// one user

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "evaluation_id")
	private long id;
	
	public long getId() {
		return id;
	}

	// many answers
	@OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Collection<Answer> answers = new LinkedHashSet<Answer>();

	public Collection<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Collection<Answer> answers) {
		this.answers = answers;
	}

	public void addAnswer(Answer answer) {
		if (answers == null) {
			answers = new LinkedHashSet<Answer>();
		}
		this.answers.add(answer);
		if (answer.getEvaluation() != this) {
			answer.setEvaluation(this);
		}
	}
}
