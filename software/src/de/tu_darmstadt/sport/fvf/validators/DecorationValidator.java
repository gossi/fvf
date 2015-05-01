package de.tu_darmstadt.sport.fvf.validators;

import org.eclipse.jface.fieldassist.ControlDecoration;

public class DecorationValidator {

	protected String errorText;
	protected ControlDecoration controlDecoration;
	
	public DecorationValidator (ControlDecoration controlDecoration, String errorText) {
		this.controlDecoration = controlDecoration;
		this.errorText = errorText;
	}
}
