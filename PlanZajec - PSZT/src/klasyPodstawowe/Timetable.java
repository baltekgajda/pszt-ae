package klasyPodstawowe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import Evolution.Genotype;
import inOut.ZarzadzanieDanymi;

public class Timetable {

	public static int workingDays = 5;
	public static int workingHours =5;
	public static int availableClassrooms=1;
	static int availableTimeSlots = 4;
	static int genNumber = 100;					//to tez nie wiem czy static 
	static int populationSize = 100;					//moze nie static? do przemyslenia	na pewno musi byc parzyste
	static double fitnessRate = 0.8;
	ArrayList <Genotype> genotypes;
	static ArrayList <Zajecia> classes;			//wszystkie zajecia
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
		classes = data.getArrayZajecia();
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
	 * @throws Exception 
	 */
	private void generateFirstPopulation() throws Exception
	{
		Genotype.setTimeSlots(availableTimeSlots);
		while(genotypes.size()<populationSize)
		{
			Genotype gen = new Genotype();
			//gen.repair();
			if(checkIfValid(gen.getChromosome()))
			{
				evaluateFitnessVal(gen);
				genotypes.add(gen);
				//System.out.println(getInterferenceNumber(gen.getChromosome()));
			}
			else
				continue;
		}
	}
	
	/**
	 * Runs genetics algorithm with given number of generations
	 * @throws Exception 
	 */
	public void geneticAlgorithm() throws Exception
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
	
	public Genotype getBestValidChromosome()
	{
		for (Genotype g:genotypes)
			if (orginalCheckifValid(g.getChromosome()))
				return g;
		
		return null;
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
					if(j==clas
					sArray[k][groupId])	//roznica godziny
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
		
		int infNum = getInterferenceNumber(chromosome);
		
		double val = fitnessRate*slotFitness + (1-fitnessRate)*(maxSlot/maxEarly)*earlyFitness;
		if (infNum>0)
			val = val/(2^infNum);
		gen.setFitnessVal(val);
	}
	
	//reprodukuj populacje
	private void breedPopulation() throws Exception {
		
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
				//gen.repair();
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
		/*
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
		*/
		return true;
	}
	
	private int getInterferenceNumber(ArrayList<Integer> chromosome)
	{
		int [][] teacherArray = new int[workingDays*workingHours][teachersCount];
		int [][] classesArray = new int[workingDays*workingHours][studentGroupsCount];
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
		
			teacherArray[j][teacherId]++;
			classesArray[j][classId]++;
			
			
			if(++i%availableClassrooms==0)
				j++;
		}
		int sum=0;
		for (i=0; i<workingDays*workingHours; i++)
			for (j=0; j<teachersCount; j++)
				if (teacherArray[i][j]>1)
					sum++;
		
		for (i=0; i<workingDays*workingHours; i++)
			for (j=0; j<studentGroupsCount; j++)
				if (classesArray[i][j]>1)
					sum++;	
		
		return sum;
	}
	
	
	private boolean orginalCheckifValid(ArrayList<Integer> chromosome)
	{
		boolean [][] teacherArray = new boolean[workingDays*workingHours][teachersCount];
		boolean [][] classesArray = new boolean[workingDays*workingHours][studentGroupsCount];
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
	
	static public Zajecia returnClass(int classNumber)
	{
		return classes.get(classNumber-1);
	}

	public static ArrayList<Zajecia> getClasses() {
		return classes;
	}



	
}
