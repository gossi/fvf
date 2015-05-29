package de.tu_darmstadt.sport.fvf.handler;

import java.util.Date;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import de.tu_darmstadt.sport.fvf.FVF;
import de.tu_darmstadt.sport.fvf.driver.ArduinoLedDriver;
import de.tu_darmstadt.sport.fvf.model.Person;
import de.tu_darmstadt.sport.fvf.model.Test;
import de.tu_darmstadt.sport.fvf.ui.PersonView;
import de.tu_darmstadt.sport.fvf.ui.dialogs.TestRunnerDialog;
import de.tu_darmstadt.sport.fvf.ui.wizards.TestSetupWizard;

public class TestCreateHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// test connection
		ArduinoLedDriver driver = FVF.getDefault().getArduinoDriver();
		
		if (!driver.isConnected()) {
			MessageDialog.openWarning(HandlerUtil.getActiveWorkbenchWindow(event).getShell(), "Test starten", "Konnte keinen Test starten, keine Verbindung zur Messvorrichtung gefunden.");
			return null;
		}
		
		// init test
		PersonView view = (PersonView) HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().findView(PersonView.ID);
		Person person = (Person)((IStructuredSelection)view.getViewer().getSelection()).getFirstElement();

		Shell shell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();
		Test test = new Test();
		test.setPerson(person);
		TestSetupWizard wizard = new TestSetupWizard();
		wizard.setTest(test);
		WizardDialog diag = new WizardDialog(shell, wizard);
		
		if (diag.open() == Window.OK) {
			TestRunnerDialog testrunner = new TestRunnerDialog(shell, test);
			test.setDate(new Date(System.currentTimeMillis()));
			if (testrunner.open() == Window.OK) {
				test.save();
				person.addTest(test);
			}
		}
		return null;
	}

}
