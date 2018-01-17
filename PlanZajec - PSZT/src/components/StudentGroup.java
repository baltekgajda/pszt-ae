package components;

/**
 * Class containing basic information about student groups
 */
public class StudentGroup {

	Integer id;
	String name = new String();

	public StudentGroup(Integer id, String name) {
		setId(id);
		setName(name);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return getId().toString() + " " + getName().toString() + " ";
	}
}
