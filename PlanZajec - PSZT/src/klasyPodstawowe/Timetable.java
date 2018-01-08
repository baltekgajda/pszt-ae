package klasyPodstawowe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import Evolution.Genotype;
import inOut.ZarzadzanieDanymi;

public class Timetable {

	public static int workingDays = 5;
	public static int workingHours =8;
	public static int availableClassrooms=1;
	static int availableTimeSlots = 4;
	static int genNumber = 1000;					//to tez nie wiem czy static 
	static int populationSize = 100;					//moze nie static? do przemyslenia	na pewno musi byc parzyste
	static double fitnessRate = 0.5;
	ArrayList <Genotype> genotypes;
	ArrayList <Zajecia> classes;			//wszystkie zajecia
	int teachersCount;
	int classesCount;
	int studentGroupsCount;
	
	/**
	 * Class constructor
	 * @param data
	 */
	public Timetable(ZarzadzanieDanymi data)
	{
		genotypes=new ArrayList<Genotype>();
		this.classes = data.getArrayZajecia();
		teachersCount=data.getArrayNauczyciel().size();
		classesCount=classes.size();
		studentGroupsCount=data.getArrayKlasa().size();
		Genotype.setClassesNo(classes.size());
		Genotype.setTimeSlots(availableTimeSlots);
		//System.out.println(teachersCount+" "+classesCount+"Ilosc slotow: "+availableTimeSlots);
	}
	
	/**
	 * Set fitness ratio (between number of empty slots and  earliness) for Timetable evaluation function
	 * @param rate
	 */
	public static void setFitnessRate(double rate)
	{
		if(rate>1.0)
			rate=1.0;
		if(rate<0.0)
			rate=0.0;
		fitnessRate=rate;
	}
	
