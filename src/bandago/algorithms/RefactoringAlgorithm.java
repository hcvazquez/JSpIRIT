package bandago.algorithms;

import java.util.ArrayList;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.SimpleName;

import bandago.metrics.MetricAnalyzer;
import bandago.solvers.CodeSmellSolver;
import bandago.utils.ExtractData;

public interface RefactoringAlgorithm {
	public ArrayList<ArrayList<ExtractData>> getSolutions();

	public SimpleName getMethodName();
	
	public void setMethodName(SimpleName simpleName);

	public void setCompilationUnit(ICompilationUnit iCompilationUnit);

	public void execute();
	
	public ArrayList<MetricAnalyzer> getMetrics();
	
	public ICompilationUnit getCompilationUnit();
	
	public void setFather(CodeSmellSolver c);
	
}
