package components;

/**
 * Class containing information about classes that is an information about
 * student group, teacher, classroom
 */
public class Course {
	Integer id;
	Subject subject;
	StudentGroup studentGroup;
	Teacher teacher;
	Classroom classroom;

	public Course() {
	}

	public Course(Integer id, Subject subject, StudentGroup studentGroup, Teacher teacher, Classroom classroom) {
		super();
		this.id = id;
		this.subject = subject;
		this.studentGroup = studentGroup;
		this.teacher = teacher;
		this.classroom = classroom;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public StudentGroup getStudentGroup() {
		return studentGroup;
	}

	public void setStudentGroup(StudentGroup studentGroup) {
		this.studentGroup = studentGroup;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Classroom getClassroom() {
		return classroom;
	}

	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}

	public String toString() {
		return getId().toString() + " " + getStudentGroup().toString() + " " + getTeacher().toString();
	}
}
