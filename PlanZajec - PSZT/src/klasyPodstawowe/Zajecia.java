/**
 * 
 */
package klasyPodstawowe;

/**
 * @author Maciek
 *
 */
public class Zajecia {
	Integer id;
	Przedmiot przedmiot;
	Klasa klasa;
	Nauczyciel nauczyciel;
	Sala sala;
	Integer godzina;
	
	
	public Zajecia(Integer id, Przedmiot przedmiot, Klasa klasa, Nauczyciel nauczyciel, Sala sala) {
		super();
		this.id = id;
		this.przedmiot = przedmiot;
		this.klasa = klasa;
		this.nauczyciel = nauczyciel;
		this.sala = sala;
	}
	
	public Zajecia() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Przedmiot getPrzedmiot() {
		return przedmiot;
	}
	public void setPrzedmiot(Przedmiot przedmiot) {
		this.przedmiot = przedmiot;
	}
	public Klasa getKlasa() {
		return klasa;
	}
	public void setKlasa(Klasa klasa) {
		this.klasa = klasa;
	}
	public Nauczyciel getNauczyciel() {
		return nauczyciel;
	}
	public void setNauczyciel(Nauczyciel nauczyciel) {
		this.nauczyciel = nauczyciel;
	} 
	public Sala getSala() {
		return sala;
	}
	public void setSala(Sala sala) {
		this.sala = sala;
	}
	
	public Integer getGodzina() {
		return godzina;
	}

	public void setGodzina(Integer godzina) {
		this.godzina = godzina;
	}

	public String toString()
	{
		return getId().toString() + " " + getKlasa().toString() + " " + getNauczyciel().toString() /*+ getSala().toString() + getGodzina().toString()*/ ;
		
	}
}
