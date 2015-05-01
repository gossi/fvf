package de.tu_darmstadt.sport.fvf.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import de.tu_darmstadt.sport.fvf.model.map.AbstractMap;

public class Model {

	private AbstractMap map;
	protected PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	
	protected Model (AbstractMap map) {
		this.map = map;
	}
	
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}
	
	public void save() {
		this.map.save(this);
	}
	
	public void delete() {
		this.map.delete(this);
	}
}
