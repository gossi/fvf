package de.tu_darmstadt.sport.fvf.controller;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;

import de.tu_darmstadt.sport.fvf.FVF;
import de.tu_darmstadt.sport.fvf.model.Person;

public class PersonController extends LabelProvider implements
		ITableLabelProvider, IStructuredContentProvider {

	private List<Person> model;
	private Image personImage = FVF.getImageDescriptor("icons/person.png").createImage();
	
	@SuppressWarnings("unchecked")
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		model = (List<Person>)newInput;
	}

	@Override
	public Object[] getElements(Object parent) {
		return model.toArray();
	}

	@Override
	public Image getColumnImage(Object obj, int index) {
		return personImage;
	}

	@Override
	public String getColumnText(Object obj, int index) {
		return ((Person)obj).getFirstName() + " " + ((Person)obj).getLastName();
	}

}
