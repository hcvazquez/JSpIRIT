package spirit.ui.views.criteriaConfiguration;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;

import spirit.db.DataBaseManager;
import spirit.priotization.criteria.AlphaCriteria;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Listener;

public class AlphaConfiguration extends CriterionConfigurationDialog {
	private AlphaCriteria alphaCriteria;
	
	private Slider slider;
	private Label sliderValue;
	public AlphaConfiguration(Shell parentShell, AlphaCriteria alphaCriteria) {
		super(parentShell);
		this.alphaCriteria=alphaCriteria;
	}
	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(null);
		
		Label lblAlphaValue = new Label(container, SWT.NONE);
		lblAlphaValue.setBounds(10, 20, 89, 14);
		lblAlphaValue.setText("Alpha value");
		
	
		
		slider = new Slider(container, SWT.NONE);
		slider.setMinimum(0);
		slider.setMaximum(110);
		slider.setIncrement(1);
		//slider.setThumb(20);
		slider.setBounds(91, 20, 169, 18);
		slider.setSelection(new Double(alphaCriteria.getAlpha()*100).intValue());
		sliderValue = new Label(container, SWT.NONE);
		sliderValue.setBounds(266, 20, 59, 24);
		sliderValue.setText(alphaCriteria.getAlpha().toString());
		
		
		slider.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event event) {
		    	 // System.out.println(slider.getSelection());
		    	  sliderValue.setText(new Double(slider.getSelection()/100.0).toString());
		      }
		    });

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
		alphaCriteria.setAlpha(new Double(sliderValue.getText()));
		DataBaseManager.getInstance().saveOrUpdateEntity(alphaCriteria);
	}
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(335, 148);
	}
}
