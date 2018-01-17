package Evolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import components.Timetable;

/**
 * 
 * class that represents genotype of the timetable
 */
public class Genotype {

	static int timeSlots = 60; // chromosome size
	static int classesNo = 0;
	static double mutationRate = 0.01;
	static int repairCounter = 0;
	ArrayList<Integer> chromosome;
	double fitnessVal = 0;
	int generation = 0; //number of the generation genotype was created in

	public void setGeneration(int gen) {
		generation = gen;
	}

	public int getGeneration() {
		return generation;
	}

	public void setFitnessVal(double val) {
		fitnessVal = val;
	}

	public ArrayList<Integer> getChromosome() {
		return chromosome;
	}

	/**
	 * Sets probability of mutation of a single gene
	 * @param mutationRate
	 */
	public static void setMutationRate(double mutationRate) {
		if (mutationRate > 1)
			mutationRate = 1;
		if (mutationRate < 0)
			mutationRate = 0;
		Genotype.mutationRate = mutationRate;
	}

	/**
	 * Class constructor Creates random schedule
	 * 
	 * @throws Exception
	 */
	public Genotype() throws Exception {
		if (classesNo > timeSlots) {
			System.out.println("Too many classes to fit in a timetable.");
			return;
		}

		createRandomChromosome();
	}

	/**
	 * Class constructor Creates child from 2 parents chromosomes, using crossover
	 * and mutation
	 * 
	 * @param parent1
	 *            first parent's chromosome
	 * @param parent2
	 *            second parent's chromosome
	 */
	public Genotype(ArrayList<Integer> parent1, ArrayList<Integer> parent2) {
		crossoverOX1(parent1, parent2);
		mutate();
	}

	/**
	 * Mutation function With probability given by mutationRate, every gene in
	 * chromosome can mutate (change value to any value from Class Id range) To
	 * avoid duplicates, and keep chromosome valid, old gene swaps place with the
	 * one that holds new value
	 */
	private void mutate() {
		Random generator = new Random();
		for (int i = chromosome.size() - 1; i >= 0; i--) {
			if (generator.nextDouble() < mutationRate) {
				int newValue = generator.nextInt(classesNo + 1);
				int oldValue = chromosome.get(i);
				if (newValue == oldValue)
					continue;
				for (int j = 0; j < chromosome.size(); j++) {
					if (chromosome.get(j) == newValue) {
						chromosome.set(j, oldValue);
						chromosome.set(i, newValue);
						break;
					}
				}
			}
		}
	}

	/**
	 * Order one crossover function In result of 2 parents crossover on child is
	 * created The child's chromosome is stored in chromosome variable
	 * 
	 * @param parent1
	 * @param parent2
	 */
	private void crossoverOX1(ArrayList<Integer> parent1, ArrayList<Integer> parent2)
	{
		Random generator = new Random();
		int beg = generator.nextInt(timeSlots);
		int end = generator.nextInt(timeSlots);
		ArrayList<Integer> child = new ArrayList<Integer>(timeSlots);

		if (beg > end) {
			int temp = beg;
			beg = end;
			end = temp;
		}

		for (int i = beg; i <= end; i++)
			child.add(parent1.get(i));

		ArrayList<Integer> array = new ArrayList<Integer>(timeSlots - (end - beg) - 1);
		for (int i = end + 1; i < timeSlots; i++)
			if (!child.contains(parent2.get(i)))
				array.add(parent2.get(i));

		for (int i = 0; i <= end; i++)
			if (!child.contains(parent2.get(i)))
				array.add(parent2.get(i));

		child.addAll(array);

		while (child.size() < timeSlots) // because genotype contains zeros
			child.add(0);
		Collections.rotate(child, beg);
		chromosome = child;
	}

	/**
	 * creates random chromosome which is well-defined
	 * using Fisher-Yates linear shuffle
	 */
	private void createRandomChromosome() throws Exception {
		chromosome = new ArrayList<Integer>(timeSlots);
		for (int i = 1; i <= classesNo; i++)
			chromosome.add(i);
		for (int i = classesNo + 1; i <= timeSlots; i++)
			chromosome.add(0);	//0 when there are no courses
		// randomizer Fisher-Yates shuffle
		Random generator = new Random();
		for (int i = timeSlots - 1; i >= 1; i--)
			Collections.swap(chromosome, generator.nextInt(i + 1), i);
		repair();
	}

	public static void setTimeSlots(int timeSlots) {
		Genotype.timeSlots = timeSlots;
	}

	public static void setClassesNo(int number) {
		Genotype.classesNo = number;
	}

	public String toString() {
		String string = new String("[");
		for (int i = 0; i < chromosome.size() - 1; i++) {
			string += chromosome.get(i);
			string += ", ";
		}
		string = string + chromosome.get(chromosome.size() - 1) + "]";
		string = string + " FitnessVal: " + fitnessVal;
		return string;
	}

