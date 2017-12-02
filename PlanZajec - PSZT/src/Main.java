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
		zarzadzanieDanymi.setFilePath("C:/Users/Nexon/Desktop/dane.txt");
		zarzadzanieDanymi.loadData();
		zarzadzanieDanymi.getArrayNauczyciel().toString();
	}

}
