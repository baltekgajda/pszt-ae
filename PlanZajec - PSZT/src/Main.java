import java.io.IOException;

import inOut.ZarzadzanieDanymi;
import klasyPodstawowe.Timetable;
import PdfVis.PdfCreator;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	
	static String defaultInFile = "D:/git/pszt-ae/PlanZajec - PSZT/src/plikiUzytkowe/dane3.txt";
	static String defaultOutFile = "out.pdf";
	static int defaultPopulation=1000;
	static int defaultGenerations=1000;
	
	private String inFile;
	private String outFile;
	private int population;
	private int generations;
	
	public static void main(String[] args) throws IOException {
		
		Main m = new Main();
		m.getParameters();
		
		ZarzadzanieDanymi zarzadzanieDanymi = new ZarzadzanieDanymi();
	//	URL url = getClass().getResource("dane.txt");
		zarzadzanieDanymi.setFilePath(m.getInFile());
		zarzadzanieDanymi.loadData();
		
		Timetable.setAvailableClassrooms(zarzadzanieDanymi.getSalaAmount());
		Timetable timetable = new Timetable(zarzadzanieDanymi);
		Timetable.setPopulationSize(m.getPopulation());
		Timetable.setGenNumber(m.getGenerations());
		timetable.geneticAlgorithm();
		
		
		if (timetable.getBestChromosome()!=null)
		{
			System.out.println("Najlepszy: "+timetable.getBestChromosome().toString());
			PdfCreator pdf = new PdfCreator(zarzadzanieDanymi);
			pdf.genotypeToFile(timetable.getBestValidChromosome(), m.getOutFile());
		}
		else System.out.println("Not found");		
		
	}
	
	 public String getInFile() {
		return inFile;
	}

	public String getOutFile() {
		return outFile;
	}

	public int getPopulation() {
		return population;
	}

	public int getGenerations() {
		return generations;
	}

	void readInFile()
	{
		Scanner scanner  = new Scanner(System.in);
		inFile = scanner.nextLine();
		
		if (inFile.length()==0)
			inFile=defaultInFile;
	}
	
	 void readOutFile()
	{
		Scanner scanner  = new Scanner(System.in);
		
		outFile = scanner.nextLine();
		
		if (outFile.length()==0)
		{
			outFile=defaultOutFile;
			return;
		}
		
		String[] ext = outFile.split("\\.");
		
		if (ext.length>1)
		{
			if (ext[1]=="pdf")
			{
				return;
			}
		}
		outFile=outFile+".pdf";	
	}
	
	 void readPopulation()
	{
		Scanner scanner  = new Scanner(System.in);
		String tmp = scanner.nextLine();
		
		if (tmp.length()==0)
		{
			population = defaultPopulation;
			return;
		}
		
		population = Integer.parseInt(tmp);
		
		
	}
	
	 void readGenerations()
	{
		Scanner scanner  = new Scanner(System.in);
		String tmp = scanner.nextLine();
		
		if (tmp.length()==0)
		{
			generations = defaultGenerations;
			return;
		}
		
		generations = Integer.parseInt(tmp);
	}
	 
	public void getParameters()
	{
		try {
		System.out.println("Plik z danymi (domyœlnie " + defaultInFile + "):");
		readInFile();
		System.out.println("Plik wyjœciowy(domyœlnie " + defaultOutFile + "):");
		readOutFile();
		System.out.println("Wielkoœæ populacji(domyœlnie " + defaultPopulation + "):");
		readPopulation();
		System.out.println("Liczba generacji(domyœlnie " + defaultGenerations + "):");
		readGenerations();
		}
		catch (Exception e)
		{
			System.out.println("Error reading data\n using default parameters");
			inFile = defaultInFile;
			outFile = defaultOutFile;
			population = defaultPopulation;
			generations = defaultGenerations;
		}
		
		System.out.println(inFile);
		System.out.println(outFile);
		System.out.println(population);
		System.out.println(generations);
		
	}
	

}
