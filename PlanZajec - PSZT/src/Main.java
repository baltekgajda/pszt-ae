import java.io.IOException;

import Evolution.Genotyp;
import inOut.ZarzadzanieDanymi;
import klasyPodstawowe.Plan;
import klasyPodstawowe.Timetable;

/**
 * 
 */

/**
 * @author Maciek
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		ZarzadzanieDanymi zarzadzanieDanymi = new ZarzadzanieDanymi();
	//	URL url = getClass().getResource("dane.txt");
		zarzadzanieDanymi.setFilePath("src/plikiUzytkowe/dane.txt");
		zarzadzanieDanymi.loadData();
		Plan.setAvailableClassrooms(zarzadzanieDanymi.getSalaAmount());
		Timetable.setAvailableClassrooms(zarzadzanieDanymi.getSalaAmount());
		Timetable timetable = new Timetable(zarzadzanieDanymi);
		timetable.geneticAlgorithm();
		
		//Genotyp.setRoomLength(zarzadzanieDanymi.getSalaAmount());
		//System.out.println(zarzadzanieDanymi.getArrayZajecia().toString());		
		//Genotyp.setRoomLength(7);
		//hohoho
		//Genotyp.test("000001010011100101110111000");
	}

}
