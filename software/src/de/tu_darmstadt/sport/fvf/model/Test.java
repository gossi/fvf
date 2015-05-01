package de.tu_darmstadt.sport.fvf.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.sormula.annotation.Column;
import org.sormula.annotation.ExplicitType;
import org.sormula.annotation.Transient;
import org.sormula.annotation.Where;
import org.sormula.annotation.cascade.OneToManyCascade;
import org.sormula.annotation.cascade.SelectCascade;
import org.sormula.translator.standard.DateTranslator;

import de.tu_darmstadt.sport.fvf.FVF;
import de.tu_darmstadt.sport.fvf.PreferenceConstants;
import de.tu_darmstadt.sport.fvf.helper.CollectionHelper;
import de.tu_darmstadt.sport.fvf.model.map.TestMap;

@Where(name="person",fieldNames="personId")
@ExplicitType(type=Date.class, translator=DateTranslator.class)
public class Test extends Model {
	
	@Column(identity=true)
	private int id;
	private Date date;
	private String note;
	private int leds = 4;
	
	@Column(name="start_frequency")
	private double startFrequency = 25;
	
	@Column(name="frequency_step")
	private double frequencyStep = 5;
	
	@Column(name="led_duration")
	private double ledDuration = 1.25;
	
	@Column(name="led_pause")
	private double ledPause = 0.2;
	
	@Column(name="cycle_pause")
	private double cyclePause = 1.5;
	
	@Column(name="frequency_cycles")
	private int frequencyCycles = 8;
	
	private double brightness;
	
	@Column(name="stop_criteria")
	private int stopCriteria = 5;
	
	@Column(name="person_id")
	private int personId;
	
	@Transient
	private Person person;
	
	@OneToManyCascade(targetClass=FrequencyCycle.class,
			selects=@SelectCascade(sourceParameterFieldNames="id",targetWhereName="test"))
	private List<FrequencyCycle> frequencyRuns;
	
	public Test() {
		super(new TestMap());
		frequencyRuns = new ArrayList<FrequencyCycle>();
		
		// load test parameters from preference store
		IPreferenceStore store = FVF.getDefault().getPreferenceStore();
		
		leds = store.getInt(PreferenceConstants.TEST_LEDS);
		startFrequency = store.getDouble(PreferenceConstants.TEST_FREQUENCY_START);
		frequencyStep = store.getDouble(PreferenceConstants.TEST_FREQUENCY_STEP);
		frequencyCycles = store.getInt(PreferenceConstants.TEST_CYCLES);
		cyclePause = store.getDouble(PreferenceConstants.TEST_CYCLE_PAUSE);
		ledDuration = store.getDouble(PreferenceConstants.TEST_LED_DURATION);
		ledPause = store.getDouble(PreferenceConstants.TEST_LED_PAUSE);
		stopCriteria = store.getInt(PreferenceConstants.TEST_STOP_CRITERIA);
		brightness = store.getInt(PreferenceConstants.TEST_BRIGHTNESS);
	}
	
	public void reset() {
		frequencyRuns.clear();
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
		TestMap.register(this);
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		propertyChangeSupport.firePropertyChange("date", this.date, this.date = date);
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note == null ? "" : note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		propertyChangeSupport.firePropertyChange("note", this.note, this.note = note);
	}

	/**
	 * @return the leds
	 */
	public int getLeds() {
		return leds;
	}

	/**
	 * @param leds the leds to set
	 */
	public void setLeds(int leds) {
		propertyChangeSupport.firePropertyChange("leds", this.leds, this.leds = leds);
	}

	/**
	 * @return the start_frequency
	 */
	public double getStartFrequency() {
		return startFrequency;
	}

	/**
	 * @param start_frequency the start_frequency to set
	 */
	public void setStartFrequency(double startFrequency) {
		propertyChangeSupport.firePropertyChange("startFrequency", this.startFrequency, this.startFrequency = startFrequency);
	}

	/**
	 * @return the frequencyStep
	 */
	public double getFrequencyStep() {
		return frequencyStep;
	}

	/**
	 * @param frequencyStep the frequencyStep to set
	 */
	public void setFrequencyStep(double frequencyStep) {
		propertyChangeSupport.firePropertyChange("frequencyStep", this.frequencyStep, this.frequencyStep = frequencyStep);
	}

	/**
	 * @return the ledDuration
	 */
	public double getLedDuration() {
		return ledDuration;
	}

