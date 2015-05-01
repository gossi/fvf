package de.tu_darmstadt.sport.fvf.ui.dialogs;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import de.tu_darmstadt.sport.fvf.FVF;
import de.tu_darmstadt.sport.fvf.PreferenceConstants;
import de.tu_darmstadt.sport.fvf.model.Test;
import de.tu_darmstadt.sport.fvf.testrunner.ITestRunnerListener;
import de.tu_darmstadt.sport.fvf.testrunner.TestRunner;

public class TestRunnerDialog extends Dialog implements ITestRunnerListener {

	private Test test;
	private TestRunner testRunner;

	private Image lightBulbOff;
	private Image[] lightBulbs;
	private Color foreground;
	private Color highlightColor;
	private Image yep;
	private Image nope;
	
	private Label leds;
	private Label startFrequency;
	private Label stepFrequency;
	private Label ledDuration;
	private Label ledPause;
	private Label cyclePause;
	private Label stopCriteria;
	private Label frequencyCycles;
	
	private Label errors;
	private Label frequency;
	private Table scoreboard;
	private TableItem sbRuns;
	private TableItem sbResults;
	private TableItem sbErrors;
	
	private Button led1;
	private Button led2;
	private Button led3;
	private Button led4;
	private Button[] ledButtons;
	private Button noLed;
	private int lastFlickeringLed;
	private Thread flickerAnimation;
	
	private boolean measurementRunning = false;
	private boolean measurementFinished = false;
	private boolean measurementStarted = false;
	
	final private static int BTN_STARTSAVE = 2;
	final private static int BTN_CONTINUE = 4;
	final private static int BTN_CONTINUE_WO_ERROR = 5;
	final private static int BTN_RESTART = 6;
	final private static int BTN_RESTART_FREQUENCY = 7;
	
	private Button startSave;
	private Button cancelButton;
	private Button[] measurementButtons;
	
	private Listener keyHandler = new Listener() {
		private char[] validChars =  new char[]{'0','1','2','3','4'};
		public void handleEvent(Event event) {
			
			// leds
			for (char c : validChars) {
				if (c == event.character) {
					markLed(Integer.parseInt(((Character)c).toString()));
				}
			}
			
			// measurement controls
			switch (event.character) {
			case 'q':
				doContinue();
				break;
				
			case 'w':
				doContinueWOError();
				break;
				
			case 'e':
				doRestart();
				break;
				
			case 'r':
				doRestartFrequency();
				break;
			}
		}
	};

