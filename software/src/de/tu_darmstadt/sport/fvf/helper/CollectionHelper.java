package de.tu_darmstadt.sport.fvf.helper;

import java.util.Collections;
import java.util.List;

public class CollectionHelper {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void copy(List destination, List source) {
		while (destination.size() < source.size()) {
			destination.add(null);
		}
		Collections.copy(destination, source);
	}
}
