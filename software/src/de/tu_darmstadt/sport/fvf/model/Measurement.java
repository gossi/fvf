package de.tu_darmstadt.sport.fvf.model;

import org.sormula.annotation.Column;
import org.sormula.annotation.Transient;
import org.sormula.annotation.Where;

import de.tu_darmstadt.sport.fvf.model.map.MeasurementMap;

@Where(name="frequencyCycle",fieldNames="frequencyCycleId")
public class Measurement extends Model {
	
	@Column(identity=true)
	private int id;
	private int run;
	private int led;
	private boolean error;
	
	@Column(name="person_led")
	private int personLed = -1;
	
	@Column(name="frequency_cycle_id")
	private int frequencyCycleId;
	
	@Transient
	private FrequencyCycle frequencyCycle;

	public Measurement() {
		super(new MeasurementMap());
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		propertyChangeSupport.firePropertyChange("id", this.id, this.id = id);
	}

	/**
	 * @return the run
	 */
	public int getRun() {
		return run;
	}

	/**
	 * @param run the run to set
	 */
	public void setRun(int run) {
		propertyChangeSupport.firePropertyChange("run", this.run, this.run = run);
	}

	/**
	 * Gets the LED which was flickering at that run
	 * 
	 * @return the led
	 */
	public int getLed() {
		return led;
	}

	/**
	 * Sets the LED which is flickering at this run
	 * 
	 * @param led the led to set
	 */
	public void setLed(int led) {
		propertyChangeSupport.firePropertyChange("led", this.led, this.led = led);
	}

	/**
	 * Gets the LED which the person thinks was flickering
	 * 
	 * @return the personLed
	 */
	public int getPersonLed() {
		return personLed;
	}

	/**
	 * Sets the LED which the person thinks is flickering
	 * 
	 * @param personLed the personLed to set
	 */
	public void setPersonLed(int personLed) {
		propertyChangeSupport.firePropertyChange("personLed", this.personLed, this.personLed = personLed);
	}

	/**
	 * @return the frequencyCycleId
	 */
	public int getFrequencyCycleId() {
		return frequencyCycleId;
	}

	/**
	 * @param frequencyCycleId the frequencyCycleId to set
	 */
	public void setFrequencyCycleId(int frequencyCycleId) {
		propertyChangeSupport.firePropertyChange("frequencyCycleId", this.frequencyCycleId, this.frequencyCycleId = frequencyCycleId);
	}

	/**
	 * @return the frequencyCycle
	 */
	public FrequencyCycle getFrequencyCycle() {
		return frequencyCycle;
	}

	/**
	 * @param frequencyCycle the frequencyCycle to set
	 */
	public void setFrequencyCycle(FrequencyCycle frequencyCycle) {
		this.frequencyCycle = frequencyCycle;
		if (frequencyCycle == null) {
			setFrequencyCycleId(-1);
		} else {
			setFrequencyCycleId(frequencyCycle.getId());
		}
	}

	/**
	 * @return the error
	 */
	public boolean isError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(boolean error) {
		propertyChangeSupport.firePropertyChange("error", this.error, this.error = error);
	}



}
