package components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import Evolution.Genotype;
import inOut.LoadData;

/**
 * Class that holds all information about created timetable, is used to perform
 * genetic algorithm
 */
public class Timetable {

	public static int workingDays = 5;
	public static int workingHours = 6;
	public static int availableClassrooms = 1;
	static int availableTimeSlots = 4;
	static int genNumber = 100;
	static int populationSize = 100;
	static double fitnessRate = 0.3;
	ArrayList<Genotype> genotypes;
	static ArrayList<Course> courses; // all courses in the timetable
	int teachersCount;
	int coursesCount;
	int studentGroupsCount;

	/**
	 * Class constructor
	 * 
	 * @param data
	 */
	public Timetable(LoadData data) {
		genotypes = new ArrayList<Genotype>();
		courses = data.getArrayCourses();
		teachersCount = data.getArrayTeacher().size();
		coursesCount = courses.size();
		studentGroupsCount = data.getArrayStudentGroup().size();
		Genotype.setClassesNo(courses.size());
		Genotype.setTimeSlots(availableTimeSlots);
	}

	/**
	 * Set fitness ratio (between number of empty slots and earliness) for Timetable
	 * evaluation function
	 * 
	 * @param rate
	 */
	public static void setFitnessRate(double rate) {
		if (rate > 1.0)
			rate = 1.0;
		if (rate < 0.0)
			rate = 0.0;
		fitnessRate = rate;
	}

	/**
	 * Set working times for timetable
	 * 
	 * @param days
	 *            available days (from 1 to 7)
	 * @param hours
	 *            available hours (from 1 to 12)
	 */
	public static void setWorkingTime(int days, int hours) {
		Timetable.workingDays = days;
		Timetable.workingHours = hours;
		Timetable.availableTimeSlots = days * hours * availableClassrooms;
		Genotype.setTimeSlots(availableTimeSlots);
	}

	public static void setAvailableClassrooms(int classrooms) {
		Timetable.availableClassrooms = classrooms;
		Timetable.availableTimeSlots = workingDays * workingHours * classrooms;
		Genotype.setTimeSlots(availableTimeSlots);
	}

	/**
	 * Set population size, has to be an even number
	 * 
	 * @param size
	 */
	public static void setPopulationSize(int size) {
		if (size % 2 == 1)
			Timetable.populationSize = size + 1;
		else
			Timetable.populationSize = size;
	}

	/**
	 * Set number of timetable generations
	 * 
	 * @param number
	 */
	public static void setGenNumber(int number) {
		Timetable.genNumber = number;
	}

	/**
	 * Creates randomly generated population of valid genotypes
	 * 
	 * @throws Exception
	 */
	private void generateFirstPopulation() throws Exception {
		Genotype.setTimeSlots(availableTimeSlots);
		while (genotypes.size() < populationSize) {
			Genotype gen = new Genotype();
			evaluateFitnessVal(gen);
			genotypes.add(gen);
			gen.setGeneration(0); // genotype remembers at which generation was created
		}
	}

	/**
	 * Runs genetics algorithm with given number of generations
	 * 
	 * @throws Exception
	 */
	public void geneticAlgorithm() throws Exception {
		generateFirstPopulation();
		for (int i = 0; i < genNumber; i++) {
			System.out.println("Generation: " + i);
			breedPopulation(i + 1);
			selectNextGeneration();
		}
	}

	/**
	 * Returns the best valid chromosome in population
	 */ 											
	public Genotype getBestValidChromosome() {
		for (Genotype g : genotypes)
			if (checkifValid(g.getChromosome()))
				return g;
		return null;
	}

	/**
	 * Selecting best genotypes to create next generation by sorting genotypes
	 * ArrayList by fitnessValue removes worst genotypes(half of them)
	 */
	private void selectNextGeneration() {

		Collections.sort(genotypes, new Comparator<Genotype>() {
			public int compare(Genotype g1, Genotype g2) {
				return Double.compare(g2.getFitnessVal(), g1.getFitnessVal());
			}
		});
		for (int i = genotypes.size() - 1; i >= populationSize; i--)
			genotypes.remove(i);
	}

