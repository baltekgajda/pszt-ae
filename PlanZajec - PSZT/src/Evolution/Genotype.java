package Evolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import klasyPodstawowe.Timetable;
import klasyPodstawowe.Zajecia;

public class Genotype {

	static int timeSlots=60;		//rozmiar chromosome
	static int classesNo=0;
	static double mutationRate=0.2;
	
	ArrayList<Integer> chromosome;		//czy zamienic na zwykla []?
	double fitnessVal=0;					//czy zamienic na inna niz int
	
	
	public void setFitnessVal(double val)
	{
		fitnessVal=val;
	}
	
	public ArrayList<Integer> getChromosome() {
		return chromosome;
	}
	
	/**
	 * Sets probability of mutation of a single gene
	 * @param mutationRate
	 */
	public static void setMutationRate(double mutationRate) {
		if(mutationRate>1)
			mutationRate=1;
		if(mutationRate<0)
			mutationRate=0;
		Genotype.mutationRate = mutationRate;
	}


	/**
	 * Class constructor
	 * Creates random schedule
	 */
	public Genotype()
	{
		if(classesNo>timeSlots)
		{
			System.out.println("Too many classes to fit in a timetable.");		//dodac exception i wyjsc z tego
			return;
		}
		
		createRandomChromosome();
		//System.out.println(this.toString());	
	}
	
	/**
	 * Class constructor
	 * Creates child from 2 parents chromosomes, using crossover and mutation
	 * @param parent1 first parent's chromosome
	 * @param parent2 second parent's chromosome
	 */
	public Genotype(ArrayList<Integer> parent1, ArrayList<Integer> parent2) 
	{
		crossoverOX1(parent1, parent2);
		mutate();
	}

	/**
	 * Mutation function
	 * With probability given by mutationRate, every gene in chromosome can mutate 
	 * (change value to any value from Class Id range)
	 * To avoid duplicates, and keep chromosome valid, old gene swaps place 
	 * with the one that holds new value
	 */
	private void mutate() 
	{
		Random generator=new Random();
		for(int i=chromosome.size()-1;i>=0;i--)
		{
			if(generator.nextDouble()<mutationRate)
			{
				int newValue=generator.nextInt(classesNo+1);
				int oldValue=chromosome.get(i);
				if(newValue==oldValue)
					continue;
				for(int j=0;j<chromosome.size();j++)
				{
					if(chromosome.get(j)==newValue)
					{
						chromosome.set(j, oldValue);
						chromosome.set(i, newValue);
						break;
					}
				}
			}
		}
	}

	/**
	 * Order one crossover function
	 * In result of 2 parents crossover on child is created
	 * The child's chromosome is stored in chromosome variable
	 * @param parent1
	 * @param parent2
	 */
	private void crossoverOX1(ArrayList<Integer> parent1, ArrayList<Integer> parent2) 	//czy moze crossover powinien byc w timetable???
	{
		Random generator = new Random();
		int beg = generator.nextInt(timeSlots);
		int end = generator.nextInt(timeSlots);
		ArrayList<Integer> child = new ArrayList<Integer>(timeSlots);
		
		if(beg>end)
		{
			int temp = beg;
			beg=end;
			end=temp;
		}
		
		//System.out.println("Poczatek: "+beg+" Koniec: "+end);
		for(int i=beg;i<=end;i++)
			child.add(parent1.get(i));
		
		ArrayList<Integer> array = new ArrayList<Integer>(timeSlots-(end-beg)-1);	//array trzymajacy elementy parent1 ktore jeszcze nie sa w dziecku
		for(int i=end+1;i<timeSlots;i++)
			if(!child.contains(parent2.get(i)))
				array.add(parent2.get(i));
		
		for(int i=0;i<=end;i++)
			if(!child.contains(parent2.get(i)))
				array.add(parent2.get(i));

		child.addAll(array);
		
		while(child.size()<timeSlots)		//bo zero jest wiele razy i sie nigdy nie doda
			child.add(0);
		Collections.rotate(child, beg);
		chromosome= child;
	}
	


