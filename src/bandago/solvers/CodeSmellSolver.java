package bandago.solvers;

import org.eclipse.ui.PartInitException;
import bandago.utils.CompilationUnitManager;

public interface CodeSmellSolver {
	
	public void solve() throws PartInitException;
	
	public String getName();
	
	public void applySolution(int s);
	
	public CompilationUnitManager previewSolution(int s);
	
}
