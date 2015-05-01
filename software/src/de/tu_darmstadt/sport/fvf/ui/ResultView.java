package de.tu_darmstadt.sport.fvf.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.internal.IWorkbenchThemeConstants;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.themes.ITheme;

import de.tu_darmstadt.sport.fvf.FVF;
import de.tu_darmstadt.sport.fvf.controller.FrequencyCycleController;
import de.tu_darmstadt.sport.fvf.controller.MeasurementController;
import de.tu_darmstadt.sport.fvf.controller.TestController;
import de.tu_darmstadt.sport.fvf.model.FrequencyCycle;
import de.tu_darmstadt.sport.fvf.model.Person;
import de.tu_darmstadt.sport.fvf.model.Test;

@SuppressWarnings("restriction")
public class ResultView extends ViewPart {
	
	public static final String ID = "de.tu_darmstadt.sport.fvf.ui.ResultView";
	
	private Person person = null;
	private TableViewer viewer;
	private Label avgFrequency;
	private Label maxFrequency;
	private Label lastFrequency;
	private CTabFolder testFolder;
	private Map<Test, CTabItem> testFolderMap = new HashMap<Test, CTabItem>();
	private Image testIcon = FVF.getImageDescriptor("icons/test.png").createImage();
	private Image setupIcon = FVF.getImageDescriptor("icons/wrench.png").createImage();
	private Image noteIcon = FVF.getImageDescriptor("icons/note.png").createImage();
	private Color bgStart;
	private Color bgEnd;
	private ResultView self = this;
	
