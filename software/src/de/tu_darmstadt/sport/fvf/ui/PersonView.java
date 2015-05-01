package de.tu_darmstadt.sport.fvf.ui;

import java.util.List;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.part.ViewPart;

import de.tu_darmstadt.sport.fvf.FVFConstants;
import de.tu_darmstadt.sport.fvf.controller.PersonController;
import de.tu_darmstadt.sport.fvf.database.DatabaseLoader;
import de.tu_darmstadt.sport.fvf.model.Person;
import de.tu_darmstadt.sport.fvf.model.map.PersonMap;

public class PersonView extends ViewPart {
	
	public static final String ID = "de.tu_darmstadt.sport.fvf.ui.PersonView";

	private TableViewer viewer;
	private IContextActivation context;
	private IContextService contextService;
	private boolean contextActive = false;

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		PersonController controller = new PersonController();
		
		viewer = new TableViewer(parent, SWT.SINGLE | SWT.H_SCROLL
				| SWT.V_SCROLL);
		viewer.setContentProvider(controller);
		viewer.setLabelProvider(controller);

		if (DatabaseLoader.getInstance().isInitialized()) {
			PersonMap map = new PersonMap();
			List<Person> model = map.selectAll();
			viewer.setInput(model);
		}
		
		// menu
		// First we create a menu Manager
		MenuManager menuManager = new MenuManager();
		Menu menu = menuManager.createContextMenu(viewer.getTable());
		// Set the MenuManager
		viewer.getTable().setMenu(menu);
		getSite().registerContextMenu(menuManager, viewer);
		
		// Make the selection available
//		getSite().setSelectionProvider(viewer);
		
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				if (!event.getSelection().isEmpty()) {
					activateContext();
				} else {
					deactivateContext();
				}
			}
		});
		viewer.getTable().addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				openPerson();
			}
		});
		viewer.getTable().addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_RETURN) {
					openPerson();
				}
			}
		});
		
		getSite().getPage().addPartListener(new IPartListener() {
			public void partOpened(IWorkbenchPart part) {}
			public void partDeactivated(IWorkbenchPart part) {}		
			public void partClosed(IWorkbenchPart part) {}			
			public void partBroughtToTop(IWorkbenchPart part) {}
			
			public void partActivated(IWorkbenchPart part) {
				if (part instanceof ResultView) {
					ResultView result = (ResultView)part;
					
					// this does not work:
					// viewer.setSelection(new StructuredSelection(result.getPerson()), true);
					
					// needs a fucking workaround
					Table table = viewer.getTable();
					TableItem found = null;
					for (TableItem i : table.getItems()) {
						
						if (((Person)i.getData()).getId() == result.getPerson().getId()) {
							found = i;
						}
					}
					
					if (found != null) {
						int index = table.indexOf(found);
						table.select(index);

					}

//					activateContext();
				}
			}
		});
		
		contextService = (IContextService) PlatformUI.getWorkbench().getAdapter(IContextService.class);
	}
	
	private void activateContext() {
		contextActive = true;
		context = contextService.activateContext(FVFConstants.CONTEXT_PERSON_SELECTED);
	}
	
	private void deactivateContext() {
		contextActive = false;
		contextService.deactivateContext(context);
	}
	
	private void openPerson() {
		if (!viewer.getSelection().isEmpty()) {
			Person person = (Person)((IStructuredSelection)viewer.getSelection()).getFirstElement();
	
			try {
				getSite().getPage().showView(ResultView.ID, ""+person.getId(), IWorkbenchPage.VIEW_ACTIVATE);
			} catch (PartInitException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public TableViewer getViewer() {
		return viewer;
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}