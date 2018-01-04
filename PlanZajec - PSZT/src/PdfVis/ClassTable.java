package PdfVis;

import klasyPodstawowe.Klasa;
import klasyPodstawowe.Timetable;

public class ClassTable {
	
	
	Klasa k;
	Lesson [][] table;
	
	public ClassTable(Klasa k)
	{
		this.k=k;
		table = new Lesson[Timetable.workingDays][Timetable.workingHours];
	}
	
	public void addClass(int id, int day, int hour,int room )
	{
		table[day-1][hour-1] = new Lesson(id, room);
	}
	
	

}
