package examinator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
//	private String questions;

	public Exam() {

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
}
