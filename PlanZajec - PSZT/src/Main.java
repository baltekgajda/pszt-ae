import java.util.Scanner;
import PdfVis.PdfCreator;
import components.Timetable;
import inOut.LoadData;

public class Main {

	/**
	 * @param args
	 * @throws IOException
	 */

	static String defaultInFile = "C:\\Users\\bartl\\git\\pszty\\PlanZajec - PSZT\\src\\dataFiles\\data.txt";
	static String defaultOutFile = "out.pdf";
	static int defaultPopulation = 1000;
	static int defaultGenerations = 1000;

	private String inFile;
	private String outFile;
	private int population;
	private int generations;

	public static void main(String[] args) throws Exception {

		Main m = new Main();
		m.getParameters();

		LoadData loadData = new LoadData();
		loadData.setFilePath(m.getInFile());
		loadData.loadData();
		Timetable.setAvailableClassrooms(loadData.getClassroomAmount());
		Timetable timetable = new Timetable(loadData);
		Timetable.setPopulationSize(m.getPopulation());
		Timetable.setGenNumber(m.getGenerations());

		timetable.geneticAlgorithm(); //perform genetic algorithm

		if (timetable.getBestValidChromosome() != null) {

			System.out.println("Best: " + timetable.getBestValidChromosome().toString());
			System.out.println("Created in generation number : " + timetable.getBestValidChromosome().getGeneration());

			PdfCreator pdf = new PdfCreator(loadData);
			pdf.genotypeToFile(timetable.getBestValidChromosome(), m.getOutFile());
		} else 
			System.out.println("Not found");
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

	void readInFile() {
		Scanner scanner = new Scanner(System.in);
		inFile = scanner.nextLine();

		if (inFile.length() == 0)
			inFile = defaultInFile;
	}

	void readOutFile() {
		Scanner scanner = new Scanner(System.in);

		outFile = scanner.nextLine();

		if (outFile.length() == 0) {
			outFile = defaultOutFile;
			return;
		}

		String[] ext = outFile.split("\\.");

		if (ext.length > 1) {
			if (ext[1] == "pdf") {
				return;
			}
		}
		outFile = outFile + ".pdf";
	}

	void readPopulation() {
		Scanner scanner = new Scanner(System.in);
		String tmp = scanner.nextLine();

		if (tmp.length() == 0) {
			population = defaultPopulation;
			return;
		}

		population = Integer.parseInt(tmp);

	}

	void readGenerations() {
		Scanner scanner = new Scanner(System.in);
		String tmp = scanner.nextLine();

		if (tmp.length() == 0) {
			generations = defaultGenerations;
			return;
		}

		generations = Integer.parseInt(tmp);
	}

	public void getParameters() {
		try {
			System.out.println("Input file (default " + defaultInFile + "):");
			readInFile();
			System.out.println("Output file (default " + defaultOutFile + "):");
			readOutFile();
			System.out.println("Population size (default " + defaultPopulation + "):");
			readPopulation();
			System.out.println("Number of generations (default " + defaultGenerations + "):");
			readGenerations();
		} catch (Exception e) {
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
