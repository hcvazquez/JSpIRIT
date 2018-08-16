package spirit.ui.views.criteriaConfiguration;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import spirit.db.DataBaseManager;
import spirit.priotization.criteria.AgglomerationEvolutionCriteria;


public class AgglomerationEvolutionConfiguration extends CriterionConfigurationDialog {

	
	private AgglomerationEvolutionCriteria selectedCriterion;
	
	public AgglomerationEvolutionConfiguration(Shell parentShell, AgglomerationEvolutionCriteria selectedCriterion, IProject currentProject) {
		super(parentShell);
		this.selectedCriterion=selectedCriterion;
	}
	
	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(null);
		

		
		
		return container;
	}
	

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button button = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				okButtonPressed();
			}
		});
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	private void okButtonPressed() {
		
		
		DataBaseManager.getInstance().saveOrUpdateEntity(selectedCriterion);
	}
	
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(328, 335);
	}
}
