/**
 * 
 */
package klasyPodstawowe;

import java.util.ArrayList;

/**
 * @author Maciek
 *
 */
public class Plan {
	
	static int availableDays = 5;
	static int availableHours = 8;
	static int availableClassroms; //do ustawienia przy wczytywaniu danych z pliku
	
	private ArrayList<Slot> timetable;
	
	public Plan()
	{
		timetable = new ArrayList<Slot>();
		genSlots();
	}
	
	
	public static void setAvailableDays(int d)
	{
		availableDays =d;
	}
	
	public static void setAvailableHours(int d)
	{
		availableHours =d;
	}
	
	public static void setAvailableClassrooms(int d)
	{
		availableClassroms =d;
	}

	private void  genSlots()
	{
		for (int day=1; day<=availableDays; day++)
		{
			for (int hour=1; hour<=availableHours; hour++)
			{
				for (int room=1; room<=availableHours; room++)
				{
					timetable.add(new Slot(day, hour, room));
				}
			}
		}
	}

	public float getScore()
	{
		//TODO
		return 0;
	}
	
	//TODO
	/* +tworzenie planu na podstawie genotypu
	 * +ocena planu
	 * 
	 * 
	 */
	
	
	
}
