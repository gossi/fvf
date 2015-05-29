package de.tu_darmstadt.sport.fvf.model.map;

import java.util.List;

import org.sormula.Database;
import org.sormula.SormulaException;
import org.sormula.Table;

import de.tu_darmstadt.sport.fvf.database.DatabaseConnection;
import de.tu_darmstadt.sport.fvf.model.Model;
import de.tu_darmstadt.sport.fvf.model.Person;

public class PersonMap extends AbstractMap{

	private Table<Person> table;
	
	public PersonMap() { 
		try {
			Database database = DatabaseConnection.getInstance().getDatabase();
			table = database.getTable(Person.class);
		} catch (SormulaException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(Person person) {
		try {
			table.delete(person);
		} catch (SormulaException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void delete(Model model) {
		delete((Person)model);		
	}

	public void save(Person person) {
		try {
			table.save(person);
		} catch (SormulaException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void save(Model model) {
		save((Person)model);
	}
	
	public List<Person> selectAll() {
		try {
			return table.selectAll();
		} catch (SormulaException e) {
			e.printStackTrace();
		}
		return null;
	}

}
