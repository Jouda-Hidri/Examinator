package examinator.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Exam {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "exam_id")
	private long id;

	public long getId() {
		return id;
	}

	private String title;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "exam", cascade = CascadeType.ALL)
	private Collection<Question> questions = new LinkedHashSet<Question>();

	public Collection<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Collection<Question> questions) {
		this.questions = questions;
	}

	public Exam() {
		setTitle("Untitled exam");
	}

	public Exam(String title) {
		setTitle(title);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void addQuestion(Question question) {
		if(questions == null) {
			questions = new ArrayList<Question>();
		}
        this.questions.add(question);
        if (question.getExam() != this) {
            question.setExam(this);
        }
    }
}
