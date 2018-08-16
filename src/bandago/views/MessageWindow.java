package bandago.views;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

public class MessageWindow extends Dialog {
	
	private String message;

	public MessageWindow(Shell parentShell,String m) {
		super(parentShell);
		message = m;
		setShellStyle(SWT.TITLE);
	}
	
	protected Control createDialogArea(Composite parent) {
		this.getShell().setText("Bandago");
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(null);
		
		Label lblMessage = new Label(container, SWT.WRAP);
		lblMessage.setBounds(10, 5, 280, 30);
		lblMessage.setText(message);
		
		Label label = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(0, 40, 300, 2);
		
		return container;
	}
	
	@SuppressWarnings("unused")
	protected void createButtonsForButtonBar(Composite parent) {
		Button button = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
	}
	
	protected Point getInitialSize() {
		return new Point(300, 115);
	}
}
