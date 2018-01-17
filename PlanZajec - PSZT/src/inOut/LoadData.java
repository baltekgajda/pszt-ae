package inOut;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import components.Course;
import components.Classroom;
import components.StudentGroup;
import components.Subject;
import components.Teacher;
import exceptions.ExceptionBadDataStructure;

/**
 * Class that holds all information from the file
 */
public class LoadData {

	ArrayList<Teacher> arrayTeacher = new ArrayList<Teacher>();
	ArrayList<StudentGroup> arrayStudentGroup = new ArrayList<StudentGroup>();
	ArrayList<Subject> arraySubject = new ArrayList<Subject>();
	ArrayList<Classroom> arrayClassroom = new ArrayList<Classroom>();
	ArrayList<Course> arrayCourses = new ArrayList<Course>();
	Integer classroomsAmount;
	String filePath;

	/**
	 * @return the arrayTeacher
	 */
	public ArrayList<Teacher> getArrayTeacher() {
		return arrayTeacher;
	}

	/**
	 * @param arrayTeacher
	 *            the arrayTeacher to set
	 */
	public void setArrayTeacher(ArrayList<Teacher> arrayTeacher) {
		this.arrayTeacher = arrayTeacher;
	}

	/**
	 * @return the arrayClas
	 */
	public ArrayList<StudentGroup> getArrayStudentGroup() {
		return arrayStudentGroup;
	}

	/**
	 * @param arrayStudentGroup
	 *            the arrayClas to set
	 */
	public void setArrayStudentGroup(ArrayList<StudentGroup> arrayStudentGroup) {
		this.arrayStudentGroup = arrayStudentGroup;
	}

	/**
	 * @return the arraySubject
	 */
	public ArrayList<Subject> getArraySubject() {
		return arraySubject;
	}

	/**
	 * @param arraySubject
	 *            the arraySubject to set
	 */
	public void setArraySubject(ArrayList<Subject> arraySubject) {
		this.arraySubject = arraySubject;
	}

	/**
	 * @return the classroomsAmount
	 */
	public Integer getClassroomAmount() {
		return classroomsAmount;
	}

	/**
	 * @param classroomsAmount
	 *            the classroomAmount to set
	 */
	public void setClassroomAmount(Integer classroomAmount) {
		this.classroomsAmount = classroomAmount;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath
	 *            the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public ArrayList<Classroom> getArrayClassroom() {
		return arrayClassroom;
	}

	public void setArrayClassroom(ArrayList<Classroom> arrayClassroom) {
		this.arrayClassroom = arrayClassroom;
	}

	public ArrayList<Course> getArrayCourses() {
		return arrayCourses;
	}

	public void setArrayCourses(ArrayList<Course> arrayCourses) {
		this.arrayCourses = arrayCourses;
	}

	public LoadData() {
	}


	/**
	 * Processes data from the file and saves it in arrays
	 */
	public void loadData() throws IOException {
		FileReader fileReader = new FileReader(filePath);
		Scanner scanner = new Scanner(fileReader);
		try {
			while (scanner.hasNext()) {
				switch (scanner.next()) {
				case "#Teachers":
					processTeachers(scanner);
					break;
				case "#StudentsGroups":
					processStudentsGroups(scanner);
					break;
				case "#Subjects":
					processSubjects(scanner);
					break;
				case "#Courses":
					processCourses(scanner);
					break;
				case "#Classrooms":
					processClassrooms(scanner);
					break;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		scanner.close();
	}

	/**
	 * processes all teachers from the file
	 */
	private void processTeachers(Scanner scanner) {
		Teacher teacher = null;
		String string = new String();
		String firstName = new String();
		String lastName = new String();
		Integer id;
		while (!(string = scanner.next()).equals("#end")) {
			id = Integer.parseInt(string);
			firstName = scanner.next();
			lastName = scanner.next();
			teacher = new Teacher(id, firstName, lastName);
			arrayTeacher.add(id, teacher);
		}
	}

	/**
	 * processes all students groups from the file
	 */
	private void processStudentsGroups(Scanner scanner) {
		StudentGroup studentGroup = null;
		String string = new String();
		String name = new String();
		Integer id;
		while (!(string = scanner.next()).equals("#end")) {
			id = Integer.parseInt(string);
			name = scanner.next();
			studentGroup = new StudentGroup(id, name);
			arrayStudentGroup.add(id, studentGroup);
		}
	}

	/**
	 * processes all subjects from the file
	 */
	private void processSubjects(Scanner scanner) {
		Subject subject = null;
		String string = new String();
		String name = new String();
		Integer id;
		while (!(string = scanner.next()).equals("#end")) {
			id = Integer.parseInt(string);
			name = scanner.next();
			subject = new Subject(id, name);
			arraySubject.add(id, subject);
		}
		;
	}

	/**
	 * processes all courses from the file
	 */
	private void processCourses(Scanner scanner) throws ExceptionBadDataStructure {
		String string = new String();
		int i = 1;
		while (!(string = scanner.next()).equals("#end")) {
			Course course = new Course();
			course.setId(i);

			if (!string.equals("Teacher"))
				throw new ExceptionBadDataStructure("There should be \"Teacher\" identifier in a file");
			if (!scanner.next().equals("="))
				throw new ExceptionBadDataStructure("There should be \"=\" identifier in a file");
			course.setTeacher(arrayTeacher.get(scanner.nextInt()));

			if (!scanner.next().equals("StudentGroup"))
				throw new ExceptionBadDataStructure("There should be \"StudentGroup\" identifier in a file");
			if (!scanner.next().equals("="))
				throw new ExceptionBadDataStructure("There should be \"=\" identifier in a file");
			course.setStudentGroup(arrayStudentGroup.get(scanner.nextInt()));

			if (!scanner.next().equals("Subject"))
				throw new ExceptionBadDataStructure("There should be \"Subject\" identifier in a file");
			if (!scanner.next().equals("="))
				throw new ExceptionBadDataStructure("There should be \"=\" identifier in a file");
			course.setSubject(arraySubject.get(scanner.nextInt()));

			if (!scanner.next().equals("Multiplicity"))
				throw new ExceptionBadDataStructure("There should be \"Multiplicity\" identifier in a file");
			if (!scanner.next().equals("="))
				throw new ExceptionBadDataStructure("There should be \"=\" identifier in a file, Line: " + string + " Id: " + i);
			int count = scanner.nextInt();
			for (int j = 0; j < count; j++) {
				arrayCourses.add(course);
				i++;
			}
		}
	}

	/**
	 * processes all classrooms from the file
	 */
	private void processClassrooms(Scanner scanner) {
		setClassroomAmount(scanner.nextInt());
		for (Integer i = new Integer(0); i < classroomsAmount; i++)
			arrayClassroom.add(new Classroom(i));
	}
}
