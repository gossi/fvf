package de.tu_darmstadt.sport.fvf.validators;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.fieldassist.ControlDecoration;

public class IntValidator extends DecorationValidator implements IValidator {
	
	public IntValidator(ControlDecoration controlDecoration, String errorText) {
		super(controlDecoration, errorText);
	}

	@Override
	public IStatus validate(Object value) {
		if (value instanceof Integer) {
			value = String.valueOf(value);
		}
		if (value instanceof String) {
			String s = (String) value;
			if (!s.matches("\\d*")) {
				controlDecoration.show();
				return ValidationStatus.error(errorText);
			}
		}
		controlDecoration.hide();
		return ValidationStatus.ok();
	}

}
