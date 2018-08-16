package bandago.views;

import java.awt.Color;
import java.util.Hashtable;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.internal.corext.refactoring.rename.RenameMethodProcessor;
import org.eclipse.jdt.internal.corext.refactoring.rename.RenameVirtualMethodProcessor;
import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jdt.internal.ui.javaeditor.JavaSourceViewer;
import org.eclipse.jdt.ui.PreferenceConstants;
import org.eclipse.jdt.ui.text.JavaSourceViewerConfiguration;
import org.eclipse.jdt.ui.text.JavaTextTools;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ltk.core.refactoring.participants.RenameRefactoring;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import bandago.solvers.CodeSmellSolver;
import bandago.utils.CompilationUnitManager;
import spirit.core.smells.CodeSmell;

import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

@SuppressWarnings({ "restriction", "unused" })
public class ExtractMethodPreview extends Dialog {
	private StyledText leftText;
	private StyledText rightText;
	private CodeSmell codeSmell;
	private CompilationUnitManager cuManager;
	private JavaSourceViewer leftViewer;
	private JavaSourceViewer rightViewer;
	private CodeSmellSolver solver;
	private int index;

	public ExtractMethodPreview(Shell parentShell, CodeSmell c, CompilationUnitManager u, CodeSmellSolver s,int i) {
		super(parentShell);
		codeSmell = c;
		cuManager = u;
		solver = s;
		index = i;
		setShellStyle(SWT.CLOSE | SWT.TITLE);
	}

	protected Control createDialogArea(Composite parent) {
		this.getShell().setText("Bandago Wizard");
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(null);
		
		Composite topBanner = new Composite(container, SWT.BORDER);
		topBanner.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		topBanner.setBounds(0, 0, 900, 80);
		
		Label topImage = new Label(topBanner, SWT.NONE);
		topImage.setImage(ResourceManager.getPluginImage("SpIRIT", "resources/eclipse.png"));
		topImage.setBounds(20, 0, 80, 80);
		
		Label titleLabel = new Label(topBanner, SWT.NONE);
		titleLabel.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 15, SWT.BOLD));
		titleLabel.setBounds(110, 10, 670, 20);
		titleLabel.setText("Bandago Wizard");
		
		Label descriptionLabel = new Label(topBanner, SWT.NONE);
		descriptionLabel.setBounds(110, 30, 670, 42);
		descriptionLabel.setText("This wizard helps to preview the changes proposed by Bandago tool.\nIf you want to apply this solution click on the \"Apply Refactoring\" button.");
		
		Label rightImage = new Label(topBanner, SWT.NONE);
		rightImage.setImage(ResourceManager.getPluginImage("SpIRIT", "resources/corner.png"));
		rightImage.setBounds(812, 14, 88, 66);
		
		Label bottomSeparator = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		bottomSeparator.setBounds(20, 555, 860, 2);
		
		leftText = getStyledText(leftViewer,container,codeSmell.getElement().toString());
		leftText.setEditable(false);
		leftText.setBounds(20,130,418,403);
		
		rightText = getStyledText(rightViewer,container,cuManager.getPreview());
		rightText.setEditable(false);
		rightText.setBounds(460,130,418,403);
		return container;
	}
	
	private StyledText getStyledText(JavaSourceViewer viewer,Composite container, String content) {
		JavaTextTools tools = JavaPlugin.getDefault().getJavaTextTools();
		tools.getColorManager();
		int styles=SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI| SWT.BORDER| SWT.FULL_SELECTION;
		IPreferenceStore store=JavaPlugin.getDefault().getCombinedPreferenceStore();
		
		Label classInfoLabel = new Label(container, SWT.BORDER);
		classInfoLabel.setText(codeSmell.getMainClassName() + ".java");
		classInfoLabel.setBounds(20, 90, 860, 14);
		
		Label leftLabel = new Label(container, SWT.BORDER);
		leftLabel.setBounds(20, 110, 318, 14);
		leftLabel.setText("Original Source");
		
		Label rightLabel = new Label(container, SWT.BORDER);
		rightLabel.setBounds(460, 110, 318, 14);
		rightLabel.setText("Refactored Source");
		viewer = new JavaSourceViewer(container,null,null,false,styles,store);
		viewer.configure(new JavaSourceViewerConfiguration(tools.getColorManager(),store,null,null));
		viewer.getControl().setFont(JFaceResources.getFont(PreferenceConstants.EDITOR_TEXT_FONT));
		String contents = content;
		IDocument document=new Document(contents);
		tools.setupJavaDocumentPartitioner(document);
		viewer.setDocument(document);
		viewer.setEditable(false);
		GridData gd=new GridData(GridData.FILL_BOTH);
		gd.heightHint=convertHeightInCharsToPixels(10);
		gd.widthHint=convertWidthInCharsToPixels(40);
		viewer.getControl().setLayoutData(gd);
		return viewer.getTextWidget();
	}

	protected void createButtonsForButtonBar(Composite parent) {
		Button okButton = createButton(parent, IDialogConstants.OK_ID, "Apply Refactoring",	true);
		okButton.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				solver.applySolution(index-1);
				cuManager.deleteCopy();
			}
		});
		Button cancelButton = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		cancelButton.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				cuManager.deleteCopy();
			}
		});
	}

	protected Point getInitialSize() {
		return new Point(900, 630);
	}
}
