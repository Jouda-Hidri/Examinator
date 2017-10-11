package examinator.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Question {
	  @Id
	  @Column(name = "QUESTION_ID")
	  private long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXAM_ID")
	private Exam exam;
	
	@OneToMany
	private List<Choice> choices;

}
