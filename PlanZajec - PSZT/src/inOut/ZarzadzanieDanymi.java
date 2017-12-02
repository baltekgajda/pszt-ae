/**
 * 
 */
package inOut;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import exceptions.ExceptionBadDataStructure;
import klasyPodstawowe.Klasa;
import klasyPodstawowe.Nauczyciel;
import klasyPodstawowe.Przedmiot;
import klasyPodstawowe.Sala;
import klasyPodstawowe.Zajecia;

/**
 * @author Maciek
 *
 */
public class ZarzadzanieDanymi {
	
	ArrayList <Nauczyciel> arrayNauczyciel = new ArrayList <Nauczyciel>();
	ArrayList <Klasa> arrayKlasa = new ArrayList <Klasa>();
	ArrayList <Przedmiot> arrayPrzedmiot = new ArrayList <Przedmiot>();
	ArrayList <Sala> arraySala= new ArrayList <Sala>();
	ArrayList <Zajecia> arrayZajecia = new ArrayList <Zajecia>();
	Integer salaAmount;
	String filePath;
//	ArrayList <Sala> arraySala;
	
	/**
	 * @return the arrayNauczyciel
	 */
	public ArrayList<Nauczyciel> getArrayNauczyciel() {
		return arrayNauczyciel;
	}

	/**
	 * @param arrayNauczyciel the arrayNauczyciel to set
	 */
	public void setArrayNauczyciel(ArrayList<Nauczyciel> arrayNauczyciel) {
		this.arrayNauczyciel = arrayNauczyciel;
	}

	/**
	 * @return the arrayKlasa
	 */
	public ArrayList<Klasa> getArrayKlasa() {
		return arrayKlasa;
	}

	/**
	 * @param arrayKlasa the arrayKlasa to set
	 */
	public void setArrayKlasa(ArrayList<Klasa> arrayKlasa) {
		this.arrayKlasa = arrayKlasa;
	}

	/**
	 * @return the arrayPrzedmiot
	 */
	public ArrayList<Przedmiot> getArrayPrzedmiot() {
		return arrayPrzedmiot;
	}

	/**
	 * @param arrayPrzedmiot the arrayPrzedmiot to set
	 */
	public void setArrayPrzedmiot(ArrayList<Przedmiot> arrayPrzedmiot) {
		this.arrayPrzedmiot = arrayPrzedmiot;
	}

	/**
	 * @return the salaAmount
	 */
	public Integer getSalaAmount() {
		return salaAmount;
	}

	/**
	 * @param salaAmount the salaAmount to set
	 */
	public void setSalaAmount(Integer salaAmount) {
		this.salaAmount = salaAmount;
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


	public ArrayList<Sala> getArraySala() {
		return arraySala;
	}

	public void setArraySala(ArrayList<Sala> arraySala) {
		this.arraySala = arraySala;
	}

	public ArrayList<Zajecia> getArrayZajecia() {
		return arrayZajecia;
	}
	
	public void setArrayZajecia(ArrayList<Zajecia> arrayZajecia) {
		this.arrayZajecia = arrayZajecia;
	}

	public ZarzadzanieDanymi() {
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
					case "#Nauczyciel":
						processNauczyciel(scanner);
						break;
					case "#Klasa":
						processKlasa(scanner);
						break;
					case "#Przedmiot": 
						processPrzedmiot(scanner);
						break;
					case "#Zajecia":
						processZajecia(scanner);
						break;
					case "#Sala" :
						processSala(scanner);
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
	private void processNauczyciel(Scanner scanner)
	{
		Nauczyciel nauczyciel = null;
		String pomS = new String();
		String imie = new String();
		String nazwisko = new String();
		Integer id;
		while(!(pomS=scanner.next()).equals("#end"))
			{
				id = Integer.parseInt(pomS);
				imie = scanner.next();
				nazwisko = scanner.next();
				nauczyciel = new Nauczyciel(id, imie, nazwisko);
				arrayNauczyciel.add(id, nauczyciel);
			};
			
		
	}
	
	private void processKlasa(Scanner scanner)
	{
		Klasa klasa= null;
		String pomS = new String();
		String nazwa = new String();
		Integer id;
		while(!(pomS=scanner.next()).equals("#end"))
			{
				id = Integer.parseInt(pomS);
				nazwa = scanner.next();
				
				klasa = new Klasa(id, nazwa);
				arrayKlasa.add(id, klasa);
			};
	}
	
	private void processPrzedmiot(Scanner scanner)
	{
		Przedmiot przedmiot= null;
		String pomS = new String();
		String nazwa = new String();
		Integer id;
		while(!(pomS=scanner.next()).equals("#end"))
			{
				id = Integer.parseInt(pomS);
				nazwa = scanner.next();
				
				przedmiot = new Przedmiot(id, nazwa);
				arrayPrzedmiot.add(id, przedmiot);
			};
	}
	
	private void processZajecia(Scanner scanner) throws ExceptionBadDataStructure
	{
		
		String pomS = new String();
		String nazwa = new String();
		Integer id;
		int i = 0;
		while(!(pomS=scanner.next()).equals("#end"))
			{
				Zajecia zajecia = new Zajecia();
				zajecia.setId(i);
			
				if (!pomS.equals("Nauczyciel")) throw new ExceptionBadDataStructure("Powinien byæ identyfikator Nauczyciel");
				if (!scanner.next().equals("=")) throw new ExceptionBadDataStructure("Powinien byæ identyfikator =");
				zajecia.setNauczyciel(arrayNauczyciel.get(scanner.nextInt()));
				
				if (!scanner.next().equals("Klasa")) throw new ExceptionBadDataStructure("Powinien byæ identyfikator Klasa");
				if (!scanner.next().equals("=")) throw new ExceptionBadDataStructure("Powinien byæ identyfikator =");
				zajecia.setKlasa(arrayKlasa.get(scanner.nextInt()));
				
				if (!scanner.next().equals("Przedmiot")) throw new ExceptionBadDataStructure("Powinien byæ identyfikator Przedmiot");
				if (!scanner.next().equals("=")) throw new ExceptionBadDataStructure("Powinien byæ identyfikator =");
				zajecia.setPrzedmiot(arrayPrzedmiot.get(scanner.nextInt()));
				
//				if (!scanner.next().equals("Sala")) throw new ExceptionBadDataStructure("Powinien byæ identyfikator Sala");
//				if (!scanner.next().equals("=")) throw new ExceptionBadDataStructure("Powinien byæ identyfikator =");
//				zajecia.setSala(arraySala.get(scanner.nextInt()));
//				nazwa = scanner.next();
				
				arrayZajecia.add(zajecia);
				i++;
			};
	}
	
	private void processSala(Scanner scanner) {
		setSalaAmount(scanner.nextInt());
		for (Integer i = new Integer (0); i<salaAmount; i++)
		{
			arraySala.add(new Sala (i));
		}
		
		
	}
}
	