	private PropertyChangeListener personListener = new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent evt) {
			updateStatistics();
			viewer.setInput(person.getTests());
		}
	};
	
	private IPartListener focusListener = new IPartListener() {
		public void partOpened(IWorkbenchPart part) {}
		public void partDeactivated(IWorkbenchPart part) {}		
		public void partClosed(IWorkbenchPart part) {}			
		public void partBroughtToTop(IWorkbenchPart part) {}
		
		public void partActivated(IWorkbenchPart part) {
			if (part == self) {
				testFolder.setSelectionBackground(new Color[]{bgStart, bgEnd}, new int[]{100}, true);
			} else {
				testFolder.setSelectionBackground(new Color[]{
						getViewSite().getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE), 
						getViewSite().getShell().getDisplay().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND)}, 
						new int[]{100}, true);	
			}
		}
	};

	public ResultView() {
		IWorkbench workbench = FVF.getDefault().getWorkbench();
		ITheme theme = workbench.getThemeManager().getCurrentTheme();
		ColorRegistry colreg = theme.getColorRegistry();

		bgStart = colreg.get(IWorkbenchThemeConstants.ACTIVE_TAB_BG_START);
		bgEnd = colreg.get(IWorkbenchThemeConstants.ACTIVE_TAB_BG_END);
	}
	
	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);

		person = Person.findOneById(Integer.parseInt(getViewSite().getSecondaryId()));
		person.addPropertyChangeListener("tests", personListener);
		
		setPartName(person.getDisplayName());
		
		getSite().getPage().addPartListener(focusListener);
	}

	@Override
	public void dispose() {
		person.removePropertyChangeListener(personListener);
		getSite().getPage().removePartListener(focusListener);
		super.dispose();
	}
	
	public Person getPerson() {
		return person;
	}

	@Override
	public void createPartControl(Composite parent) {
		GridLayout gl_parent = new GridLayout(1, false);
		gl_parent.verticalSpacing = 3;
		gl_parent.marginWidth = 0;
		gl_parent.marginHeight = 0;
		gl_parent.horizontalSpacing = 0;
		parent.setLayout(gl_parent);

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(6, true));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		
		Label lblMaximalErreichteFrequenz = new Label(composite, SWT.NONE);
		lblMaximalErreichteFrequenz.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblMaximalErreichteFrequenz.setText("Frequenz (Max):");
		
		maxFrequency = new Label(composite, SWT.NONE);
		maxFrequency.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblFrequenzavg = new Label(composite, SWT.NONE);
		lblFrequenzavg.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblFrequenzavg.setText("Frequenz (Avg):");
		
		avgFrequency = new Label(composite, SWT.NONE);
		avgFrequency.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lblLetzteFrequenz = new Label(composite, SWT.NONE);
		lblLetzteFrequenz.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblLetzteFrequenz.setText("Letzte Frequenz:");
		
		lastFrequency = new Label(composite, SWT.NONE);
		lastFrequency.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		SashForm sashForm = new SashForm(parent, SWT.VERTICAL);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
			
		viewer = new TableViewer(sashForm, SWT.BORDER | SWT.FULL_SELECTION);
		Table table = viewer.getTable();
		table.setHeaderVisible(true);
		
		TableColumn nr = new TableColumn(table, SWT.NONE);
		nr.setText("Nr");
		nr.setWidth(40);
		
		TableColumn date = new TableColumn(table, SWT.NONE);
		date.setText("Datum");
		date.setWidth(70);
		
		TableColumn leds = new TableColumn(table, SWT.NONE);
		leds.setText("LEDs");
		leds.setWidth(50);
		
		TableColumn startFrequency = new TableColumn(table, SWT.NONE);
		startFrequency.setText("Startfrequenz");
		startFrequency.setWidth(90);
		
		TableColumn achievedFrequence = new TableColumn(table, SWT.NONE);
		achievedFrequence.setText("Erreichte Frequenz");
		achievedFrequence.setWidth(110);
		
		TableColumn cycles = new TableColumn(table, SWT.NONE);
		cycles.setText("Messzyklen pro Frequenz");
		cycles.setWidth(150);
		
		TableColumn stopCriteria = new TableColumn(table, SWT.NONE);
		stopCriteria.setText("Abbruchbedingung");
		stopCriteria.setWidth(120);
			
		testFolder = new CTabFolder(sashForm, SWT.BORDER | SWT.CLOSE);
		testFolder.setSelectionBackground(new Color[]{bgStart, bgEnd}, new int[]{100}, true);
		testFolder.setTabHeight(18);
		testFolder.setSize(testFolder.getSize().x, 200);
		
		
		sashForm.setWeights(new int[] {1, 1});
		
		TestController controller = new TestController();
		viewer.setContentProvider(controller);
		viewer.setLabelProvider(controller);
		viewer.setInput(person.getTests());
		viewer.getTable().addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				openTest();
			}
		});
		viewer.getTable().addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_RETURN) {
					openTest();
				}
			}
		});

		updateStatistics();
	}

	private void updateStatistics() {
		DecimalFormat df = new DecimalFormat("#.##");
		
		double max = person.getMaxFrequency();
		maxFrequency.setText(max == -1 ? "N/A" : "" + df.format(max));
		
		double avg = person.getAvgFrequency();
		avgFrequency.setText(avg == -1 ? "N/A" : "" + df.format(avg));
		
		double last = -1;
		List<Test> tests = person.getTests();
		if (tests != null) {
			int i = tests.size() - 1;
			while (last == -1 && i > 0) {
				last = tests.get(i).getAchievedFrequency();
				i--;
			}
		}
		lastFrequency.setText(last == -1 ? "N/A" : "" + df.format(last));
	}

	private void openTest() {
		IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
		final Test test = (Test)selection.getFirstElement();

		// switch to test tab
		if (testFolderMap.containsKey(test)) {
			testFolder.setSelection(testFolderMap.get(test));
		}

		// open new tab
		else if (selection.size() > 0) {
			CTabItem tab = new CTabItem(testFolder, SWT.NONE);
			tab.setText("#" + test.getId());
			tab.setImage(testIcon);
			tab.setControl(createTestContents(tab, test));
			tab.addDisposeListener(new DisposeListener() {
				public void widgetDisposed(DisposeEvent e) {
					testFolderMap.remove(test);
				}
			});
			testFolderMap.put(test, tab);
			testFolder.setSelection(tab);
		}
	}
	
	private Control createTestContents(CTabItem tab, Test test) {
		CTabFolder folder = new CTabFolder(tab.getParent(), SWT.BORDER | SWT.FLAT | SWT.BOTTOM);
		folder.setSelectionBackground(getViewSite().getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE));
		folder.setTabHeight(22);
		
		// setup
		CTabItem setupItem = new CTabItem(folder, SWT.NONE);
		setupItem.setText("Parameter");
		setupItem.setImage(setupIcon);
		
		Composite setup = new Composite(folder, SWT.NONE);
		setup.setLayout(new GridLayout(2, false));
		
		(new Label(setup, SWT.NONE)).setText("LEDs");
		(new Label(setup, SWT.NONE)).setText(""+test.getLeds());
		
		(new Label(setup, SWT.NONE)).setText("Startfrquenz");
		(new Label(setup, SWT.NONE)).setText(""+test.getStartFrequency());
		
		(new Label(setup, SWT.NONE)).setText("Frequenzsteigerung");
		(new Label(setup, SWT.NONE)).setText(""+test.getFrequencyStep());
		
		(new Label(setup, SWT.NONE)).setText("Messzyklen pro Frequenz");
		(new Label(setup, SWT.NONE)).setText(""+test.getFrequencyCycles());
		
		(new Label(setup, SWT.NONE)).setText("Pausendauer pro Messzyklus");
		(new Label(setup, SWT.NONE)).setText(""+test.getCyclePause());
		
		(new Label(setup, SWT.NONE)).setText("Anzeigedauer pro LED");
		(new Label(setup, SWT.NONE)).setText(""+test.getLedDuration());
		
		(new Label(setup, SWT.NONE)).setText("Pausendauer pro LED");
		(new Label(setup, SWT.NONE)).setText(""+test.getLedPause());
		
		(new Label(setup, SWT.NONE)).setText("Abbruchkriterium (Falschnennungen)");
		(new Label(setup, SWT.NONE)).setText(""+test.getStopCriteria());
		
