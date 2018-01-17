package components;

/**
 * Class containing all information about teacher objects
 */
public class Teacher {

	Integer id;
	String firstName;
	String lastName;

	public Teacher(Integer id, String firstName, String lastName) {
		setId(id);
		setFirstName(firstName);
		setLastName(lastName);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String toString() {
		return id + " " + firstName + " " + lastName;
	}
}
