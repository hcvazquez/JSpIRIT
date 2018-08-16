package bandago.solvers;

import java.util.ArrayList;

import org.eclipse.ui.PartInitException;

import bandago.algorithms.RefactoringAlgorithm;
import bandago.metrics.MetricAnalyzer;
import bandago.utils.CompilationUnitManager;
import bandago.utils.ExtractData;

public class BrainMethodSolver implements CodeSmellSolver {

	private ArrayList<MetricAnalyzer> metrics;
	private RefactoringAlgorithm algorithm;

	public BrainMethodSolver(RefactoringAlgorithm algorithm){
		this.algorithm = algorithm;
		algorithm.setFather(this);
	}
	
	public ArrayList<MetricAnalyzer> getMetrics(){
		return metrics;
	}
	
	@Override
	public void solve() throws PartInitException{
		algorithm.execute();
		metrics = algorithm.getMetrics();
	}

	@Override
	public String getName() {
		return algorithm.getMethodName().getFullyQualifiedName();
	}

	@Override
	public void applySolution(int s) {
		if(algorithm.getSolutions() != null){
			CompilationUnitManager.restartCounter();
			CompilationUnitManager u = new CompilationUnitManager(algorithm.getCompilationUnit(), false, algorithm.getMethodName().getFullyQualifiedName());
			ArrayList<ExtractData> solution = algorithm.getSolutions().get(s);
			for(ExtractData e : solution){
				u.applyExtractRefactoring(e.getStartPosition(), e.getLenght());
			}
		}
	}
	
	@Override
	public CompilationUnitManager previewSolution(int s){
		if(algorithm.getSolutions() != null){
			CompilationUnitManager.restartCounter();
			CompilationUnitManager u = new CompilationUnitManager(algorithm.getCompilationUnit(), true, algorithm.getMethodName().getFullyQualifiedName());
			ArrayList<ExtractData> solution = algorithm.getSolutions().get(s);
			for(ExtractData e : solution){
				u.applyExtractRefactoring(e.getStartPosition(), e.getLenght());
			}
			return u;
		}
		return null;
	}
}
