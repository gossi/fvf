package de.tu_darmstadt.sport.fvf.model.map;

import java.util.List;

import org.sormula.Database;
import org.sormula.SormulaException;
import org.sormula.Table;

import de.tu_darmstadt.sport.fvf.database.DatabaseConnection;
import de.tu_darmstadt.sport.fvf.model.FrequencyCycle;
import de.tu_darmstadt.sport.fvf.model.Model;

public class FrequencyCycleMap extends AbstractMap{

	private Table<FrequencyCycle> table;
	
	public FrequencyCycleMap() { 
		try {
			Database database = DatabaseConnection.getInstance().getDatabase();
			table = database.getTable(FrequencyCycle.class);
		} catch (SormulaException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(FrequencyCycle test) {
		try {
			table.delete(test);
		} catch (SormulaException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void delete(Model model) {
		delete((FrequencyCycle)model);		
	}

	public void save(FrequencyCycle test) {
		try {
			table.save(test);
		} catch (SormulaException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void save(Model model) {
		save((FrequencyCycle)model);
	}
	
	public List<FrequencyCycle> selectAll() {
		try {
			return table.selectAll();
		} catch (SormulaException e) {
			e.printStackTrace();
		}
		return null;
	}
}