	public double getFitnessVal() {
		return fitnessVal;
	}

	// repairs chromosome so that no teacher or classe have lessons more than once
	// in a hour
	public void repair() throws Exception {
		repairCounter++;
		// System.out.println(repairCounter);
		// System.out.println("before: " + chromosome.toString());
		for (Integer i = new Integer(0); i < chromosome.size(); i = i + Timetable.availableClassrooms) // loop which
																										// checks every
																										// hour of
																										// everyday for
																										// any problem
																										// in the
																										// genotype
		{// System.out.println(chromosome.size());
			for (Integer j = new Integer(i); j < i + Timetable.availableClassrooms; j++) {
				if (!check(i, j)) {
					swap(j, findSwap(i, j));
					// System.out.println("after swap: " + chromosome.toString());
				} // move(i, j); //if there is an conflict, resolve the conflict, move the
					// class(j)
			}
		}
		// System.out.println("after: " + chromosome.toString() + "\n");
	}

	// returns the coordinates found for conflictClass to swap
	private int findSwap(int hourSlot, int conflictClass) throws Exception {
		int slotsPerDay = Timetable.availableClassrooms * Timetable.workingHours;
		int k = 0, i = 0;

		while (k < slotsPerDay) {
			if (i == hourSlot)// no reason to check the hourSlot of the conflictClass from which we are taking
								// it
			{
				i += slotsPerDay;
				if (i >= timeSlots) {
					k += Timetable.availableClassrooms;
					i = k;
				}
				// i=+Timetable.availableClassrooms; // problem with continue, it omits the
				// increase in (i)
				continue;
			}
			Integer j = new Integer(i);
			for (; j < i + Timetable.availableClassrooms; j++) {
				if (canSwap(conflictClass, hourSlot, j, i))
					return j; // lets see is it possible to swap the class in the (conflictClass) slot for the
								// class in the (j) slot
			}
			i += slotsPerDay;
			if (i >= timeSlots) {
				k += Timetable.availableClassrooms;
				i = k;
			}
		}
		throw new Exception("impossible to fit all the classes without conflict, \nhourslot: " + hourSlot
				+ "\nconflictClass: " + conflictClass + "\nrepair counter: " + repairCounter + chromosome.toString());
		// return 0;
	}

	private void swap(int slot1, int slot2) {
		Collections.swap(chromosome, slot1, slot2);
	}

	private boolean canSwap(int slot1, int hourslot1, int slot2, int hourslot2) {
		// System.out.println("to check swap: " + chromosome.get(slot1) + " and " +
		// chromosome.get(slot2));
		for (int x = hourslot1; x < hourslot1 + Timetable.availableClassrooms; x++) // check if inserting the class
																					// (slot2) under (slot1), would
																					// create a conflict
		{
			if (chromosome.get(slot2) == 0)
				break;
			if (x == slot1)
				continue; // this is to omit the class we are trying to swawp
			if (!checkIfBoth(chromosome.get(slot2), chromosome.get(x))) {
				// System.out.println("false");
				return false;
			}
		}

		for (int x = hourslot2; x < hourslot2 + Timetable.availableClassrooms; x++) // check if inserting the class
																					// (slot1) under (slot2), would
																					// create a conflict
		{
			if (chromosome.get(slot1) == 0)
				break;
			if (x == slot2)
				continue;
			if (!checkIfBoth(chromosome.get(slot1), chromosome.get(x))) {
				// System.out.println("false");
				return false;
			}
		}
		// System.out.println("true");
		return true;
	}

	// check whether there is a teacher or class that have lesson more than once in
	// a hour
	private boolean check(int hourStart, int classToCheck) {
		// if (classToCheck==0) return true;
		if (chromosome.get(classToCheck) == 0)
			return true;
		int aux = chromosome.get(classToCheck);
		for (int i = hourStart; i < Timetable.availableClassrooms + hourStart; i++) {
			if (chromosome.get(i) == 0)
				continue; // if a classsroom is empty, no problem, continue
			if (i == classToCheck)
				continue; // if we are checking the same class, don't do that, continue
			if (!checkIfBoth(aux, chromosome.get(i)))
				return false; // check if a teacher or a class is having lessons more than once a hour
		}
		return true;

	}

	// check whether to classes have the same teacher or the same
	private boolean checkIfBoth(int class1, int class2) {
		if (class1 == 0 || class2 == 0)
			return true;
		// System.out.println("CHECKING:" + class1.getId() + " vs " + class2.getId());
		if (Timetable.returnClass(class1).getTeacher().equals(Timetable.returnClass(class2).getTeacher()))
			return false;

		if (Timetable.returnClass(class1).getStudentGroup().equals(Timetable.returnClass(class2).getStudentGroup()))
			return false;

		return true;

	}

	public void setChromosome(ArrayList<Integer> chromosome) {
		this.chromosome = chromosome;
	}

}
