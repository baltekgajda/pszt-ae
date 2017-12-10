/**
 * 
 */
package klasyPodstawowe;

import java.util.HashMap;

public class Plan {
	
	static int availableDays = 5;
	static int availableHours = 8;
	static int availableClassroms; //do ustawienia przy wczytywaniu danych z pliku
	
	private HashMap<Integer, Slot> timetable;
	
	public Plan()
	{
		timetable = new HashMap <Integer, Slot> (availableDays*availableHours*availableClassroms);
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
	
	private int genKey(int day, int hour, int room)
	{
		int key = day*1000+hour*100+room;
		return key;
	}

	private void  genSlots()
	{
		for (int day=1; day<=availableDays; day++)
		{
			for (int hour=1; hour<=availableHours; hour++)
			{
				for (int room=1; room<=availableHours; room++)
				{
					int key = day*1000+hour*100+room;
					timetable.put(genKey(day, hour,room), new Slot(day, hour, room));
				}
			}
		}
	}
	
	public void addZajecie(int day, int hour, int room, int zajecie)
	{
		int key = genKey(day, hour,room);
		timetable.get(key).add(zajecie);
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
