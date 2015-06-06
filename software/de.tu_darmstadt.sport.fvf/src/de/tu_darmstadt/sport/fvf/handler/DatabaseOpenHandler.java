package de.tu_darmstadt.sport.fvf.handler;

import java.io.File;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.handlers.HandlerUtil;

import de.tu_darmstadt.sport.fvf.FVF;
import de.tu_darmstadt.sport.fvf.PreferenceConstants;
import de.tu_darmstadt.sport.fvf.database.DatabaseLoader;
import de.tu_darmstadt.sport.fvf.model.Person;
import de.tu_darmstadt.sport.fvf.model.map.PersonMap;
import de.tu_darmstadt.sport.fvf.ui.PersonView;

public class DatabaseOpenHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IPreferenceStore store = FVF.getDefault().getPreferenceStore();
		FileDialog diag = new FileDialog(HandlerUtil.getActiveWorkbenchWindow(event).getShell());
		String path = diag.open();
		if (path != null) {
			File file = new File(path);
			store.setValue(PreferenceConstants.DATABASE_LASTDB, file.getAbsolutePath());
			open(path);
		}
		return null;
	}

	public void open(String path) {
		File file = new File(path);
		DatabaseLoader.getInstance().setDatabase(file);
		DatabaseLoader.getInstance().initialize();
		
		// load persons
		PersonMap map = new PersonMap();
		List<Person> model = map.selectAll();

		PersonView persons = (PersonView)FVF.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(PersonView.ID);
		persons.getViewer().setInput(model);
		
		// close all result views
		// TODO: how to get all views with ResultView.ID ?
	}
}
