package PdfVis;

import components.StudentGroup;
import components.Timetable;

/**
 * Stores entire schedule for one class (group of students)
 * Table representation o a schedule
 * 
 */
public class ClassTable {
	
	
	StudentGroup k;
	Lesson [][] table;//day, hour
	
	/**
	 * Class constructor
	 * @param k specified group of students
	 */
	public ClassTable(StudentGroup k)
	{
		this.k=k;
		table = new Lesson[Timetable.workingDays][Timetable.workingHours];
	}
	
	/**
	 * Add lesson to schedule
	 * @param id id of the lesson
	 * @param day day of the lesson, (from 1 to Timetable.workingDays)
	 * @param hour day of the lesson, (from 1 to Timetable.workingHours)
	 * @param room room where lesson takes place (from 1 to number defined in input file)
	 * @see Timetable.workingDays
	 */
	public void addClass(int id, int day, int hour,int room )
	{
		table[day-1][hour-1] = new Lesson(id, room);
	}
	
	

}
