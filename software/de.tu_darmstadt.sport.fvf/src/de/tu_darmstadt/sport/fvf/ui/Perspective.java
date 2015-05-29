package de.tu_darmstadt.sport.fvf.ui;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

//	private IFolderLayout results;
	
	public static final String ID = "de.tu_darmstadt.sport.fvf.ui.Perspective";

	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
		layout.setFixed(true);

		layout.addStandaloneView(PersonView.ID, false, IPageLayout.LEFT, 0.3f, IPageLayout.ID_EDITOR_AREA);
//		layout.addPlaceholder(ResultView.ID, IPageLayout.LEFT, 0.7f, IPageLayout.ID_EDITOR_AREA);
		layout.createFolder("resultviews", IPageLayout.LEFT, 0.7f, IPageLayout.ID_EDITOR_AREA);
//		results.addPlaceholder(ResultView.ID);
//		layout.createPlaceholderFolder("bla", IPageLayout.LEFT, 0.7f, IPageLayout.ID_EDITOR_AREA).addPlaceholder(ResultView.ID);
		
	}
	
//	public void addResultView(ResultView view) {
//		results.addView(ResultView.ID);
//	}

}