	/**
	 * Evaluating fitness value of the chromosome
	 */
	private void evaluateFitnessVal(Genotype gen) {
		double slotFitness = (workingHours - 2) * workingDays * studentGroupsCount,
				earlyFitness = (Math.pow(2, workingHours) - 1) * workingDays * availableClassrooms;
		double maxSlot = slotFitness, maxEarly = earlyFitness;
		ArrayList<Integer> chromosome = gen.getChromosome();
		// j - hour of the day<0,workingHours-1>, k-weekday
		int i = 0, j = 0, k = 0;
		int groupId;
		int[][] classArray = new int[workingDays][studentGroupsCount];

		while (i < availableTimeSlots) {

			if (chromosome.get(i) != 0) {
				// Early fitness
				earlyFitness -= Math.pow(2, j);

				// Slot fitness
				groupId = courses.get(chromosome.get(i) - 1).getStudentGroup().getId();
				if (classArray[k][groupId] == 0 || classArray[k][groupId] == j)
					classArray[k][groupId] = j + 1;
				else {
					slotFitness -= (j - classArray[k][groupId]);
					classArray[k][groupId] = j + 1;
				}
			}

			if (++i % availableClassrooms == 0)
				if (++j % workingHours == 0) {
					j = 0;
					k++;
				}
		}

		int infNum = getInterferenceNumber(chromosome); // returns how many courses interfere with each other
														// which means how many hard constraints failed
		double val = fitnessRate * slotFitness + (1 - fitnessRate) * (maxSlot / maxEarly) * earlyFitness;
		if (infNum > 0)
			val = val / (2 ^ infNum);
		gen.setFitnessVal(val);
	}

	/**
	 * Breeding parents genotypes to create new ones
	 */
	private void breedPopulation(int generation) throws Exception {

		// choose 2 parents using roulette selection method
		int a, b;
		for (int i = 0; i < populationSize / 2; i++) {
			a = rouletteSelect();
			b = rouletteSelect();

				// create new genotype from parents a and b
				Genotype gen = new Genotype(genotypes.get(a).getChromosome(), genotypes.get(b).getChromosome());
				if (!checkifValid(gen.getChromosome())) // checking if created child is valid
					gen.repair();
				evaluateFitnessVal(gen);
				gen.setGeneration(generation);
				genotypes.add(gen);
				
				// create new genotype from parents b and a
				gen = new Genotype(genotypes.get(b).getChromosome(), genotypes.get(a).getChromosome());
				if (!checkifValid(gen.getChromosome()))
					gen.repair();
				evaluateFitnessVal(gen);
				gen.setGeneration(generation);
				genotypes.add(gen);
		}

	}

	/**
	 * Roulette selecting parents from genotypes ArrayList
	 */
	private int rouletteSelect() {
		double fitnessSum = 0;
		for (int i = 0; i < populationSize; i++)
			fitnessSum += genotypes.get(i).getFitnessVal();

		Random generator = new Random();
		double value = generator.nextDouble() * fitnessSum;

		for (int i = 0; i < populationSize; i++) {
			value -= genotypes.get(i).getFitnessVal();
			if (value < 0)
				return i;
		}
		return populationSize - 1;
	}

	/**
	 * Return number of hard constraints fails
	 */
	private int getInterferenceNumber(ArrayList<Integer> chromosome) {
		int[][] teacherArray = new int[workingDays * workingHours][teachersCount];
		int[][] classesArray = new int[workingDays * workingHours][studentGroupsCount];
		int i = 0, j = 0;

		while (i < availableTimeSlots) {
			if (chromosome.get(i) == 0) {
				if (++i % availableClassrooms == 0)
					j++;
				continue;
			}

			int teacherId = courses.get(chromosome.get(i) - 1).getTeacher().getId();
			int classId = courses.get(chromosome.get(i) - 1).getStudentGroup().getId();

			teacherArray[j][teacherId]++;
			classesArray[j][classId]++;

			if (++i % availableClassrooms == 0)
				j++;
		}
		int sum = 0;
		for (i = 0; i < workingDays * workingHours; i++)
			for (j = 0; j < teachersCount; j++)
				if (teacherArray[i][j] > 1)
					sum++;

		for (i = 0; i < workingDays * workingHours; i++)
			for (j = 0; j < studentGroupsCount; j++)
				if (classesArray[i][j] > 1)
					sum++;

		return sum;
	}

	private boolean checkifValid(ArrayList<Integer> chromosome) {
		boolean[][] teacherArray = new boolean[workingDays * workingHours][teachersCount];
		boolean[][] classesArray = new boolean[workingDays * workingHours][studentGroupsCount];
		int i = 0, j = 0;

		while (i < availableTimeSlots) {
			if (chromosome.get(i) == 0) {
				if (++i % availableClassrooms == 0)
					j++;
				continue;
			}

			int teacherId = courses.get(chromosome.get(i) - 1).getTeacher().getId();
			int classId = courses.get(chromosome.get(i) - 1).getStudentGroup().getId();

			if (teacherArray[j][teacherId] == true || classesArray[j][classId] == true) {
				// System.out.println("!!!");
				return false;
			}

			else {
				teacherArray[j][teacherId] = true;
				classesArray[j][classId] = true;
			}

			if (++i % availableClassrooms == 0)
				j++;
		}

		return true;
	}

	static public Course returnClass(int classNumber) {
		return courses.get(classNumber - 1);
	}

	public static ArrayList<Course> getClasses() {
		return courses;
	}

}
