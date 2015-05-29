package de.tu_darmstadt.sport.fvf.controller;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;

import de.tu_darmstadt.sport.fvf.FVF;
import de.tu_darmstadt.sport.fvf.model.Measurement;

public class MeasurementController extends LabelProvider implements
		ITableLabelProvider, IStructuredContentProvider {

	private List<Measurement> model;
	private Image yep = FVF.getImageDescriptor("icons/yep.png").createImage();
	private Image nope = FVF.getImageDescriptor("icons/nope.png").createImage();
	
	@SuppressWarnings("unchecked")
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		model = (List<Measurement>)newInput;
	}

	@Override
	public Object[] getElements(Object parent) {
		return model.toArray();
	}

	@Override
	public Image getColumnImage(Object obj, int index) {
		if (index == 0) {
			return ((Measurement)obj).getLed() == ((Measurement)obj).getPersonLed() ? yep : nope;
		}
		return null;
	}

	@Override
	public String getColumnText(Object obj, int index) {
		Measurement m = (Measurement)obj;
		switch (index) {
		case 0:
			return ""+m.getRun();
			
		case 1:
			return ""+m.getLed();
			
		case 2:
			return ""+m.getPersonLed();
		}
		return "";
	}

}