	/**
	 * Set working times for timetable 
	 * @param days available days (from 1 to 7)
	 * @param hours available hours (from 1 to 12)
	 */
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
		//System.out.println("Ilosc slotow po zmianie: " + availableTimeSlots);
	}
	
	public static void setPopulationSize(int size)
	{
		if(size%2==1)
			Timetable.populationSize=size+1;
		else
			Timetable.populationSize=size;
	}
	
	/**
	 * Set number of timetable generations
	 * @param number
	 */
	public static void setGenNumber(int number)
	{
		Timetable.genNumber=number;
	}
	
	/**
	 * Creates randomly generated population of valid genotypes
	 */
	private void generateFirstPopulation()
	{
		Genotype.setTimeSlots(availableTimeSlots);
		while(genotypes.size()<populationSize)
		{
			Genotype gen = new Genotype();
			
			if(checkIfValid(gen.getChromosome()))
			{
				evaluateFitnessVal(gen);
				genotypes.add(gen);
			}
			else
				continue;
		}
	}
	
	/**
	 * Runs genetics algorithm with given number of generations
	 */
	public void geneticAlgorithm()
	{
		generateFirstPopulation();
		for(int i=0;i<genNumber;i++)
		{
			breedPopulation();
			selectNextGeneration();
		}
		/*for(int i=0;i<genotypes.size();i++)
			System.out.println(genotypes.get(i).toString());*/
	}
	/**
	 * Returns the best chromosome in population
	 * 
	 */
	public Genotype getBestChromosome()
	{
		Genotype chromosome=genotypes.get(0);
		return chromosome;
	}

	private void selectNextGeneration() {

		Collections.sort(genotypes, new Comparator<Genotype>() {
		    public int compare(Genotype g1, Genotype g2) {
		        return Double.compare(g2.getFitnessVal(), g1.getFitnessVal());			//teraz jest rosnoco, jak zamienic to g1 i g2 
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

	private void evaluateFitnessVal(Genotype gen) 
	{
		double slotFitness=(workingHours-2)*workingDays*studentGroupsCount, 
				earlyFitness=(Math.pow(2, workingHours)-1)*workingDays*availableClassrooms;
		double maxSlot=slotFitness, maxEarly=earlyFitness;
		ArrayList <Integer> chromosome = gen.getChromosome();
		//System.out.println("Rozmiar chromosomu: "+gen.toString());

		
		int i=0,j=0,k=0;			//j okresla ktora godzina danego dnia od 0 do (workingHours-1), k to dzien tygodnia
		int groupId;
		//pierwsze to ktory dzien, drugie to klasa
		int [][] classArray = new int[workingDays][studentGroupsCount];
		
		while(i<availableTimeSlots)
		{

			if(chromosome.get(i)!=0)
			{
				//Early fitness
				earlyFitness-=Math.pow(2,j);
				
				
				//Slot fitness
				groupId =classes.get(chromosome.get(i)-1).getKlasa().getId();
				if(classArray[k][groupId]==0 || classArray[k][groupId]==j)
					classArray[k][groupId]=j+1;	//umieszczamy zeby byly od 1 czyli 8:00 do np 12 czyli 19:00
				else
				{
					slotFitness-=(j-classArray[k][groupId]);
					classArray[k][groupId]=j+1;
				}
				
				/*else				//sprawdzic jeszcze
				 if(classArray[k][groupId]==0)
					classArray[k][groupId]=j+1;	//umieszczamy zeby byly od 1 czyli 8:00 do np 12 czyli 19:00
				else
				{
					if(j==classArray[k][groupId])	//roznica godziny
						classArray[k][groupId]=j+1;
					else
					{
						slotFitness-=(j-classArray[k][groupId]);
						System.out.println("LOL"+(j-classArray[k][groupId]));
						classArray[k][groupId]=j+1;
					}
				}*/
				
			}
			
			//zwiekszyc albo zmniejszyc indeksy
			if(++i%availableClassrooms==0)
				if(++j%workingHours==0)
				{
					j=0;
					k++;
				}
		}
		
		gen.setFitnessVal(fitnessRate*slotFitness + (1-fitnessRate)*(maxSlot/maxEarly)*earlyFitness);
	}
	
	//reprodukuj populacje
	private void breedPopulation() {
		
		//choose 2 parents using roulette selection method
		int a,b;
		for(int i=0;i<populationSize/2;i++)
		{
			a=rouletteSelect();
			b=rouletteSelect();
			//System.out.println("Rodzice: "+genotypes.get(a).toString()+"  "+genotypes.get(b).toString());
			while(genotypes.size()-populationSize==2*i)
			{
				Genotype gen = new Genotype(genotypes.get(a).getChromosome(),genotypes.get(b).getChromosome());
				if(checkIfValid(gen.getChromosome()))
				{
					evaluateFitnessVal(gen);
					//System.out.println("DZIECI:  "+gen.toString());
					genotypes.add(gen);
				}
				else
					continue;
			}
			while(genotypes.size()-populationSize==2*i+1)
			{
				Genotype gen = new Genotype(genotypes.get(b).getChromosome(),genotypes.get(a).getChromosome());
				if(checkIfValid(gen.getChromosome()))
				{
					evaluateFitnessVal(gen);
					genotypes.add(gen);
				}
				else
					continue;
			}
		}

	}

	private int rouletteSelect() 				//nazwa metody do zmiany
	{	
		// tu zastosowane fitness proportionate selection(kolo ruletki) ? mozliwe ze zmienic na inne albo dopisac kolejna
		double fitnessSum=0;
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
		//System.out.println("Cos nie tak z ruletka");
		return populationSize-1;			//kiedy cos pójdzie nie tak ale nie powinno nigdy 
	}
	
	private boolean checkIfValid(ArrayList<Integer> chromosome)
	{
		boolean [][] teacherArray = new boolean[workingDays*workingHours][teachersCount];
		boolean [][] classesArray = new boolean[workingDays*workingHours][classesCount];
		int i=0, j=0;
		
		while(i<availableTimeSlots)
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
				//System.out.println("!!!");
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
