package de.tu_darmstadt.sport.fvf.ui.wizards;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import de.tu_darmstadt.sport.fvf.converter.DoubleToSpinnerConverter;
import de.tu_darmstadt.sport.fvf.converter.SpinnerToDoubleConverter;
import de.tu_darmstadt.sport.fvf.model.Test;

public class TestSetupPage extends WizardPage {

	private Test test;
	private Spinner stopCriteria;
	private Combo mode;
	private Spinner startFrequency;
	private Spinner stepFrequency;
	private Spinner ledDuration;
	private Spinner ledPause;
	private Spinner cyclePause;
	private Spinner frequencyCycles;
	private Text text;
//	private Text light;
//	private Text dark;
	private boolean firstRun;
	
	public TestSetupPage() {
		super("Neuer Test");
		setTitle("Neuer Test");
		setDescription("Parameter für den neuen Test eingeben");
		firstRun = true;
	}

	@Override
	public void createControl(Composite parent) {
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout gl_container = new GridLayout(2, true);
		container.setLayout(gl_container);
		
		Label lblModus = new Label(container, SWT.NONE);
		lblModus.setText("LEDs");
		
		mode = new Combo(container, SWT.NONE);
		mode.setItems(new String[] {"2", "4"});
		mode.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblStartfrequenz = new Label(container, SWT.NONE);
		lblStartfrequenz.setText("Startfrequenz [Hz]");
		
		startFrequency = new Spinner(container, SWT.BORDER);
		startFrequency.setIncrement(50);
		startFrequency.setSelection(2500);
		startFrequency.setPageIncrement(500);
		startFrequency.setMaximum(10000);
		startFrequency.setDigits(2);
		startFrequency.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setText("Frequenzsteigerung [Hz]");
		
		stepFrequency = new Spinner(container, SWT.BORDER);
		stepFrequency.setIncrement(100);
		stepFrequency.setSelection(500);
		stepFrequency.setPageIncrement(500);
		stepFrequency.setMaximum(10000);
		stepFrequency.setDigits(2);
		stepFrequency.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblMesszyklenProFrequenz = new Label(container, SWT.NONE);
		lblMesszyklenProFrequenz.setText("Messzyklen pro Frequenz");
		
		frequencyCycles = new Spinner(container, SWT.BORDER);
		frequencyCycles.setMaximum(-1);
		frequencyCycles.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblPausendauerProMesszyklus = new Label(container, SWT.NONE);
		lblPausendauerProMesszyklus.setText("Pausendauer pro Messzyklus [s]");
		
		cyclePause = new Spinner(container, SWT.BORDER);
		cyclePause.setDigits(2);
		cyclePause.setMaximum(10000);
		cyclePause.setIncrement(10);
		cyclePause.setSelection(150);
		cyclePause.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblAnzeigedauerProLed = new Label(container, SWT.NONE);
		lblAnzeigedauerProLed.setText("Anzeigedauer pro LED [s]");
		
		ledDuration = new Spinner(container, SWT.BORDER);
		ledDuration.setPageIncrement(100);
		ledDuration.setDigits(2);
		ledDuration.setIncrement(25);
		ledDuration.setMaximum(10000);
		ledDuration.setSelection(125);
		ledDuration.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblPausendauerProLed = new Label(container, SWT.NONE);
		lblPausendauerProLed.setText("Pausendauer pro LED [s]");
		
		ledPause = new Spinner(container, SWT.BORDER);
		ledPause.setDigits(2);
		ledPause.setIncrement(10);
		ledPause.setMaximum(10000);
		ledPause.setSelection(20);
		ledPause.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblAbbruchkriteriumfalschnennungen = new Label(container, SWT.NONE);
		lblAbbruchkriteriumfalschnennungen.setText("Abbruchkriterium (Falschnennungen)");
		
		stopCriteria = new Spinner(container, SWT.BORDER);
		stopCriteria.setSelection(7);
		stopCriteria.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		final ControlDecoration decor = new ControlDecoration(stopCriteria, SWT.TOP | SWT.LEFT);
		stopCriteria.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				try {
					int stopCrit = Integer.valueOf(stopCriteria.getText());
					int cycles = Integer.valueOf(frequencyCycles.getText());
					if (stopCrit >= cycles && !firstRun) {
						decor.setDescriptionText("Abbruchbedingung sollte kleiner als Messzyklen pro Frequenz sein");
						Image image = FieldDecorationRegistry.getDefault()
								.getFieldDecoration(FieldDecorationRegistry.DEC_WARNING)
								.getImage();
						decor.setImage(image);
						decor.show();
					} else {
						decor.hide();
					}
				} catch (Exception ex) {
				}
			}
		});
		
