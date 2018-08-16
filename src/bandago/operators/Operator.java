package bandago.operators;

import java.util.ArrayList;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.internal.corext.refactoring.code.ExtractMethodRefactoring;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

import bandago.utils.StatementSet;

@SuppressWarnings("restriction")
public abstract class Operator {
	protected ICompilationUnit icu;
	protected NullProgressMonitor pm = new NullProgressMonitor();
	private Refactoring refactoring;
	private RefactoringStatus result;
	protected String name = "";
	
	public String getName(){
		return name;
	}
	
	public void setICompilationUnit(ICompilationUnit icu){
		this.icu = icu;
	}
	
	protected StatementSet checkError(ArrayList<StatementSet> statements){
		for(StatementSet s:statements){
			refactoring = new ExtractMethodRefactoring(icu, s.getStartPosition(),s.getLength());			
			try {
				result = refactoring.checkAllConditions(pm);
			} catch (Exception e) {
				return null;
			}
			if(!result.hasError())
				return s;
		}
		return null;
	}
	
	public abstract StatementSet getSelectedStatement(ArrayList<StatementSet> statements);
}