//		(new Label(setup, SWT.NONE)).setText("Helligkeit");
//		(new Label(setup, SWT.NONE)).setText(""+test.getBrightness());
		
		setupItem.setControl(setup);
		
		// notes
		CTabItem notesItem = new CTabItem(folder, SWT.NONE);
		notesItem.setText("Bemerkung");
		notesItem.setImage(noteIcon);
		
		Composite notes = new Composite(folder, SWT.NONE);
		notes.setLayout(new GridLayout());
		
		(new Label(notes, SWT.NONE)).setText("Bemerkung:");
		(new Label(notes, SWT.NONE)).setText(test.getNote());
		
		notesItem.setControl(notes);
		
		// data
		CTabItem dataItem = new CTabItem(folder, SWT.NONE);
		dataItem.setText("Daten");
		dataItem.setImage(testIcon);
		
		Composite data = new Composite(folder, SWT.NONE);
		data.setLayout(new GridLayout(2, false));
		
		// -- cycles
		TableViewer cycleViewer = new TableViewer(data, SWT.SINGLE | SWT.BORDER);
		Table cycleTable = cycleViewer.getTable();
		cycleTable.setHeaderVisible(true);
		cycleTable.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true));
		
		TableColumn frequencyColumn = new TableColumn(cycleTable, SWT.NONE);
		frequencyColumn.setText("Frequenz");
		frequencyColumn.setWidth(90);
		
		TableColumn errorColumn = new TableColumn(cycleTable, SWT.NONE);
		errorColumn.setText("Fehler");
		errorColumn.setWidth(50);
		
		FrequencyCycleController cycleController = new FrequencyCycleController();
		cycleViewer.setLabelProvider(cycleController);
		cycleViewer.setContentProvider(cycleController);
		cycleViewer.setInput(test.getFrequencyRuns());
		
		
		// -- measurements
		final TableViewer measurementViewer = new TableViewer(data, SWT.SINGLE | SWT.BORDER);
		Table measurementTable = measurementViewer.getTable();
		measurementTable.setHeaderVisible(true);
		measurementTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		TableColumn runColumn = new TableColumn(measurementTable, SWT.NONE);
		runColumn.setText("Testlauf");
		runColumn.setWidth(90);
		
		TableColumn ledColumn = new TableColumn(measurementTable, SWT.NONE);
		ledColumn.setText("LED");
		ledColumn.setWidth(120);
		
		TableColumn personLedColumn = new TableColumn(measurementTable, SWT.NONE);
		personLedColumn.setText("Probandenwahl");
		personLedColumn.setWidth(120);
		
		MeasurementController measurementController = new MeasurementController();
		measurementViewer.setLabelProvider(measurementController);
		measurementViewer.setContentProvider(measurementController);
		
		// cycle viewer -> measurement viewer
		cycleViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				FrequencyCycle cycle = (FrequencyCycle)((IStructuredSelection)event.getSelection()).getFirstElement();
				measurementViewer.setInput(cycle.getMeasurements());
			}
		});
		
		
		// auto select data tab
//		tab.getParent().addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				if (e.item == folder) {
//					folder.setSelection(dataItem);
//				}
//			}
//		});
		folder.setSelection(dataItem);
		dataItem.setControl(data);
		
		return folder;
	}

	@Override
	public void setFocus() {
		viewer.getTable().setFocus();
	}
}