	private void createRandomChromosome()
	{
		chromosome = new ArrayList<Integer>(timeSlots);
		for(int i=1;i<=classesNo;i++)
			chromosome.add(i);
		for(int i=classesNo+1;i<=timeSlots;i++)
			chromosome.add(0);			//rozmiar teraz wynosi timeSlots
		//randomizer Fisher-Yates shuffle									//wg mnie powinno dzialac
		Random generator = new Random();
		for(int i=timeSlots-1;i>=1;i--)
			Collections.swap(chromosome, generator.nextInt(i+1), i);
		
		repair();
	}

	public static void setTimeSlots(int timeSlots)
	{
		Genotype.timeSlots=timeSlots;
	}

	public static void setClassesNo(int number) 
	{
		Genotype.classesNo=number;
	}
	
	public String toString()
	{
		String string = new String("[");
		for(int i=0;i<chromosome.size()-1;i++)
		{
			string+=chromosome.get(i);
			string+=", ";
		}
		string=string+ chromosome.get(chromosome.size()-1) + "]";
		string=string+" FitnessVal: "+fitnessVal;
		return string;
	}
	
	public double getFitnessVal()
	{
		return fitnessVal;
	}
	
	//repairs chromosome so that no teacher or classe have lessons more than once in a hour
		public void repair(){
			for (Integer i = new Integer(0); i<chromosome.size(); i=i+Timetable.availableClassrooms) //loop which checks every 8 each day for any problem in the genotype
			{
				for (Integer j = new Integer(i); j<i+Timetable.availableClassrooms; j++)
				{
					if (!check(i, j)) move(i, j); //if there is an conflict, resolve the conflict, move the class(j)
				}
			}
		}
		
		//move the classes to a hour in which there will be no conflict
		private void move(int hourSlot, int classToMove) {
			for (int i = 0; i<chromosome.size();  i=i+Timetable.availableClassrooms)
			{
				if (i==hourSlot) continue; //this is to omit the hour from which the class is taken to be moved
				if (!check(i, classToMove)) continue; //if placing the class in a hourslot creates a conflict, continue
				Integer aux = new Integer(checkIfPlace(i)); //find empty classroom for the class
				if (aux==null) continue; //if there is no empty classroom, continue
				
				moveTo(classToMove, i); //we found empty classroom, so now lets move the class
				break;
			}
			
		}

		//moves the class to the designated place in the chromosome
		private void moveTo(int classToMove, int where) {
			chromosome.set(where, chromosome.get(classToMove)); //move the chromosome
			chromosome.set(classToMove, 0); //set its old position to zero(empty)
			
		}
		//returns the position in chromosome which is not taken by any class(empty classroom)
		private Integer checkIfPlace(int hourSlot) {
			for(int i=0; i<Timetable.availableClassrooms; i++)
			{
				if (chromosome.get(i+hourSlot)==0) return i+hourSlot;
			}
			return null;
			
			
		}

		//check whether there is a teacher or class that have lesson more than once in a hour
		private boolean check(int hourStart, int classToCheck)
		{
			if (classToCheck==0) return true;
			if (chromosome.get(classToCheck)==0) return true;
			Zajecia aux = Timetable.returnClass(chromosome.get(classToCheck));
			for (int i=hourStart; i<Timetable.availableClassrooms+hourStart; i++)
			{
				if (chromosome.get(i)==0) continue;
				if (i==classToCheck) continue;
				if (!checkIfBoth(aux, Timetable.returnClass(chromosome.get(i)))) return false;
			}
			return true;
			
		}
		
		//check whether to classes have the same teacher or the same 
		private boolean checkIfBoth(Zajecia class1, Zajecia class2)
		{
			if (class1.getNauczyciel().equals(class2.getNauczyciel())) return false;
			if (class1.getKlasa().equals(class2.getKlasa())) return false;
			return true;
			
		}
	
}

