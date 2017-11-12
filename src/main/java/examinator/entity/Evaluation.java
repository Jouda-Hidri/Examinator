package examinator.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collection;
import java.util.Iterator;
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
public class Evaluation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "evaluation_id")
	private long id;

	LocalDateTime date = LocalDateTime.now();

	boolean finished = false;

	@ManyToOne
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "student_id")
	private Student student;
	
	@OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Collection<Answer> answers = new LinkedHashSet<Answer>();

	public long getId() {
		return id;
	}

	public String getDate() {
		final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
		return date.format(dateTimeFormatter);
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished() {
		this.finished = true;
	}

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

	public Answer getLastAnswer() {
		Iterator<Answer> it = answers.iterator();
		Answer lastAnswer = null;
		while (it.hasNext()) {
			lastAnswer = it.next();
		}
		return lastAnswer;
	}

	@Override
	public String toString() {
		if (answers.isEmpty()) {
			return "This evaluation dit not start.";
		}
		if (finished) {
			return "See the result.";
		}
		return "Resume.";
	}

}
