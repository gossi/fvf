package de.tu_darmstadt.sport.fvf.ui.wizards;

import org.eclipse.jface.wizard.Wizard;

import de.tu_darmstadt.sport.fvf.model.Test;

public class TestSetupWizard extends Wizard {

	private Test test = null;
	private TestSetupPage testSetupPage = null;
	
	@Override
	public void addPages() {
		testSetupPage = new TestSetupPage();
		testSetupPage.setTest(test);
		addPage(testSetupPage);
	}

	@Override
	public boolean performFinish() {
		return true;
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
		if (testSetupPage != null) {
			testSetupPage.setTest(test);
		}
	}

}
