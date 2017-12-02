/**
 * 
 */
package klasyPodstawowe;

/**
 * @author Maciek
 *
 */
public class Przedmiot {
	Integer id;
	String nazwa;
	
	public Przedmiot(Integer id, String nazwa) {
		super();
		this.id = id;
		this.nazwa = nazwa;
	}
	
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
