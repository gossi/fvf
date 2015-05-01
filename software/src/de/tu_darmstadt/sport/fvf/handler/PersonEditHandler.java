package de.tu_darmstadt.sport.fvf.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import de.tu_darmstadt.sport.fvf.model.Person;
import de.tu_darmstadt.sport.fvf.ui.PersonView;
import de.tu_darmstadt.sport.fvf.ui.dialogs.PersonDialog;

public class PersonEditHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		PersonView view = (PersonView) HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().findView(PersonView.ID);
		Person person = (Person)((IStructuredSelection)view.getViewer().getSelection()).getFirstElement();

		PersonDialog diag = new PersonDialog(HandlerUtil.getActiveWorkbenchWindow(event).getShell());
		diag.setPerson(person);

		if (diag.open() == Dialog.OK) {
			person.save();
			view.getViewer().refresh();
		}
		return null;
	}

}