	/**
	 * @param ledDuration the ledDuration to set
	 */
	public void setLedDuration(double ledDuration) {
		propertyChangeSupport.firePropertyChange("ledDuration", this.ledDuration, this.ledDuration = ledDuration);
	}
	
	/**
	 * @return the ledPause
	 */
	public double getLedPause() {
		return ledPause;
	}

	/**
	 * @param ledPause the ledPause to set
	 */
	public void setLedPause(double ledPause) {
		propertyChangeSupport.firePropertyChange("ledPause", this.ledPause, this.ledPause = ledPause);
	}

	/**
	 * @return the cyclePause
	 */
	public double getCyclePause() {
		return cyclePause;
	}

	/**
	 * @param cyclePause the cyclePause to set
	 */
	public void setCyclePause(double cyclePause) {
		propertyChangeSupport.firePropertyChange("cyclePause", this.cyclePause, this.cyclePause = cyclePause);
	}

	/**
	 * @return the cycles
	 */
	public int getFrequencyCycles() {
		return frequencyCycles;
	}

	/**
	 * @param frequencyCycles the frequencyCycles to set
	 */
	public void setFrequencyCycles(int frequencyCycles) {
		propertyChangeSupport.firePropertyChange("frequencyCycles", this.frequencyCycles, this.frequencyCycles = frequencyCycles);
	}

	/**
	 * @return the stopCriteria
	 */
	public int getStopCriteria() {
		return stopCriteria;
	}

	/**
	 * @param stopCriteria the stopCriteria to set
	 */
	public void setStopCriteria(int stopCriteria) {
		propertyChangeSupport.firePropertyChange("stopCriteria", this.stopCriteria, this.stopCriteria = stopCriteria);
	}

	/**
	 * @return the personId
	 */
	public int getPersonId() {
		return personId;
	}

	/**
	 * @param personId the personId to set
	 */
	public void setPersonId(int personId) {
		propertyChangeSupport.firePropertyChange("personId", this.personId, this.personId = personId);
	}

	/**
	 * @return the runs
	 */
	public List<FrequencyCycle> getFrequencyRuns() {
		return frequencyRuns;
	}

	/**
	 * @param runs the runs to set
	 */
	public void setFrequencyRuns(List<FrequencyCycle> frequencyRuns) {
		propertyChangeSupport.firePropertyChange("frequencyRuns", this.frequencyRuns, this.frequencyRuns = frequencyRuns);
		for (FrequencyCycle cycle : frequencyRuns) {
			cycle.setTest(this);
		}
	}
	
	public void addFrequencyRun(FrequencyCycle frequencyCycle) {
		List<FrequencyCycle> old = new ArrayList<FrequencyCycle>();
		CollectionHelper.copy(old, frequencyRuns);
		frequencyCycle.setTest(this);
		frequencyRuns.add(frequencyCycle);
		propertyChangeSupport.firePropertyChange("frequencyRuns", old, frequencyRuns);
	}
	
	public void removeFrequencyRun(FrequencyCycle frequencyCycle) {
		List<FrequencyCycle> old = new ArrayList<FrequencyCycle>();
		CollectionHelper.copy(old, frequencyRuns);
		frequencyCycle.setTest(null);
		frequencyRuns.remove(frequencyCycle);
		propertyChangeSupport.firePropertyChange("frequencyRuns", old, frequencyRuns);
	}

	/**
	 * @return the person
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * @param person the person to set
	 */
	public void setPerson(Person person) {
		this.person = person;
		if (person == null) {
			setPersonId(-1);
		} else {
			setPersonId(person.getId());
		}
	}

	/**
	 * @return the brightness
	 */
	public double getBrightness() {
		return brightness;
	}

	/**
	 * @param brightness the brightness to set
	 */
	public void setBrightness(double brightness) {
		propertyChangeSupport.firePropertyChange("brightness", this.brightness, this.brightness = brightness);
	}

	public double getAchievedFrequency() {
		ArrayList<Double> frequencies = new ArrayList<Double>();
		for (FrequencyCycle cycle : frequencyRuns) {
			if (cycle.getErrors() < stopCriteria) {
				frequencies.add(cycle.getFrequency());
			}
		}

		double max = -1;
		for (double frequency : frequencies) {
			max = Math.max(max, frequency);
		}

		return max;
	}
	
	
	/* Operations */
	public void save() {
		super.save();
		for (FrequencyCycle run : frequencyRuns) {
			run.setTestId(id);
			run.save();
		}
	}
}
