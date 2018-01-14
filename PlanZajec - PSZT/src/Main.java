import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import Evolution.Genotype;
import PdfVis.PdfCreator;
import inOut.LoadData;
import klasyPodstawowe.Timetable;

public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	
	
	static String defaultInFile = "C:\\Users\\bartl\\git\\pszty\\PlanZajec - PSZT\\resources\\dane2.txt";
	static String defaultOutFile = "out.pdf";
	static int defaultPopulation=1000;
	static int defaultGenerations=1000;
	
	private String inFile;
	private String outFile;
	private int population;
	private int generations;
	
	public static void main(String[] args) throws Exception {

		
		Main m = new Main();
		m.getParameters();
		
		LoadData loadData = new LoadData();
	//	URL url = getClass().getResource("dane.txt");
		loadData.setFilePath(m.getInFile());
		loadData.loadData();
//		Timetable.setWorkingTime(3, 2);
		Timetable.setAvailableClassrooms(loadData.getSalaAmount());
		Timetable timetable = new Timetable(loadData);
		Timetable.setPopulationSize(m.getPopulation());
		Timetable.setGenNumber(m.getGenerations());
		

		//test(8, Timetable.workingDays*Timetable.workingHours*Timetable.availableClassrooms, false);
		//return;
		timetable.geneticAlgorithm();
		
		
		if (timetable.getBestValidChromosome()!=null)
		{

			System.out.println("Najlepszy: "+timetable.getBestChromosome().toString());
			System.out.println("Najlepszy valid: "+timetable.getBestValidChromosome().toString());
			System.out.println("Generation: " + timetable.getBestValidChromosome().getGeneration());

			PdfCreator pdf = new PdfCreator(loadData);
			//pdf.genotypeToFile(timetable.getBestValidChromosome(), m.getOutFile());
			pdf.genotypeToFile(timetable.getBestValidChromosome(), m.getOutFile());
		}
		else
		{
			System.out.println("Not found");

		}
		
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
	
	public static void test(int classesNo, int size, boolean random) throws Exception
	{
		System.out.println();
		
		
		ArrayList<Integer> chromosome = new ArrayList<Integer>();
		if (random) {
			for (int i = 1; i <= classesNo; i++)
				chromosome.add(i);
			for (int i = classesNo + 1; i <= size; i++)
				chromosome.add(0);
			Random generator = new Random();
			for (int i = chromosome.size() - 1; i >= 1; i--)
				Collections.swap(chromosome, generator.nextInt(i + 1), i);
		}
		
		else
		{
			chromosome.add(0);
			chromosome.add(6);
			chromosome.add(0);
			chromosome.add(0);
			chromosome.add(3);
			chromosome.add(5);
			chromosome.add(8);
			chromosome.add(0);
			chromosome.add(7);
			chromosome.add(2);
			chromosome.add(1);
			chromosome.add(4);
			
		}
		Genotype gen = new Genotype(chromosome);
		gen.setChromosome(chromosome);
		System.out.println(gen.getChromosome());
		gen.repair();
		System.out.println(gen.getChromosome());
	}

}
