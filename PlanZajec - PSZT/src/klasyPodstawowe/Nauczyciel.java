/**
 * 
 */
package klasyPodstawowe;

/**
 * @author Maciek
 * klasa zawiera wszystkie informacje na temat nauczyciela
 */
public class Nauczyciel {

	Integer id;
	String imie;
	String nazwisko;
	
	public Nauczyciel(Integer id, String imie, String nazwisko)
	{
		setId(id);
		setImie(imie);
		setNazwisko(nazwisko);
	}
	
	public String getImie() {
		return imie;
	}
	public void setImie(String imie) {
		this.imie = imie;
	}
	public String getNazwisko() {
		return nazwisko;
	}
	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String toString()
	{
		return id + imie + nazwisko;
		
	}
}
