package Evolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Genotype {

	static int timeSlots=60;		//rozmiar chromosome
	static int classesNo=0;
	static double mutationRate=0.0;
	
	ArrayList<Integer> chromosome;		//czy zamienic na zwykla []?
	int fitnessVal;					//czy zamienic na inna niz int
	
	
	
	public ArrayList<Integer> getChromosome() {
		return chromosome;
	}

	public static void setMutationRate(double mutationRate) {
		if(mutationRate>1)
			mutationRate=1;
		if(mutationRate<0)
			mutationRate=0;
		Genotype.mutationRate = mutationRate;
	}


	public Genotype()
	{
		if(classesNo>timeSlots)
		{
			System.out.println("Too many classes to fit in a timetable.");		//dodac exception i wyjsc z tego
			return;
		}
		
		createRandomChromosome();
		evaluateFitnessVal();
		System.out.println(this.toString());	
	}
	

	public Genotype(ArrayList<Integer> parent1, ArrayList<Integer> parent2) 
	{
		crossoverOX1(parent1, parent2);
		mutate();
		evaluateFitnessVal();
		System.out.println("DZIECI:  "+toString());
	}


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
		
		System.out.println("Poczatek: "+beg+" Koniec: "+end);
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
	}
	
	public void evaluateFitnessVal() 
	{
		// TODO Auto-generated method stub		//DO ZROBIENIA 
		this.fitnessVal=1;
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
	
	public int getFitnessVal()
	{
		return fitnessVal;
	}
}

