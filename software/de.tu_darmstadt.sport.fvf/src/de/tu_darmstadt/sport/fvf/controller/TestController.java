package de.tu_darmstadt.sport.fvf.controller;

import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;

import de.tu_darmstadt.sport.fvf.model.Test;

public class TestController extends LabelProvider implements
		ITableLabelProvider, IStructuredContentProvider {

	private List<Test> model;
	
	@SuppressWarnings("unchecked")
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		model = (List<Test>)newInput;
	}

	@Override
	public Object[] getElements(Object parent) {
		return model.toArray();
	}

	@Override
	public Image getColumnImage(Object obj, int index) {
		return null;
	}

	@Override
	public String getColumnText(Object obj, int index) {
		Test t = (Test)obj;
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN);
		double achieved = t.getAchievedFrequency();
		switch(index) {
		case 0:
			return ""+t.getId();
			
		case 1:
			return df.format(t.getDate());
			
		case 2:
			return ""+t.getLeds();
			
		case 3:
			return ""+t.getStartFrequency();
			
		case 4:
			return achieved == -1 ? "N/A" : ""+achieved;
			
		case 5:
			return ""+t.getFrequencyCycles();
			
		case 6:
			return ""+t.getStopCriteria();
		}
		
		return null;
	}

}
