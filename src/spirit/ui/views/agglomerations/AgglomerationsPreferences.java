package spirit.ui.views.agglomerations;

import java.util.Vector;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import spirit.core.agglomerations.model.projectBuilders.impl.ProjectFromConcernMapperBuilder;
import spirit.core.agglomerations.model.projectBuilders.impl.ProjectFromPackageStructureBuilder;
import spirit.db.DataBaseManager;
import spirit.priotization.RankingManagerForSmells;
import spirit.ui.views.SpIRITAgglomerationsView;

public class AgglomerationsPreferences extends Dialog {
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private AgglomerationConfigurationForProject currentConfiguration;
	private ComboViewer comboViewer;
	private SpIRITAgglomerationsView spIRITAgglomerationsView;

	/**
	 * Create the dialog.
	 * @param parentShell
	 * @param spIRITAgglomerationsView 
	 */
	public AgglomerationsPreferences(Shell parentShell, SpIRITAgglomerationsView spIRITAgglomerationsView) {
		super(parentShell);
		String projectName=RankingManagerForSmells.getInstance().getCurrentProject();
		currentConfiguration=DataBaseManager.getInstance().getAgglomerationConfigurationForProject(projectName);
		this.spIRITAgglomerationsView=spIRITAgglomerationsView;
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(null);
		
		Label lblReadComponentStructure = new Label(container, SWT.NONE);
		lblReadComponentStructure.setBounds(10, 10, 195, 14);
		lblReadComponentStructure.setText("Read component structure from ");
		
		Vector<String> structureSources=new Vector<String>();
		structureSources.add(ProjectFromPackageStructureBuilder.NAME);
		structureSources.add(ProjectFromConcernMapperBuilder.NAME);
		
		comboViewer = new ComboViewer(container, SWT.NONE);
		Combo combo = comboViewer.getCombo();
		combo.setBounds(192, 6, 143, 22);
		comboViewer.setContentProvider(ArrayContentProvider.getInstance());
		comboViewer.setInput(structureSources); 
		
		int selected=structureSources.indexOf(currentConfiguration.getStructureSource());
		if(selected!=-1)
			combo.select(selected);
		else 
			combo.select(0);
		
		Label lblIntramethodThreashold = new Label(container, SWT.NONE);
		lblIntramethodThreashold.setBounds(10, 44, 143, 14);
		lblIntramethodThreashold.setText("Intra-Method threshold");
		
		Label lblIntracomponentThreshold = new Label(container, SWT.NONE);
		lblIntracomponentThreshold.setBounds(10, 77, 165, 14);
		lblIntracomponentThreshold.setText("Intra-Component threshold");
		
		Label lblHierarchicalThreshold = new Label(container, SWT.NONE);
		lblHierarchicalThreshold.setBounds(10, 108, 153, 14);
		lblHierarchicalThreshold.setText("Hierarchical threshold");
		
		Label lblIntraClassThreshold = new Label(container, SWT.NONE);
		lblIntraClassThreshold.setBounds(10, 139, 153, 14);
		lblIntraClassThreshold.setText("Intra-Class threshold");
		
		text = new Text(container, SWT.BORDER);
		text.setBounds(172, 39, 71, 19);
		text.setText(currentConfiguration.getIntraMethodThreshold().toString());
		
		text_1 = new Text(container, SWT.BORDER);
		text_1.setBounds(172, 72, 71, 19);
		text_1.setText(currentConfiguration.getIntraComponentThreshold().toString());
		
		text_2 = new Text(container, SWT.BORDER);
		text_2.setBounds(172, 105, 71, 19);
		text_2.setText(currentConfiguration.getHierarchicalThreshold().toString());
		
		text_3 = new Text(container, SWT.BORDER);
		text_3.setBounds(172, 137, 71, 19);
		Integer intraClassThreshold=currentConfiguration.getIntraClassThreshold();
		if(intraClassThreshold==null)
			intraClassThreshold=2;
		text_3.setText(intraClassThreshold.toString());
		
		

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

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(346, 246);
	}
	private void okButtonPressed() {
		currentConfiguration.setIntraClassThreshold(new Integer(text_3.getText()));
		currentConfiguration.setHierarchicalThreshold(new Integer(text_2.getText()));
		currentConfiguration.setIntraComponentThreshold(new Integer(text_1.getText()));
		currentConfiguration.setIntraMethodThreshold(new Integer(text.getText()));
		currentConfiguration.setStructureSource((String) ((IStructuredSelection)(comboViewer.getSelection())).getFirstElement());
		DataBaseManager.getInstance().saveOrUpdateEntity(currentConfiguration);
		//spIRITAgglomerationsView.update
	}
}
