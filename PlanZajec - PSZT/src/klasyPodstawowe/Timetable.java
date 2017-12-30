package klasyPodstawowe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import Evolution.Genotype;
import inOut.ZarzadzanieDanymi;

public class Timetable {

	static int workingDays = 1;
	static int workingHours =4;
	static int availableClassrooms=1;
	static int availableTimeSlots = 4;
	static int genNumber = 1;					//to tez nie wiem czy static 
	static int populationSize = 6;				//moze nie static? do przemyslenia	na pewno musi byc parzyste
	ArrayList <Genotype> genotypes;
	ArrayList <Zajecia> classes;			//wszystkie zajecia
	int teachersCount;
	int classesCount;
	
	public Timetable(ZarzadzanieDanymi data)
	{
		genotypes=new ArrayList<Genotype>();
		this.classes = data.getArrayZajecia();
		teachersCount=data.getArrayNauczyciel().size();
		classesCount=data.getArrayKlasa().size();
		Genotype.setClassesNo(classes.size());
		Genotype.setTimeSlots(availableTimeSlots);
		System.out.println(teachersCount+" "+classesCount+"Ilosc slotow: "+availableTimeSlots);
	}
	
	public static void setWorkingTime(int days, int hours)
	{
		Timetable.workingDays = days;
		Timetable.workingHours=hours;
		Timetable.availableTimeSlots=days*hours*availableClassrooms;
		Genotype.setTimeSlots(availableTimeSlots);
	}
	
	public static void setAvailableClassrooms(int classrooms)
	{
		Timetable.availableClassrooms=classrooms;
		Timetable.availableTimeSlots=workingDays*workingHours*classrooms;
		Genotype.setTimeSlots(availableTimeSlots);
		System.out.println("Ilosc slotow po zmianie: " + availableTimeSlots);
	}
	
	public static void setPopulationSize(int size)
	{
		if(size%2==1)
			Timetable.populationSize=size+1;
		else
			Timetable.populationSize=size;
	}
	
	public static void setGenNumber(int number)
	{
		Timetable.genNumber=number;
	}
	
	private void generateFirstPopulation()
	{
		Genotype.setTimeSlots(availableTimeSlots);
		while(genotypes.size()<populationSize)
		{
			Genotype gen = new Genotype();
			if(checkIfValid(gen.getChromosome()))
				genotypes.add(gen);
			else
				continue;
		}
	}
	
	public void geneticAlgorithm()
	{
		generateFirstPopulation();
		for(int i=0;i<genNumber;i++)
		{
			breedPopulation();
			selectNextGeneration();
		}
	}

	private void selectNextGeneration() {

		Collections.sort(genotypes, new Comparator<Genotype>() {
		    public int compare(Genotype g1, Genotype g2) {
		        return Integer.compare(g2.getFitnessVal(), g1.getFitnessVal());			//teraz jest rosnoco, jak zamienic to g1 i g2 
		    }});
		//for(int i=0;i<genotypes.size();i++)
		//	System.out.print(genotypes.get(i).fitnessVal+" ");
		//System.out.println(genotypes.size());
		for(int i=genotypes.size()-1;i>=populationSize;i--)
			genotypes.remove(i);
		//System.out.println(genotypes.size());
		//for(int i=0;i<genotypes.size();i++)
		//	System.out.print(genotypes.get(i).fitnessVal+" ");
		
	}

	//reprodukuj populacje
	private void breedPopulation() {
		
		//najpierw wybieramy rodzicow
		int a,b;
		for(int i=0;i<populationSize/2;i++)
		{
			a=rouletteSelect();
			b=rouletteSelect();
			System.out.println("Rodzice: "+genotypes.get(a).toString()+"  "+genotypes.get(b).toString());
			while(genotypes.size()-populationSize==2*i)
			{
				Genotype gen = new Genotype(genotypes.get(a),genotypes.get(b));
				if(checkIfValid(gen.getChromosome()))
					genotypes.add(gen);
				else
					continue;
			}
			while(genotypes.size()-populationSize==2*i+1)
			{
				Genotype gen = new Genotype(genotypes.get(b),genotypes.get(a));
				if(checkIfValid(gen.getChromosome()))
					genotypes.add(gen);
				else
					continue;
			}
		}
		System.out.println("HOHO: "+genotypes.size());
	}

	private int rouletteSelect() 				//nazwa metody do zmiany
	{	
		// tu zastosowane fitness proportionate selection(kolo ruletki) ? mozliwe ze zmienic na inne albo dopisac kolejna
		long fitnessSum=0;
		for(int i=0;i<populationSize;i++)
			fitnessSum+=genotypes.get(i).getFitnessVal();
		//System.out.println(fitnessSum);
		
		Random generator = new Random();					//next Double
		double value =generator.nextDouble()* fitnessSum;			//nigdy nie osiagnie 1.0 ale to chyba nie problem bo male prawdpopodobienstwo
		
		for(int i=0;i<populationSize;i++)
		{
			value-=genotypes.get(i).getFitnessVal();
			if(value<0)
				return i;
		}
		System.out.println("Cos nie tak z ruletka");
		return populationSize-1;			//kiedy cos pójdzie nie tak ale nie powinno nigdy 
	}
	
	private boolean checkIfValid(ArrayList<Integer> chromosome)
	{
		boolean [][] teacherArray = new boolean[workingDays*workingHours][teachersCount];
		boolean [][] classesArray = new boolean[workingDays*workingHours][classesCount];
		int i=0, j=0;
		
		while(i<chromosome.size())
		{
			if(chromosome.get(i)==0)
			{
				if(++i%availableClassrooms==0)
					j++;
				continue;
			}
			
			int teacherId=classes.get(chromosome.get(i)-1).getNauczyciel().getId();
			int classId=classes.get(chromosome.get(i)-1).getKlasa().getId();
			
			if(teacherArray[j][teacherId]==true || classesArray[j][classId]==true)
			{
				System.out.println("!!!");
				return false;
			}

			else
			{
				teacherArray[j][teacherId]=true;
				classesArray[j][classId]=true;
			}
			
			if(++i%availableClassrooms==0)
				j++;
		}
		return true;
	}
	
}
