package de.tu_darmstadt.sport.fvf.model.map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sormula.Database;
import org.sormula.SormulaException;
import org.sormula.Table;

import de.tu_darmstadt.sport.fvf.database.DatabaseConnection;
import de.tu_darmstadt.sport.fvf.model.Model;
import de.tu_darmstadt.sport.fvf.model.Test;

public class TestMap extends AbstractMap{

	private Table<Test> table;
	private static Map<Integer, Test> map = new HashMap<Integer, Test>();
	
	public TestMap() { 
		try {
			Database database = DatabaseConnection.getInstance().getDatabase();
			table = database.getTable(Test.class);
		} catch (SormulaException e) {
			e.printStackTrace();
		}
	}
	
	public static void register(Test test) {
		TestMap.map.put(test.getId(), test);
	}
	
	public static void unregister(Test test) {
		TestMap.map.remove(test.getId());
	}
	
	public static Test getById(int id) {
		if (TestMap.map.containsKey(id)) {
			return TestMap.map.get(id);
		}
		return null;
	}
	
	public void delete(Test test) {
		try {
			table.delete(test);
			TestMap.unregister(test);
		} catch (SormulaException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void delete(Model model) {
		delete((Test)model);		
	}

	public void save(Test test) {
		try {
			table.save(test);
		} catch (SormulaException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void save(Model model) {
		save((Test)model);
	}
	
	public List<Test> selectAll() {
		try {
			return table.selectAll();
		} catch (SormulaException e) {
			e.printStackTrace();
		}
		return null;
	}
}
