package klasyPodstawowe;

import java.util.ArrayList;

public class Slot {
	
	int day; //dzien 1- pon, 5- pt
	int hour; //nr lekcji
	int classroom; //nr klasy
	ArrayList<Integer> zajecia; //lista zajec w danym slocie 
	
	public Slot(int d, int h, int c)
	{
		day=d;
		hour=h;
		classroom=c;
		zajecia = new ArrayList<Integer>();
	}
	
	public void add(int z)
	{
		zajecia.add(z);
	}
	
	public void add(Zajecia z)
	{
		zajecia.add(z.getId());
	}
	
	
	

}
