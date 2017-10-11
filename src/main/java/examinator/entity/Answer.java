package examinator.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

public class Answer {
	@Id
	@Column(name = "ANSWER_ID")
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHOICE_ID")
	Choice choice;
	
	@ManyToMany(mappedBy="answers")
	List<User> users;
	
	boolean selected;
	Date date;
}