	public TestRunnerDialog(Shell parentShell, Test test) {
		super(parentShell);
		setShellStyle(SWT.TITLE | SWT.APPLICATION_MODAL);
		this.test = test;
		testRunner = new TestRunner(test);
		testRunner.addTestRunnerListener(this);
		
		// images
		yep = FVF.getImageDescriptor("icons/yep.png").createImage();
		nope = FVF.getImageDescriptor("icons/nope.png").createImage();
		
		// light bulb images
		lightBulbs = new Image[11];
		for (int i = 0; i <= 10; i++) {
			lightBulbs[i] = FVF.getImageDescriptor("icons/lightbulb/lightbulb-" + (i * 10) + ".png").createImage();
		}
		lightBulbOff = FVF.getImageDescriptor("icons/lightbulb/lightbulb_off.png").createImage();

		// colors
		highlightColor = new Color((Device)parentShell.getDisplay(), 
				PreferenceConverter.getColor(FVF.getDefault().getPreferenceStore(), PreferenceConstants.VISUAL_HIGHLIGHT_COLOR));  
		
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Flimmer Verschmelzungs Frequenz Test");
		
		// prevent escape from closing:
//		newShell.addListener(SWT.Traverse, new Listener() {
//			public void handleEvent(Event e) {
//				if (e.detail == SWT.TRAVERSE_ESCAPE && !cancelButton.getEnabled()) {
//					e.doit = false;
//				}
//			}
//		});
		
		final TestRunnerDialog self = this;
		// add keyboard handler and remove it on shell dispose
		newShell.getDisplay().addFilter(SWT.KeyDown, keyHandler);
		newShell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				e.widget.getDisplay().removeFilter(SWT.KeyDown, keyHandler);
				testRunner.removeTestRunnerListener(self);
			}
		});
	}
	
	@Override
	protected Control createDialogArea(Composite parentComposite) {
		Composite parent = (Composite)super.createDialogArea(parentComposite);
		GridData gridLayout_data = new GridData(SWT.FILL, SWT.FILL, true, true);
		parent.setLayoutData(gridLayout_data);

		Group grpSetup = new Group(parent, SWT.SHADOW_IN);
		grpSetup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpSetup.setText("Test Einstellungen");
		grpSetup.setLayout(new GridLayout(4, true));
		
		// LEDs
		Label lblLeds = new Label(grpSetup, SWT.NONE);
		lblLeds.setText("LEDs:");
		
		leds = new Label(grpSetup, SWT.NONE);
		
		// Start Frequency
		Label lblStartFrequency = new Label(grpSetup, SWT.NONE);
		lblStartFrequency.setText("Startfrequenz:");
		
		startFrequency = new Label(grpSetup, SWT.NONE);

		// LED Duration
		Label lblLedDuration = new Label(grpSetup, SWT.NONE);
		lblLedDuration.setText("LED Dauer:");
		
		ledDuration = new Label(grpSetup, SWT.NONE);
		
		// Step Frequency
		Label lblStepFrequency = new Label(grpSetup, SWT.NONE);
		lblStepFrequency.setText("Frequenz Steigerung:");

		stepFrequency = new Label(grpSetup, SWT.NONE);
		
		// LED Pause
		Label lblLedPause = new Label(grpSetup, SWT.NONE);
		lblLedPause.setText("LED Pause:");
		
		ledPause = new Label(grpSetup, SWT.NONE);
		
		// Cycle Pause
		Label lblCyclePause = new Label(grpSetup, SWT.NONE);
		lblCyclePause.setText("Messzyklus Pause:");
		
		cyclePause = new Label(grpSetup, SWT.NONE);
		
		// Stop Criteria
		Label lblStopCriteria = new Label(grpSetup, SWT.NONE);
		lblStopCriteria.setText("Abbruchbedingung:");
		
		stopCriteria = new Label(grpSetup, SWT.NONE);
		
		// Frequency Cycles
		Label lblFrequencyCycles = new Label(grpSetup, SWT.NONE);
		lblFrequencyCycles.setText("Zyklen pro Frequenz");
		
		frequencyCycles = new Label(grpSetup, SWT.NONE);
		
		// TestRunner Properties
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		composite.setLayout(new GridLayout(4, true));
		
		Label lblFrequenz = new Label(composite, SWT.NONE);
		lblFrequenz.setText("Frequenz:");
		
		frequency = new Label(composite, SWT.NONE);
		frequency.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		
		Label lblErrors = new Label(composite, SWT.NONE);
		lblErrors.setText("Fehler:");
		
		errors = new Label(composite, SWT.NONE);
		errors.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		
		scoreboard = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd_scoreboard = new GridData(SWT.FILL, SWT.FILL, true, false, 4, 1);
		gd_scoreboard.heightHint = 52;
		scoreboard.setLayoutData(gd_scoreboard);
		scoreboard.setHeaderVisible(true);
		scoreboard.setLinesVisible(true);
		
		// build scoreboard header
		(new TableColumn(scoreboard, SWT.NONE)).setWidth(150);
		
		for (int i = 1; i <= test.getFrequencyCycles() + 1; i++) {
			TableColumn col = new TableColumn(scoreboard, SWT.CENTER);
			col.setText(""+i);
			col.setWidth(22);
		}
		
		sbRuns = new TableItem(scoreboard, SWT.NONE);
		sbRuns.setText(0, "Flimmernde LED:");
		sbResults = new TableItem(scoreboard, SWT.NONE);
		sbResults.setText(0, "Proband sagt:");
		sbErrors = new TableItem(scoreboard, SWT.NONE);
		sbErrors.setText(0, "Fehler:");

		
		Composite buttons = new Composite(parent, SWT.NONE);
		buttons.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false, 1, 1));
		GridLayout gl_buttons = new GridLayout(2, false);
		gl_buttons.marginBottom = 20;
		gl_buttons.marginTop = 20;
		gl_buttons.marginRight = 30;
		gl_buttons.marginLeft = 30;
		buttons.setLayout(gl_buttons);
		
		class LedListener extends SelectionAdapter {
			public void widgetSelected(SelectionEvent e) {
				markLed((Integer)e.widget.getData());
			}
		}
		LedListener ledListener = new LedListener();
		
		led1 = new Button(buttons, SWT.NONE);
		GridData gd_led1 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_led1.minimumHeight = 30;
		gd_led1.minimumWidth = 120;
		led1.setLayoutData(gd_led1);
		led1.setText("Links oben");
		led1.setImage(lightBulbOff);
		led1.setData(1);
		led1.addSelectionListener(ledListener);
		foreground = led1.getForeground();
		
		
		led2 = new Button(buttons, SWT.NONE);
		GridData gd_led2 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_led2.minimumHeight = 30;
		gd_led2.minimumWidth = 120;
		led2.setLayoutData(gd_led2);
		led2.setText("Rechts oben");
		led2.setImage(lightBulbOff);
		led2.setData(2);
		led2.addSelectionListener(ledListener);
		
		led3 = new Button(buttons, SWT.NONE);
		GridData gd_led3 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_led3.minimumHeight = 30;
		gd_led3.minimumWidth = 120;
		led3.setLayoutData(gd_led3);
		led3.setText("Links unten");
		led3.setImage(lightBulbOff);
		led3.setData(3);
		led3.addSelectionListener(ledListener);
		
		led4 = new Button(buttons, SWT.NONE);
		GridData gd_led4 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_led4.minimumHeight = 30;
		gd_led4.minimumWidth = 120;
		led4.setLayoutData(gd_led4);
		led4.setText("Rechts unten");
		led4.setImage(lightBulbOff);
		led4.setData(4);
		led4.addSelectionListener(ledListener);
		
		ledButtons = new Button[]{led1, led2, led3, led4};
		
		noLed = new Button(buttons, SWT.NONE);
		noLed.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
		noLed.setText("Keine");
		noLed.setData(0);
		noLed.addSelectionListener(ledListener);
		
		if (test.getLeds() == 2) {
			((GridData)led3.getLayoutData()).exclude = true;
			((GridData)led4.getLayoutData()).exclude = true;
			led1.setText("Links");
			led2.setText("Rechts");
		}
		initDataBindings();

		return parent;
	}

	protected Point getInitialSize() {
		return new Point(800, test.getLeds() == 4 ? 438 : 400);
	}
	
	public void markLed(int led) {
		if (measurementStarted && !measurementFinished) {
			// update scoreboard
			testRunner.getCurrentMeasurement().setPersonLed(led);
			sbResults.setText(testRunner.getCurrentMeasurement().getRun(), ""+led);
			
			// try proceed
			doNextMeasurement();
		}
	}

	@Override
	public void ledOn(final int led, final int brightness, boolean canFlicker) {
		final Button btn = ledButtons[led - 1];
		
		// flicker animation
		if (FVF.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.VISUAL_ANIMATE_FLICKER_LED)
				&& led == lastFlickeringLed && canFlicker) {
			
			flickerAnimation = new Thread(new Runnable() {
				public void run() {
					boolean on = true;
					try {
						while (!Thread.currentThread().isInterrupted()) {
							final Image img = on ? lightBulbs[(int)Math.floor(brightness / 10)] : lightBulbOff;
							getShell().getDisplay().syncExec(new Runnable() {
								public void run() {
									btn.setImage(img);
								}
							});
							on = !on;
							Thread.sleep(200);
						}
					} catch (InterruptedException e) {
						getShell().getDisplay().syncExec(new Runnable() {
							public void run() {
								btn.setImage(lightBulbOff);
							}
						});
					}
				}
			}, "FlickerAnimation");
			flickerAnimation.start();
		} 
		
		// or just turn the light bulb on
		else {
			getShell().getDisplay().syncExec(new Runnable() {
				public void run() {
					btn.setImage(lightBulbs[(int)Math.floor(brightness / 10)]);
					btn.update();
				}
			});
		}
	}

	@Override
	public void ledOff(final int led) {
		if (flickerAnimation != null && !flickerAnimation.isInterrupted()) {
			flickerAnimation.interrupt();
			flickerAnimation = null;
		}
		final Button btn = ledButtons[led - 1];  
		getShell().getDisplay().syncExec(new Runnable() {
			public void run() {
				btn.setImage(lightBulbOff);
				btn.update();
			}
		});
	}
	
	public void ledsOn(final int mode) {
		getShell().getDisplay().syncExec(new Runnable() {
			public void run() {
				for (int i = 0; i < mode; i++) {
					Button btn = ledButtons[i];
					btn.setImage(lightBulbs[10]);
				}
			}
		});
	}
	
	public void ledsOff(final int mode) {
		if (flickerAnimation != null && !flickerAnimation.isInterrupted()) {
			flickerAnimation.interrupt();
			flickerAnimation = null;
		}
		getShell().getDisplay().syncExec(new Runnable() {
			public void run() {
				for (int i = 0; i < mode; i++) {
					Button btn = ledButtons[i];
					btn.setImage(lightBulbOff);
				}
			}
		});
	}

	@Override
	public void measurementCycleStarted(int flickeringLed) {
		measurementRunning = true;
		
		// unhighlight last flickering led
		if (FVF.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.VISUAL_HIGHLIGHT_FLICKER_LED)) {
			if (lastFlickeringLed > 0) {
				final Button btn = ledButtons[lastFlickeringLed - 1];  
				getShell().getDisplay().asyncExec(new Runnable() {
					public void run() {
						btn.setForeground(foreground);
					}
				});
			}
			
			// highlight the new flickering led
			if (flickeringLed > 0) {
				final Button btn = ledButtons[flickeringLed - 1];  
				getShell().getDisplay().asyncExec(new Runnable() {
					public void run() {
						btn.setForeground(highlightColor);
					}
				});
			}
	
			lastFlickeringLed = flickeringLed;
		}
		
		// update scoreboard
		getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				sbRuns.setText(testRunner.getCurrentMeasurement().getRun(), ""+testRunner.getCurrentMeasurement().getLed());
			}
		});
	}

	@Override
	public void measurementCycleFinished() {
		measurementRunning = false;
		doNextMeasurement();
	}

	@Override
	public void measurementFinished() {
		measurementFinished = true;
		
		// ui clean up
		getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				for (Button btn : ledButtons) {
					btn.setForeground(foreground);
					btn.setImage(lightBulbOff);
					btn.setEnabled(false);
				}
				noLed.setEnabled(false);
				startSave.setEnabled(true);
				getShell().getDisplay().beep();
			}
		});
		
	}
	
	@Override
	public void measurementReset() {
		clearScoreboard();
		
		// disable buttons
		getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				for (Button btn : measurementButtons) {
					btn.setEnabled(false);
				}
			}
		});
	}

	@Override
	public void nextFrequency() {
		clearScoreboard();
	}
	
	private void clearScoreboard() {
		getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				for (int i = 1; i <= test.getFrequencyCycles() + 1; i++) {
					sbRuns.setText(i, "");
					sbResults.setText(i, "");
					sbErrors.setImage(i, null);
				}
			}
		});
	}
	
	private void doNextMeasurement() {
		doNextMeasurement(false, true);
	}
	
	private void doNextMeasurement(boolean proceed, boolean allowError) {
		if (measurementRunning) {
			return;
		}
		
		int personLed = testRunner.getCurrentMeasurement().getPersonLed();
		int led = testRunner.getCurrentMeasurement().getLed();

//		if (personLed == -1) {
//			if (!measurementRunning) {
//				getShell().getDisplay().asyncExec(new Runnable() {
//					public void run() {
//						cancelButton.setEnabled(true);
//					}
//				});
//			}
//			return;
//		}
		
		final boolean error = allowError ? personLed != led : false;
		testRunner.getCurrentMeasurement().setError(error);

		// enable buttons when errors are present
		if (error && !proceed) {
			getShell().getDisplay().asyncExec(new Runnable() {
				public void run() {
					for (Button btn : measurementButtons) {
						btn.setEnabled(true);
					}
				}
			});
			return;
		}
		
		final int run = testRunner.getCurrentMeasurement().getRun();
		getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				sbErrors.setImage(run, error ? nope : yep);
			}
		});
		
		// disable buttons
		getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				for (Button btn : measurementButtons) {
					btn.setEnabled(false);
				}
