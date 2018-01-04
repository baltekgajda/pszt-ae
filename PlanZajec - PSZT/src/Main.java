import java.io.IOException;

import inOut.ZarzadzanieDanymi;
import klasyPodstawowe.Timetable;
import PdfVis.PdfCreator;

public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		ZarzadzanieDanymi zarzadzanieDanymi = new ZarzadzanieDanymi();
	//	URL url = getClass().getResource("dane.txt");
		zarzadzanieDanymi.setFilePath("D:/git/pszt-ae/PlanZajec - PSZT/src/plikiUzytkowe/dane.txt");
		zarzadzanieDanymi.loadData();
		
		Timetable.setAvailableClassrooms(zarzadzanieDanymi.getSalaAmount());
		Timetable timetable = new Timetable(zarzadzanieDanymi);
		timetable.geneticAlgorithm();
		System.out.println("Najlepszy: "+timetable.getBestChromosome().toString());
		
		PdfCreator pdf = new PdfCreator(zarzadzanieDanymi);
		pdf.genotypeToFile(timetable.getBestChromosome(), "file.pdf");
		
	}

}
