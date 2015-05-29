package de.tu_darmstadt.sport.fvf.handler;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.ui.handlers.HandlerUtil;

import de.tu_darmstadt.sport.fvf.model.Person;
import de.tu_darmstadt.sport.fvf.ui.PersonView;
import de.tu_darmstadt.sport.fvf.ui.dialogs.PersonDialog;

public class PersonCreateHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Person person = new Person();
		PersonDialog diag = new PersonDialog(HandlerUtil.getActiveWorkbenchWindow(event).getShell());
		diag.setPerson(person);

		if (diag.open() == Dialog.OK) {
			person.save();
			PersonView view = (PersonView) HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().findView(PersonView.ID);
			@SuppressWarnings("unchecked")
			List<Person> persons = (List<Person>) view.getViewer().getInput();
			persons.add(person);
			view.getViewer().refresh();
		}
		return null;
	}

}