//		Label lblHelldunkelQuotient = new Label(container, SWT.NONE);
//		lblHelldunkelQuotient.setText("Hell/Dunkel Quotient");
//		
//		Composite composite = new Composite(container, SWT.NONE);
//		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
//		GridLayout gl_composite = new GridLayout(4, false);
//		gl_composite.marginHeight = 0;
//		gl_composite.marginWidth = 0;
//		composite.setLayout(gl_composite);
//		
//		Label lblHell = new Label(composite, SWT.NONE);
//		lblHell.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
//		lblHell.setText("Hell");
//		
//		light = new Text(composite, SWT.BORDER);
//		light.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
//		
//		Label lblZuDunkel = new Label(composite, SWT.NONE);
//		lblZuDunkel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
//		lblZuDunkel.setText("zu Dunkel");
//		
//		dark = new Text(composite, SWT.BORDER);
//		dark.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
//		
		Label lblBemerkung = new Label(container, SWT.NONE);
		lblBemerkung.setText("Bemerkung");
		new Label(container, SWT.NONE);

		text = new Text(container, SWT.BORDER | SWT.MULTI);
		GridData gd_text = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 2);
		gd_text.heightHint = 55;
		text.setLayoutData(gd_text);
		initDataBindings();
		
		firstRun = false;
	}

	/**
	 * @return the test
	 */
	public Test getTest() {
		return test;
	}

	/**
	 * @param test the test to set
	 */
	public void setTest(Test test) {
		this.test = test;
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue modeObserveSelectionObserveWidget = SWTObservables.observeSelection(mode);
		IObservableValue testLedsObserveValue = BeansObservables.observeValue(test, "leds");
		bindingContext.bindValue(modeObserveSelectionObserveWidget, testLedsObserveValue, null, null);
		//
		IObservableValue startFrequencyObserveSelectionObserveWidget = SWTObservables.observeSelection(startFrequency);
		IObservableValue testStartFrequencyObserveValue = BeansObservables.observeValue(test, "startFrequency");
		UpdateValueStrategy strategy = new UpdateValueStrategy();
		strategy.setConverter(new SpinnerToDoubleConverter());
		UpdateValueStrategy strategy_1 = new UpdateValueStrategy();
		strategy_1.setConverter(new DoubleToSpinnerConverter());
		bindingContext.bindValue(startFrequencyObserveSelectionObserveWidget, testStartFrequencyObserveValue, strategy, strategy_1);
		//
		IObservableValue stepFrequencyObserveSelectionObserveWidget = SWTObservables.observeSelection(stepFrequency);
		IObservableValue testFrequencyStepObserveValue = BeansObservables.observeValue(test, "frequencyStep");
		UpdateValueStrategy strategy_2 = new UpdateValueStrategy();
		strategy_2.setConverter(new SpinnerToDoubleConverter());
		UpdateValueStrategy strategy_3 = new UpdateValueStrategy();
		strategy_3.setConverter(new DoubleToSpinnerConverter());
		bindingContext.bindValue(stepFrequencyObserveSelectionObserveWidget, testFrequencyStepObserveValue, strategy_2, strategy_3);
		//
		IObservableValue ledDurationObserveSelectionObserveWidget = SWTObservables.observeSelection(ledDuration);
		IObservableValue testLedDurationObserveValue = BeansObservables.observeValue(test, "ledDuration");
		UpdateValueStrategy strategy_4 = new UpdateValueStrategy();
		strategy_4.setConverter(new SpinnerToDoubleConverter());
		UpdateValueStrategy strategy_5 = new UpdateValueStrategy();
		strategy_5.setConverter(new DoubleToSpinnerConverter());
		bindingContext.bindValue(ledDurationObserveSelectionObserveWidget, testLedDurationObserveValue, strategy_4, strategy_5);
		//
		IObservableValue ledPauseObserveSelectionObserveWidget = SWTObservables.observeSelection(ledPause);
		IObservableValue testLedPauseObserveValue = BeansObservables.observeValue(test, "ledPause");
		UpdateValueStrategy strategy_6 = new UpdateValueStrategy();
		strategy_6.setConverter(new SpinnerToDoubleConverter());
		UpdateValueStrategy strategy_7 = new UpdateValueStrategy();
		strategy_7.setConverter(new DoubleToSpinnerConverter());
		bindingContext.bindValue(ledPauseObserveSelectionObserveWidget, testLedPauseObserveValue, strategy_6, strategy_7);
		//
		IObservableValue cyclePauseObserveSelectionObserveWidget = SWTObservables.observeSelection(cyclePause);
		IObservableValue testCyclePauseObserveValue = BeansObservables.observeValue(test, "cyclePause");
		UpdateValueStrategy strategy_8 = new UpdateValueStrategy();
		strategy_8.setConverter(new SpinnerToDoubleConverter());
		UpdateValueStrategy strategy_9 = new UpdateValueStrategy();
		strategy_9.setConverter(new DoubleToSpinnerConverter());
		bindingContext.bindValue(cyclePauseObserveSelectionObserveWidget, testCyclePauseObserveValue, strategy_8, strategy_9);
		//
		IObservableValue stopCriteriaObserveSelectionObserveWidget = SWTObservables.observeSelection(stopCriteria);
		IObservableValue testStopCriteriaObserveValue = BeansObservables.observeValue(test, "stopCriteria");
		bindingContext.bindValue(stopCriteriaObserveSelectionObserveWidget, testStopCriteriaObserveValue, null, null);
		//
		IObservableValue spinnerObserveSelectionObserveWidget = SWTObservables.observeSelection(frequencyCycles);
		IObservableValue testFrequencyCyclesObserveValue = BeansObservables.observeValue(test, "frequencyCycles");
		bindingContext.bindValue(spinnerObserveSelectionObserveWidget, testFrequencyCyclesObserveValue, null, null);
		//
//		IObservableValue lightObserveTextObserveWidget = SWTObservables.observeText(light, SWT.Modify);
//		IObservableValue testLightObserveValue = BeansObservables.observeValue(test, "light");
//		bindingContext.bindValue(lightObserveTextObserveWidget, testLightObserveValue, null, null);
//		//
//		IObservableValue darkObserveTextObserveWidget = SWTObservables.observeText(dark, SWT.Modify);
//		IObservableValue testDarkObserveValue = BeansObservables.observeValue(test, "dark");
//		bindingContext.bindValue(darkObserveTextObserveWidget, testDarkObserveValue, null, null);
		//
		return bindingContext;
	}
}
