/**
 * 
 */
package inOut;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import klasyPodstawowe.Klasa;
import klasyPodstawowe.Nauczyciel;
import klasyPodstawowe.Przedmiot;

/**
 * @author Maciek
 *
 */
public class ZarzadzanieDanymi {
	
	ArrayList <Nauczyciel> arrayNauczyciel = new ArrayList <Nauczyciel>();
	ArrayList <Klasa> arrayKlasa = new ArrayList <Klasa>();
	ArrayList <Przedmiot> arrayPrzedmiot = new ArrayList <Przedmiot>();
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
						processZajecia();
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
		while((pomS=scanner.next()).equals("#end"))
			{
				id = Integer.parseInt(pomS);
				imie = scanner.next();
				nazwisko = scanner.next();
				nauczyciel = new Nauczyciel(id, imie, nazwisko);
				arrayNauczyciel.add(nauczyciel);
			};
			
		
	}
	
	private void processKlasa(Scanner scanner)
	{
		Klasa klasa= null;
		String pomS = new String();
		String nazwa = new String();
		Integer id;
		while((pomS=scanner.next()).equals("#end"))
			{
				id = Integer.parseInt(pomS);
				nazwa = scanner.next();
				
				klasa = new Klasa(id, nazwa);
				arrayKlasa.add(klasa);
			};
	}
	
	private void processPrzedmiot(Scanner scanner)
	{
		Przedmiot przedmiot= null;
		String pomS = new String();
		String nazwa = new String();
		Integer id;
		while((pomS=scanner.next()).equals("#end"))
			{
				id = Integer.parseInt(pomS);
				nazwa = scanner.next();
				
				przedmiot = new Przedmiot(id, nazwa);
				arrayPrzedmiot.add(przedmiot);
			};
	}
	
	private void processZajecia()
	{
		
	}
	
	private void processSala(Scanner scanner) {
		// TODO Auto-generated method stub
		
	}
}
	
