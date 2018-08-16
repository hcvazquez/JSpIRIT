package spirit.ui.views.criteriaConfiguration;

import java.util.Vector;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
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

import spirit.core.agglomerations.HierarchicalAgglomeration;
import spirit.core.agglomerations.IntraClassAgglomeration;
import spirit.core.agglomerations.IntraComponentAgglomeration;
import spirit.core.agglomerations.IntraMethodAgglomeration;
import spirit.db.DataBaseManager;
import spirit.priotization.criteria.AgglomerationRelevanceCriteria;


public class AgglomerationRelevanceConfiguration extends CriterionConfigurationDialog {
	private Vector<Double> relevanceValues;
	private ComboViewer comboViewerHierarchical;
	private ComboViewer comboViewerIntraClass;
	private ComboViewer comboViewerIntraComponent;
	private ComboViewer comboViewerIntraMethod;
	
	private AgglomerationRelevanceCriteria selectedCriterion;
	
	public AgglomerationRelevanceConfiguration(Shell parentShell, AgglomerationRelevanceCriteria selectedCriterion, IProject currentProject) {
		super(parentShell);
		relevanceValues=new Vector<>();
		relevanceValues.add(0.0);
		relevanceValues.add(0.1);
		relevanceValues.add(0.2);
		relevanceValues.add(0.3);
		relevanceValues.add(0.4);
		relevanceValues.add(0.5);
		relevanceValues.add(0.6);
		relevanceValues.add(0.7);
		relevanceValues.add(0.8);
		relevanceValues.add(0.9);
		relevanceValues.add(1.0);
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
		
		comboViewerHierarchical=createComboViewer(10,30,"Hierarchical",container);
		comboViewerIntraClass=createComboViewer(10,80,"Intra-Class",container);
		comboViewerIntraComponent=createComboViewer(10,120,"Intra-Component",container);
		comboViewerIntraMethod=createComboViewer(10,170,"Intra-Method",container);
		
		
		return container;
	}
	
	private ComboViewer createComboViewer(int x,int y, String name, Composite container){
		Label lbl = new Label(container, SWT.NONE);
		lbl.setBounds(x, y-20, 140, 16);
		lbl.setText(name);
		ComboViewer comboViewer = new ComboViewer(container, SWT.NONE);
		Combo combo = comboViewer.getCombo();
		combo.setBounds(x, y, 59, 22);
		comboViewer.setContentProvider(ArrayContentProvider.getInstance());
		comboViewer.setLabelProvider(new LabelProvider() {
		  @Override
		  public String getText(Object element) {
		    if (element instanceof Integer) {
		    	Integer relevance = (Integer) element;
		      return relevance.toString();
		    }
		    return super.getText(element);
		  }
		});
		comboViewer.setInput(relevanceValues); 
		
		
		
		combo.select(relevanceValues.indexOf(selectedCriterion.getRelevance(name)));
		return comboViewer;
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
		selectedCriterion.addRelevance(HierarchicalAgglomeration.NAME, getComboSelection(comboViewerHierarchical));
		selectedCriterion.addRelevance(IntraClassAgglomeration.NAME, getComboSelection(comboViewerIntraClass));
		selectedCriterion.addRelevance(IntraComponentAgglomeration.NAME, getComboSelection(comboViewerIntraComponent));
		selectedCriterion.addRelevance(IntraMethodAgglomeration.NAME, getComboSelection(comboViewerIntraMethod));
		
		DataBaseManager.getInstance().saveOrUpdateEntity(selectedCriterion);
	}
	private Double getComboSelection(ComboViewer comboViewer){
		if(((IStructuredSelection)(comboViewer.getSelection())).size()>0){
			return (Double) ((IStructuredSelection)(comboViewer.getSelection())).getFirstElement();
		}
		return 0.0;
	}
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(328, 335);
	}
}