//				cancelButton.setEnabled(false);
			}
		});
		
		// update errors
		if (error) {
			testRunner.setErrors(testRunner.getErrors() + 1);
		}
		
		// proceed
		(new Thread(testRunner, "TestRunner")).start();
	}
	
	private void doContinueWOError() {
		doNextMeasurement(true, false);
	}
	
	private void doContinue() {
		doNextMeasurement(true, true);
	}
	
	private void doRestart() {
		testRunner.reset();
		(new Thread(testRunner, "TestRunner")).start();
	}
	
	private void doRestartFrequency() {
		testRunner.resetFrequency();
		(new Thread(testRunner, "TestRunner")).start();
	}
	
	protected void createButtonsForButtonBar(Composite parent) {
		GridLayout buttonLayout = (GridLayout)parent.getLayout();
		buttonLayout.makeColumnsEqualWidth = false;
		
		// Weiter
		Button continueButton = createButton(parent, BTN_CONTINUE, "Weiter [Q]", false);
		continueButton.setEnabled(false);
		continueButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				doContinue();
			}
		});
		
		// Weiter ohne Fehler
		Button noErrorButton = createButton(parent, BTN_CONTINUE_WO_ERROR, "Weiter ohne Fehler [W]", false);
		noErrorButton.setEnabled(false);
		noErrorButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				doContinueWOError();
			}
		});
		
		// Neustart
		Button restart = createButton(parent, BTN_RESTART, "Neustart [E]", false);
		restart.setEnabled(false);
		restart.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				doRestart();
			}
		});
		
		// Neustart mit aktueller Frequenz
		Button restartFrequency = createButton(parent, BTN_RESTART_FREQUENCY, "Neustart mit aktueller Frequenz [R]", false);
		restartFrequency.setEnabled(false);
		restartFrequency.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				doRestartFrequency();
			}
		});
		
		// Abbruch
		cancelButton = createButton(parent, CANCEL, "Abbruch", false);
		cancelButton.setEnabled(true);
		
		measurementButtons = new Button[]{continueButton, noErrorButton, restart, restartFrequency};
		
		// Start & Speichern
		startSave = createButton(parent, BTN_STARTSAVE, "Start", true);
		startSave.setEnabled(true);
		startSave.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (measurementFinished) {
					setReturnCode(OK);
					close();
				} else {
					measurementStarted = true;
					(new Thread(testRunner, "TestRunner")).start();
					startSave.setEnabled(false);
					startSave.setText("Speichern");
//					cancelButton.setEnabled(false);
				}
			}
		});
	}

	
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue ledsObserveTextObserveWidget = SWTObservables.observeText(leds);
		IObservableValue testLedsObserveValue = BeansObservables.observeValue(test, "leds");
		bindingContext.bindValue(ledsObserveTextObserveWidget, testLedsObserveValue, null, null);
		//
		IObservableValue startFrequencyObserveTextObserveWidget = SWTObservables.observeText(startFrequency);
		IObservableValue testStartFrequencyObserveValue = BeansObservables.observeValue(test, "startFrequency");
		bindingContext.bindValue(startFrequencyObserveTextObserveWidget, testStartFrequencyObserveValue, null, null);
		//
		IObservableValue stepFrequencyObserveTextObserveWidget = SWTObservables.observeText(stepFrequency);
		IObservableValue testFrequencyStepObserveValue = BeansObservables.observeValue(test, "frequencyStep");
		bindingContext.bindValue(stepFrequencyObserveTextObserveWidget, testFrequencyStepObserveValue, null, null);
		//
		IObservableValue ledDurationObserveTextObserveWidget = SWTObservables.observeText(ledDuration);
		IObservableValue testLedDurationObserveValue = BeansObservables.observeValue(test, "ledDuration");
		bindingContext.bindValue(ledDurationObserveTextObserveWidget, testLedDurationObserveValue, null, null);
		//
		IObservableValue ledPauseObserveTextObserveWidget = SWTObservables.observeText(ledPause);
		IObservableValue testLedPauseObserveValue = BeansObservables.observeValue(test, "ledPause");
		bindingContext.bindValue(ledPauseObserveTextObserveWidget, testLedPauseObserveValue, null, null);
		//
		IObservableValue cyclePauseObserveTextObserveWidget = SWTObservables.observeText(cyclePause);
		IObservableValue testCyclePauseObserveValue = BeansObservables.observeValue(test, "cyclePause");
		bindingContext.bindValue(cyclePauseObserveTextObserveWidget, testCyclePauseObserveValue, null, null);
		//
		IObservableValue stopCriteriaObserveTextObserveWidget = SWTObservables.observeText(stopCriteria);
		IObservableValue testStopCriteriaObserveValue = BeansObservables.observeValue(test, "stopCriteria");
		bindingContext.bindValue(stopCriteriaObserveTextObserveWidget, testStopCriteriaObserveValue, null, null);
		//
		IObservableValue errorsObserveTextObserveWidget = SWTObservables.observeText(errors);
		IObservableValue testRunnerErrorsObserveValue = BeansObservables.observeValue(testRunner, "errors");
		bindingContext.bindValue(errorsObserveTextObserveWidget, testRunnerErrorsObserveValue, null, null);
		//
		IObservableValue frequencyObserveTextObserveWidget = SWTObservables.observeText(frequency);
		IObservableValue testRunnerFrequencyObserveValue = BeansObservables.observeValue(testRunner, "frequency");
		bindingContext.bindValue(frequencyObserveTextObserveWidget, testRunnerFrequencyObserveValue, null, null);
		//
		IObservableValue lblFrequencyCyclesObserveTextObserveWidget = SWTObservables.observeText(frequencyCycles);
		IObservableValue testFrequencyCyclesObserveValue = BeansObservables.observeValue(test, "frequencyCycles");
		bindingContext.bindValue(lblFrequencyCyclesObserveTextObserveWidget, testFrequencyCyclesObserveValue, null, null);
		//
		return bindingContext;
	}
}
