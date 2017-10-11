package examinator.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Exam {
	@Id
	@Column(name = "EXAM_ID")
	private long id;

	@OneToMany
	private List<Question> questions;

}
