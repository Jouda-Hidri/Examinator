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
public class Exam {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "exam_id")
	private long id;

	@Column(name = "exam_title")
	private String title;
	
	@OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Collection<Question> questions = new LinkedHashSet<Question>();

	public Exam() {
		setTitle("Untitled exam");
	}
	
	public Exam(String title) {
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

	public Collection<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Collection<Question> questions) {
		this.questions = questions;
	}
	
	public void addQuestion(Question question) {
		if(questions == null) {
			questions = new LinkedHashSet<Question>();
		}
        this.questions.add(question);
        if (question.getExam() != this) {
            question.setExam(this);
        }
    }
	
//	@Override
//	public String toString() {
//		return ""+id;
//	}
	
}
