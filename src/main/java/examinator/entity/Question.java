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
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "question_id")
	private long id;

	@Column(name = "question_title")
	private String title;

	@ManyToOne
	@JoinColumn(name = "exam_id")
	private Exam exam;

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Collection<Choice> choices = new LinkedHashSet<Choice>();

	public Question() {
		setTitle("Untitled question");
	}

	public Question(String title) {
		setTitle(title);
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
		if (!exam.getQuestions().contains(this)) { 
			exam.getQuestions().add(this);
			// warning this may cause performance issues if you have a large data
			// set since this operation is O(n)
		}
	}

	public Collection<Choice> getChoices() {
		return choices;
	}
	
	public void setChoices(Collection<Choice> choices) {
		this.choices = choices;
	}
	
	public void addChoice(Choice choice) {
		if(choices == null) {
			choices = new LinkedHashSet<Choice>();
		}
        this.choices.add(choice);
        if (choice.getQuestion() != this) {
            choice.setQuestion(this);
        }
    }

}
