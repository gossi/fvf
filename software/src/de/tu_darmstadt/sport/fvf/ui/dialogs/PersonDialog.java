package de.tu_darmstadt.sport.fvf.ui.dialogs;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import de.tu_darmstadt.sport.fvf.model.Person;

public class PersonDialog extends Dialog {
	private DataBindingContext bindingContext;
	private Person person;
	private Text firstName;
	private Text lastName;
	private Spinner age;
//	private ControlDecoration ageDeco;
	
	public PersonDialog(Shell parentShell) {
		super(parentShell);
	}
	
	@Override
	protected Point getInitialSize() {
		return new Point(300, 168);
	}

	@Override
	protected Control createDialogArea(Composite parentComposite) {
		Composite parent = (Composite)super.createDialogArea(parentComposite);
		GridLayout gridLayout = (GridLayout) parent.getLayout();
		gridLayout.makeColumnsEqualWidth = true;
		gridLayout.numColumns = 2;
		Label lblFirstName = new Label(parent, SWT.NONE);
		lblFirstName.setText("Vorname: ");
		
		firstName = new Text(parent, SWT.BORDER);
		firstName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblLastName = new Label(parent, SWT.NONE);
		lblLastName.setText("Nachname:");
		
		lastName = new Text(parent, SWT.BORDER);
		lastName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblAge = new Label(parent, SWT.NONE);
		lblAge.setText("Alter:");
		
		age = new Spinner(parent, SWT.BORDER);
		age.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
//		ageDeco = new ControlDecoration(age, SWT.BOTTOM | SWT.LEFT);
//		ageDeco.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_DEC_FIELD_WARNING));
		
//		age.addVerifyListener(new VerifyListener() {
//			@Override
//			public void verifyText(VerifyEvent e) {
//				String string = e.text;
//				char[] chars = new char[string.length()];
//				string.getChars(0, chars.length, chars, 0);
//				for (int i = 0; i < chars.length; i++) {
//					if (!('0' <= chars[i] && chars[i] <= '9')) {
//						e.doit = false;
//						return;
//					}
//				}
//			}
//		});

//		new Label(parent, SWT.NONE);
		initDataBindings();
		
		return parent;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}
	protected DataBindingContext initDataBindings() {
		bindingContext = new DataBindingContext();
		//
		IObservableValue firstNameObserveTextObserveWidget = SWTObservables.observeText(firstName, SWT.Modify);
		IObservableValue personFirstNameObserveValue = BeansObservables.observeValue(person, "firstName");
		bindingContext.bindValue(firstNameObserveTextObserveWidget, personFirstNameObserveValue, null, null);
		//
		IObservableValue lastNameObserveTextObserveWidget = SWTObservables.observeText(lastName, SWT.Modify);
		IObservableValue personLastNameObserveValue = BeansObservables.observeValue(person, "lastName");
		bindingContext.bindValue(lastNameObserveTextObserveWidget, personLastNameObserveValue, null, null);
		//
		IObservableValue ageObserveSelectionObserveWidget = SWTObservables.observeSelection(age);
		IObservableValue personAgeObserveValue = BeansObservables.observeValue(person, "age");
		bindingContext.bindValue(ageObserveSelectionObserveWidget, personAgeObserveValue, null, null);
		//
		return bindingContext;
	}
}
