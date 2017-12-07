import java.io.IOException;

import inOut.ZarzadzanieDanymi;
import klasyPodstawowe.Plan;

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
		System.out.println(zarzadzanieDanymi.getArrayZajecia().toString());		
	}

}
