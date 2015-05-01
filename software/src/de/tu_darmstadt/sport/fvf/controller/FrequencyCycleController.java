package de.tu_darmstadt.sport.fvf.controller;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;

import de.tu_darmstadt.sport.fvf.FVF;
import de.tu_darmstadt.sport.fvf.model.FrequencyCycle;
import de.tu_darmstadt.sport.fvf.model.Test;

public class FrequencyCycleController extends LabelProvider implements
		ITableLabelProvider, IStructuredContentProvider {

	private List<FrequencyCycle> model;
	private Image yep = FVF.getImageDescriptor("icons/yep.png").createImage();
	private Image nope = FVF.getImageDescriptor("icons/nope.png").createImage();
	
	@SuppressWarnings("unchecked")
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		model = (List<FrequencyCycle>)newInput;
	}

	@Override
	public Object[] getElements(Object parent) {
		return model.toArray();
	}

	@Override
	public Image getColumnImage(Object obj, int index) {
		if (index == 0) {
			Test test = ((FrequencyCycle)obj).getTest();
			if (test != null) {
				return ((FrequencyCycle)obj).getErrors() < test.getStopCriteria() ? yep : nope;
			}
		}
		return null;
	}

	@Override
	public String getColumnText(Object obj, int index) {
		FrequencyCycle f = (FrequencyCycle)obj;
		switch (index) {
		case 0:
			return f.getFrequency() + " Hz";
			
		case 1:
			return ""+f.getErrors();
		}
		return "";
	}

}
