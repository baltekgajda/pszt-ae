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
	private HashMap<Integer, Slot> illegal;
	
	public Plan()
	{
		timetable = new HashMap <Integer, Slot>(); //poprawne sloty (dobry dzien godzina klasa)
		illegal = new HashMap <Integer, Slot>(); //niepoprawne sloty (dzien, godzina lub klasa poza zakresem)
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

	private void  genSlots() //niepotrzebne
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
	
	private boolean isValidSlot(int d, int h, int r) //czy poprawne dzien godzina klasa
	{
		
		if (d<1 || d>availableDays) return false;
		if (h<1 || d>availableHours) return false;
		if (r<0 || d>availableClassroms) return false;
		return true;
	}
	
	public void addZajecie(int day, int hour, int room, int zajecie)
	{
		int key = genKey(day, hour,room);
		if (isValidSlot(day,hour,room))
		{
			if (timetable.containsKey(key))
				timetable.get(key).add(zajecie);
			else 
			{
				Slot s = new Slot(day, hour, room);
				s.add(zajecie);
				timetable.put(key, s);
			}
		}
		else
		{
			
			if (illegal.containsKey(key))
				illegal.get(key).add(zajecie);
			else 
			{
				Slot s = new Slot(day, hour, room);
				s.add(zajecie);
				illegal.put(key, s);
			}
		}	
	}

	public float getScore()
	{
		//TODO
		return 0;
	}
	
	//TODO
	/* +ocena planu
	 * 
	 * 
	 */
	
	
	
}
