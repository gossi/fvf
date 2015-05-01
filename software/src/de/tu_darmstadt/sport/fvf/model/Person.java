package de.tu_darmstadt.sport.fvf.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.sormula.SormulaException;
import org.sormula.annotation.Column;
import org.sormula.annotation.Transient;
import org.sormula.annotation.cascade.OneToManyCascade;
import org.sormula.annotation.cascade.SelectCascade;

import de.tu_darmstadt.sport.fvf.database.DatabaseConnection;
import de.tu_darmstadt.sport.fvf.helper.CollectionHelper;
import de.tu_darmstadt.sport.fvf.model.map.PersonMap;

public class Person extends Model {
	@Column(identity=true)
	private int id;
	@Column(name="first_name")
	private String firstName = "";
	@Column(name="last_name")
	private String lastName = "";
	private int age;
	
	@Transient
	private List<Double> achievedFrequencies = null;
	
	@OneToManyCascade(targetClass=Test.class,
			selects=@SelectCascade(sourceParameterFieldNames="id",targetWhereName="person"))
	private List<Test> tests;
	
	public Person() {
		super(new PersonMap());
		
		addPropertyChangeListener("tests", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				achievedFrequencies = null;
			}
		});
	}
	
	public static Person findOneById(int id) {
		try {
			return DatabaseConnection.getInstance().getDatabase().getTable(Person.class).select(id);
		} catch (SormulaException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		propertyChangeSupport.firePropertyChange("id", this.id, this.id = id);
	}

	/**
	 * @return the name
	 */
	public String getFirstName() {
		return firstName == null ? "" : firstName;
	}

	/**
	 * @param name the name to set
	 */
	public void setFirstName(String firstName) {
		propertyChangeSupport.firePropertyChange("firstName", this.firstName, this.firstName = firstName);
	}

	/**
	 * @return the name
	 */
	public String getLastName() {
		return lastName == null ? "" : lastName;
	}

	/**
	 * @param name the name to set
	 */
	public void setLastName(String lastName) {
		propertyChangeSupport.firePropertyChange("lastName", this.lastName, this.lastName = lastName);
	}
	
	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		propertyChangeSupport.firePropertyChange("age", this.age, this.age = age);
	}

	/**
	 * @return the tests
	 */
	public List<Test> getTests() {
		return tests;
	}

	/**
	 * @param tests the tests to set
	 */
	public void setTests(List<Test> tests) {
		propertyChangeSupport.firePropertyChange("tests", this.tests, this.tests = tests);
		for (Test test : tests) {
			test.setPerson(this);
		}
	}
	
	public void addTest(Test test) {
		test.setPerson(this);
		List<Test> old = new ArrayList<Test>();
		CollectionHelper.copy(old, tests);
		tests.add(test);
		propertyChangeSupport.firePropertyChange("tests", old, tests);
	}
	
	public void removeTest(Test test) {
		test.setPerson(null);
		List<Test> old = new ArrayList<Test>();
		CollectionHelper.copy(old, tests);
		tests.remove(test);
		propertyChangeSupport.firePropertyChange("tests", old, tests);
	}
	
	public String toString() {
		return firstName + " " + lastName + " (" + age + ") #" + id;
	}
	
	public String getDisplayName() {
		return firstName + " " + lastName;
	}
	
	public List<Double> getAchievedFrequencies() {
		if (achievedFrequencies == null) {
			achievedFrequencies = new ArrayList<Double>();
			for (Test test : tests) {
				double achieved = test.getAchievedFrequency();
				if (achieved != -1) {
					achievedFrequencies.add(achieved);
				}
			}
		}
		return achievedFrequencies;
	}
	
	public double getMaxFrequency() {
		double max = -1;
		for (double f : getAchievedFrequencies()) {
			max = Math.max(max, f);
		}

		return max;
	}
	
	public double getAvgFrequency() {
		List<Double> frequencies = getAchievedFrequencies();
		if (frequencies.size() == 0) {
			return -1;
		}
		
		double total = 0;
		for (double f : frequencies) {
			total += f;
		}

		return total / frequencies.size();
	}
}
