package examinator.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class User {

	@Id
	@Column(name = "USER_ID")
	private long id;
	
	@ManyToMany
	@JoinTable(name="EVALUATION")
	private List<Answer> answers;

}
