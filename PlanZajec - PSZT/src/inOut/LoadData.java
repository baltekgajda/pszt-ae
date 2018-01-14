/**
 * 
 */
package inOut;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import exceptions.ExceptionBadDataStructure;
import klasyPodstawowe.Clas;
import klasyPodstawowe.Teacher;
import klasyPodstawowe.Subject;
import klasyPodstawowe.Classroom;
import klasyPodstawowe.Classes;

/**
 * @author Maciek
 *
 */
public class LoadData {
	
	ArrayList <Teacher> arrayTeacher = new ArrayList <Teacher>();
	ArrayList <Clas> arrayClas = new ArrayList <Clas>();
	ArrayList <Subject> arraySubject = new ArrayList <Subject>();
	ArrayList <Classroom> arrayClassroom= new ArrayList <Classroom>();
	ArrayList <Classes> arrayClasses = new ArrayList <Classes>();
	Integer classesAmount;
	String filePath;
	
	/**
	 * @return the arrayTeacher
	 */
	public ArrayList<Teacher> getArrayNauczyciel() {
		return arrayTeacher;
	}

	/**
	 * @param arrayTeacher the arrayTeacher to set
	 */
	public void setArrayNauczyciel(ArrayList<Teacher> arrayNauczyciel) {
		this.arrayTeacher = arrayNauczyciel;
	}

	/**
	 * @return the arrayClas
	 */
	public ArrayList<Clas> getArrayKlasa() {
		return arrayClas;
	}

	/**
	 * @param arrayClas the arrayClas to set
	 */
	public void setArrayKlasa(ArrayList<Clas> arrayKlasa) {
		this.arrayClas = arrayKlasa;
	}

	/**
	 * @return the arraySubject
	 */
	public ArrayList<Subject> getArrayPrzedmiot() {
		return arraySubject;
	}

	/**
	 * @param arraySubject the arraySubject to set
	 */
	public void setArrayPrzedmiot(ArrayList<Subject> arrayPrzedmiot) {
		this.arraySubject = arrayPrzedmiot;
	}

	/**
	 * @return the classesAmount
	 */
	public Integer getSalaAmount() {
		return classesAmount;
	}

	/**
	 * @param classesAmount the classesAmount to set
	 */
	public void setSalaAmount(Integer salaAmount) {
		this.classesAmount = salaAmount;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	public ArrayList<Classroom> getArraySala() {
		return arrayClassroom;
	}

	public void setArraySala(ArrayList<Classroom> arraySala) {
		this.arrayClassroom = arraySala;
	}

	public ArrayList<Classes> getArrayZajecia() {
		return arrayClasses;
	}
	
	public void setArrayZajecia(ArrayList<Classes> arrayZajecia) {
		this.arrayClasses = arrayZajecia;
	}

	public LoadData() {
		// TODO Auto-generated constructor stub
	}
	
	public void loadData() throws IOException
	{
	 String pom = new String();
	 FileReader fileReader = new FileReader(filePath);
	 Scanner scanner = new Scanner(fileReader);
			
			try {
				int i=0;
				
				while (scanner.hasNext())
				{
					switch (scanner.next())
					{
					case "#Nauczyciele":
						processTeacher(scanner);
						break;
					case "#Klasy":
						processClass(scanner);
						break;
					case "#Przedmioty": 
						processSubject(scanner);
						break;
					case "#Classes":
						processClasses(scanner);
						break;
					case "#Sale" :
						processClassroom(scanner);
						break; 
					}
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
			}
			
			scanner.close();
	}

/**
 * @param scanner scanner which is used by the function
 * 
 */
	private void processTeacher(Scanner scanner)
	{
		Teacher teacher = null;
		String pomS = new String();
		String imie = new String();
		String nazwisko = new String();
		Integer id;
		while(!(pomS=scanner.next()).equals("#end"))
			{
				id = Integer.parseInt(pomS);
				imie = scanner.next();
				nazwisko = scanner.next();
				teacher = new Teacher(id, imie, nazwisko);
				arrayTeacher.add(id, teacher);
			};
			
		
	}
	
	private void processClass(Scanner scanner)
	{
		Clas clas= null;
		String pomS = new String();
		String nazwa = new String();
		Integer id;
		while(!(pomS=scanner.next()).equals("#end"))
			{
				id = Integer.parseInt(pomS);
				nazwa = scanner.next();
				
				clas = new Clas(id, nazwa);
				arrayClas.add(id, clas);
			};
	}
	
	private void processSubject(Scanner scanner)
	{
		Subject subject= null;
		String pomS = new String();
		String nazwa = new String();
		Integer id;
		while(!(pomS=scanner.next()).equals("#end"))
			{
				id = Integer.parseInt(pomS);
				nazwa = scanner.next();
				
				subject = new Subject(id, nazwa);
				arraySubject.add(id, subject);
			};
	} 
	 
	private void processClasses(Scanner scanner) throws ExceptionBadDataStructure
	{
		
		String pomS = new String();
		String nazwa = new String();
		Integer id;
		int i = 1;
		while(!(pomS=scanner.next()).equals("#end"))
			{
				Classes classes = new Classes();
				classes.setId(i);
			
				if (!pomS.equals("Teacher")) throw new ExceptionBadDataStructure("Powinien byæ identyfikator Teacher");
				if (!scanner.next().equals("=")) throw new ExceptionBadDataStructure("Powinien byæ identyfikator =");
				classes.setNauczyciel(arrayTeacher.get(scanner.nextInt()));
				
				if (!scanner.next().equals("Clas")) throw new ExceptionBadDataStructure("Powinien byæ identyfikator Clas");
				if (!scanner.next().equals("=")) throw new ExceptionBadDataStructure("Powinien byæ identyfikator =");
				classes.setKlasa(arrayClas.get(scanner.nextInt()));
				
				if (!scanner.next().equals("Subject")) throw new ExceptionBadDataStructure("Powinien byæ identyfikator Subject");
				if (!scanner.next().equals("=")) throw new ExceptionBadDataStructure("Powinien byæ identyfikator =");
				classes.setPrzedmiot(arraySubject.get(scanner.nextInt()));
				
				if (!scanner.next().equals("Ile")) throw new ExceptionBadDataStructure("Powinien byæ identyfikator Ile");
				if (!scanner.next().equals("=")) throw new ExceptionBadDataStructure("Powiinien byæ identyfikator =, Line: "+pomS+" Id: "+i);
				int count = scanner.nextInt();
				for(int j=0;j<count;j++)
					{
						arrayClasses.add(classes);
						i++;
					}
					
			};
	}
	
	private void processClassroom(Scanner scanner) {
		setSalaAmount(scanner.nextInt());
		for (Integer i = new Integer (0); i<classesAmount; i++)
			arrayClassroom.add(new Classroom (i));
	}
}
	
