package de.tu_darmstadt.sport.fvf.converter;

import org.eclipse.core.databinding.conversion.Converter;

public class SpinnerToDoubleConverter extends Converter {

	public SpinnerToDoubleConverter() {
		super(int.class, double.class);
	}

	@Override
	public Object convert(Object fromObject) {
		return (double)((Integer)fromObject / 100);
	}

}
