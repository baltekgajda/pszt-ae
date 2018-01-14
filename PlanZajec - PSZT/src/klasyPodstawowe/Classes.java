/**
 * 
 */
package klasyPodstawowe;

/**
 * @author Maciek
 *
 */
public class Classes {
	Integer id;
	Subject subject;
	Clas clas;
	Teacher teacher;
	Classroom classroom;
	Integer godzina;
	
	
	public Classes(Integer id, Subject subject, Clas clas, Teacher teacher, Classroom classroom) {
		super();
		this.id = id;
		this.subject = subject;
		this.clas = clas;
		this.teacher = teacher;
		this.classroom = classroom;
	}
	
	public Classes() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Subject getPrzedmiot() {
		return subject;
	}
	public void setPrzedmiot(Subject subject) {
		this.subject = subject;
	}
	public Clas getKlasa() {
		return clas;
	}
	public void setKlasa(Clas clas) {
		this.clas = clas;
	}
	public Teacher getNauczyciel() {
		return teacher;
	}
	public void setNauczyciel(Teacher teacher) {
		this.teacher = teacher;
	} 
	public Classroom getSala() {
		return classroom;
	}
	public void setSala(Classroom classroom) {
		this.classroom = classroom;
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
