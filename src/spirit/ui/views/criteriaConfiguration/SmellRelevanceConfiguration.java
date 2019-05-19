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

import spirit.core.smells.BrainClass;
import spirit.core.smells.BrainMethod;
import spirit.core.smells.DataClass;
import spirit.core.smells.DispersedCoupling;
import spirit.core.smells.FeatureEnvy;
import spirit.core.smells.GodClass;
import spirit.core.smells.IntensiveCoupling;
import spirit.core.smells.LongMethod;
import spirit.core.smells.RefusedParentBequest;
import spirit.core.smells.ShotgunSurgery;
import spirit.core.smells.TraditionBreaker;
import spirit.db.DataBaseManager;
import spirit.priotization.criteria.SmellRelevanceCriteria;

public class SmellRelevanceConfiguration extends CriterionConfigurationDialog {
	private Vector<Integer> relevanceValues;
	private ComboViewer comboViewerBrainClass;
	private ComboViewer comboViewerDataClass;
	private ComboViewer comboViewerFeatureEnvy;
	private ComboViewer comboViewerIntensiveCoupling;
	private ComboViewer comboViewerShotgunSurgery;
	private ComboViewer comboViewerBrainMethod;
	private ComboViewer comboViewerLongMethod;
	private ComboViewer comboViewerDispersedCoupling;
	private ComboViewer comboViewerGodClass;
	private ComboViewer comboViewerRefusedParentBequest;
	private ComboViewer comboViewerTraditionBreaker;
	private SmellRelevanceCriteria selectedCriterion;
	
	public SmellRelevanceConfiguration(Shell parentShell, SmellRelevanceCriteria selectedCriterion, IProject currentProject) {
		super(parentShell);
		relevanceValues=new Vector<>();
		relevanceValues.add(1);
		relevanceValues.add(2);
		relevanceValues.add(3);
		relevanceValues.add(4);
		relevanceValues.add(5);
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
		
		comboViewerBrainClass=createComboViewer(10,30,"Brain Class",container);
		comboViewerDataClass=createComboViewer(10,80,"Data Class",container);
		comboViewerFeatureEnvy=createComboViewer(10,130,"Feature Envy",container);
		comboViewerIntensiveCoupling=createComboViewer(10,180,"Intensive Coupling",container);
		comboViewerShotgunSurgery=createComboViewer(10,230,"Shotgun Surgery",container);
		
		comboViewerBrainMethod=createComboViewer(170,30,"Brain Method",container);
		comboViewerDispersedCoupling=createComboViewer(170,80,"Dispersed Coupling",container);
		comboViewerGodClass=createComboViewer(170,130,"God Class",container);
		comboViewerRefusedParentBequest=createComboViewer(170,180,"Refused Parent Bequest",container);
		comboViewerTraditionBreaker=createComboViewer(170,230,"Tradition Breaker",container);
		comboViewerLongMethod=createComboViewer(170,280,"Long Method",container);
		
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
		combo.select(selectedCriterion.getRelevance(name)-1);
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
		selectedCriterion.addRelevance(BrainClass.NAME, getComboSelection(comboViewerBrainClass));
		selectedCriterion.addRelevance(DataClass.NAME, getComboSelection(comboViewerDataClass));
		selectedCriterion.addRelevance(FeatureEnvy.NAME, getComboSelection(comboViewerFeatureEnvy));
		selectedCriterion.addRelevance(LongMethod.NAME, getComboSelection(comboViewerLongMethod));
		selectedCriterion.addRelevance(IntensiveCoupling.NAME, getComboSelection(comboViewerIntensiveCoupling));
		selectedCriterion.addRelevance(ShotgunSurgery.NAME, getComboSelection(comboViewerShotgunSurgery));
		selectedCriterion.addRelevance(BrainMethod.NAME, getComboSelection(comboViewerBrainMethod));
		selectedCriterion.addRelevance(DispersedCoupling.NAME, getComboSelection(comboViewerDispersedCoupling));
		selectedCriterion.addRelevance(GodClass.NAME, getComboSelection(comboViewerGodClass));
		selectedCriterion.addRelevance(RefusedParentBequest.NAME, getComboSelection(comboViewerRefusedParentBequest));
		selectedCriterion.addRelevance(TraditionBreaker.NAME, getComboSelection(comboViewerTraditionBreaker));
		DataBaseManager.getInstance().saveOrUpdateEntity(selectedCriterion);
	}
	private Integer getComboSelection(ComboViewer comboViewer){
		if(((IStructuredSelection)(comboViewer.getSelection())).size()>0){
			return (Integer) ((IStructuredSelection)(comboViewer.getSelection())).getFirstElement();
		}
		return 1;
	}
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(383, 335);
	}
}
