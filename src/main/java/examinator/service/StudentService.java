package examinator.service;

import java.util.List;

import org.springframework.stereotype.Service;

import examinator.dao.StudentDao;
import examinator.entity.Student;
import examinator.test.TestInsert;

@Service
public class StudentService {
	StudentDao studentDao = new StudentDao();

	public void creatIfDoesntExist() {
		// in case the database is empty add a test student
		List<Student> listStudents = studentDao.findAll();
		if (listStudents.isEmpty()) {
			TestInsert.createStudent();
		}
	}
	
	public Student getStudent(String username, String password) {
		return studentDao.findByUsernameAndPassword(username, password);
	}
}
