import java.io.IOException;

import inOut.ZarzadzanieDanymi;

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
		System.out.println(zarzadzanieDanymi.getArrayZajecia().toString());		
	}

}
