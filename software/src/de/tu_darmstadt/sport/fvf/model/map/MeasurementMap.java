package de.tu_darmstadt.sport.fvf.model.map;

import java.util.List;

import org.sormula.Database;
import org.sormula.SormulaException;
import org.sormula.Table;

import de.tu_darmstadt.sport.fvf.database.DatabaseConnection;
import de.tu_darmstadt.sport.fvf.model.Measurement;
import de.tu_darmstadt.sport.fvf.model.Model;

public class MeasurementMap extends AbstractMap{

	private Table<Measurement> table;
	
	public MeasurementMap() { 
		try {
			Database database = DatabaseConnection.getInstance().getDatabase();
			table = database.getTable(Measurement.class);
		} catch (SormulaException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(Measurement test) {
		try {
			table.delete(test);
		} catch (SormulaException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void delete(Model model) {
		delete((Measurement)model);		
	}

	public void save(Measurement test) {
		try {
			table.save(test);
		} catch (SormulaException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void save(Model model) {
		save((Measurement)model);
	}
	
	public List<Measurement> selectAll() {
		try {
			return table.selectAll();
		} catch (SormulaException e) {
			e.printStackTrace();
		}
		return null;
	}
}
