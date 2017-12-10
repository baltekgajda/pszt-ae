package Evolution;
import java.awt.image.ByteLookupTable;

import klasyPodstawowe.Plan;

public class Genotyp {
	
	static int dayLength = 3;
	static int hourLength = 3;
	static int roomLength; //trzeba ustawiæ przed
	
	private String genotyp;
	
	
	public static void setDayLength(int d)
	{
		dayLength =(int) Math.ceil( Math.log(d)/Math.log(2));
	}
	
	public static void setHourLength(int d)
	{
		hourLength =(int) Math.ceil( Math.log(d)/Math.log(2));
	}
	
	public static void setRoomLength(int d)
	{
		roomLength =(int) Math.ceil( Math.log(d)/Math.log(2));
	}
	
	public Plan getPlan()
	{
		Plan plan = new Plan();
		
		int genlen = dayLength+hourLength+roomLength;

		for ( int i=0, id=1; i<genotyp.length(); i=i+genlen, id++)
		{
			String s = genotyp.substring(i, i+genlen);		
			int d = Integer.parseInt(s.substring(0,dayLength),2);
			int h = Integer.parseInt(s.substring(dayLength,roomLength),2);
			int r = Integer.parseInt(s.substring(roomLength,s.length()),2);
			
			plan.addZajecie(d, h, r, id);
		}

		return plan;
	}
	
	public static void  test(String p)
	{

		
		int genlen = dayLength+hourLength+roomLength;

		for ( int i=0, id=1; i<p.length(); i=i+genlen, id++)
		{
			String s = p.substring(i, i+genlen);		
			int d = Integer.parseInt(s.substring(0,dayLength),2);
			int h = Integer.parseInt(s.substring(dayLength,dayLength+roomLength),2);
			int r = Integer.parseInt(s.substring(dayLength+roomLength,s.length()),2);
			
			System.out.println("Id: "+id+" day: "+d+" hour: "+h+" room: "+r);
		}

	}
	
	

}

