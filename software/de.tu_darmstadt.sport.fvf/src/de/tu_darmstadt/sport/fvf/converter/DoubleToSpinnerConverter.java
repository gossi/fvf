package de.tu_darmstadt.sport.fvf.converter;

import org.eclipse.core.databinding.conversion.Converter;

public class DoubleToSpinnerConverter extends Converter {

	public DoubleToSpinnerConverter() {
		super(double.class, int.class);
	}

	@Override
	public Object convert(Object fromObject) {
		return (int)((Double)fromObject * 100);
	}

}
