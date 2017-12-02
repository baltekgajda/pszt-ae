/**
 * 
 */
package klasyPodstawowe;

/**
 * @author Maciek
 *
 */
public class Klasa {
	public Klasa(Integer id, String nazwa) 
	{
		setId(id);
		setNazwa(nazwa);
	}
	Integer id;
	String nazwa = new String();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
}
