package de.tu_darmstadt.sport.fvf.model;

import java.util.ArrayList;
import java.util.List;

import org.sormula.annotation.Column;
import org.sormula.annotation.Row;
import org.sormula.annotation.Transient;
import org.sormula.annotation.Where;
import org.sormula.annotation.cascade.OneToManyCascade;
import org.sormula.annotation.cascade.SelectCascade;

import de.tu_darmstadt.sport.fvf.helper.CollectionHelper;
import de.tu_darmstadt.sport.fvf.model.map.FrequencyCycleMap;
import de.tu_darmstadt.sport.fvf.model.map.TestMap;

@Where(name="test",fieldNames="testId")
@Row(tableName="frequency_cycle")
public class FrequencyCycle extends Model {
	

	@Column(identity=true)
	private int id;
	private double frequency;
	
	@Column(name="test_id")
	private int testId;
	
	@Transient
	private Test test;
	
	@Transient
	private int errors = -1;
	
	@OneToManyCascade(targetClass=Measurement.class,
			selects=@SelectCascade(sourceParameterFieldNames="id",targetWhereName="frequencyCycle"))
	private List<Measurement> measurements;

	public FrequencyCycle() {
		super(new FrequencyCycleMap());
		measurements = new ArrayList<Measurement>();
	}
	
	public void reset() {
		measurements.clear();
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
	 * @return the frequency
	 */
	public double getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(double frequency) {
		propertyChangeSupport.firePropertyChange("frequency", this.frequency, this.frequency = frequency);
	}

	/**
	 * @return the testId
	 */
	public int getTestId() {
		return testId;
	}

	/**
	 * @param testId the testId to set
	 */
	public void setTestId(int testId) {
		propertyChangeSupport.firePropertyChange("testId", this.testId, this.testId = testId);
	}

	/**
	 * @return the test
	 */
	public Test getTest() {
		if (test == null) {
			test = TestMap.getById(testId);
		}
		return test;
	}

	/**
	 * @param test the test to set
	 */
	public void setTest(Test test) {
		this.test = test;
		if (test == null) {
			setTestId(-1);
		} else {
			setTestId(test.getId());
		}
	}

	/**
	 * @return the measurements
	 */
	public List<Measurement> getMeasurements() {
		return measurements;
	}

	/**
	 * @param measurements the measurements to set
	 */
	public void setMeasurements(List<Measurement> measurements) {
		propertyChangeSupport.firePropertyChange("measurements", this.measurements, this.measurements = measurements);
		for (Measurement measurement : measurements) {
			measurement.setFrequencyCycle(this);
		}
	}

	public void addMeasurement(Measurement measurement) {
		List<Measurement> old = new ArrayList<Measurement>();
		CollectionHelper.copy(old, measurements);
		measurement.setFrequencyCycle(this);
		measurements.add(measurement);
		propertyChangeSupport.firePropertyChange("measurements", old, measurements);
	}

	public void removeMeasurement(Measurement measurement) {
		List<Measurement> old = new ArrayList<Measurement>();
		CollectionHelper.copy(old, measurements);
		measurement.setFrequencyCycle(null);
		measurements.remove(measurement);
		propertyChangeSupport.firePropertyChange("measurements", old, measurements);
	}
	
	public int getErrors() {
		if (errors == -1) {
			errors = 0;
			for (Measurement m : measurements) {
				if (m.isError()) {
					errors++;
				}
			}
		}
		return errors;
	}

	
	/* Operations */
	public void save() {
		super.save();
		for (Measurement m : measurements) {
			m.setFrequencyCycleId(id);
			m.save();
		}
	}
}
