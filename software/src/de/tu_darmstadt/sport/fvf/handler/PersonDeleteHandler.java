package de.tu_darmstadt.sport.fvf.handler;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.handlers.HandlerUtil;

import de.tu_darmstadt.sport.fvf.FVF;
import de.tu_darmstadt.sport.fvf.model.Person;
import de.tu_darmstadt.sport.fvf.ui.PersonView;

public class PersonDeleteHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		PersonView view = (PersonView) HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().findView(PersonView.ID);
		Person person = (Person)((IStructuredSelection)view.getViewer().getSelection()).getFirstElement();

		Image icon = FVF.getImageDescriptor("icons/person_delete.png").createImage();
		MessageDialog diag = new MessageDialog(HandlerUtil.getActiveWorkbenchWindow(event).getShell(), 
				"Person löschen", icon, "Soll die Person wirklich gelöscht werden?", 
				MessageDialog.WARNING, new String[]{"Abbrechen", "OK"}, 0);
		
		if (diag.open() == 1) {
			person.delete();
			
			@SuppressWarnings("unchecked")
			List<Person> persons = (List<Person>) view.getViewer().getInput();
			persons.remove(person);
			view.getViewer().refresh();
		}
		return null;
	}

}
