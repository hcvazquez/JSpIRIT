package bandago.views;

import java.util.ArrayList;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import spirit.core.smells.CodeSmell;
import bandago.algorithms.RefactoringAlgorithm;
import bandago.algorithms.SimulatedAnnealing;
import bandago.metrics.MetricAnalyzer;
import bandago.solvers.BrainMethodSolver;
import bandago.solvers.CodeSmellSolver;
import bandago.utils.Parameters;

public class StartupManager {
	private static CodeSmellSolver brmd;
	private static CodeSmell codeSmell;
	private static Thread thread;
	private static IPackageFragment packageAux;
	private static NullProgressMonitor pm = new NullProgressMonitor();
	
	public StartupManager(CodeSmell cs){
		actionDo(cs);
	}
	
	public static CodeSmell getCodeSmell(){
		return codeSmell;
	}
	
	public static Thread getWorkingThread(){
		return thread;
	}
	
	public static void deleteRefactroingCopies(){
		try {
			IJavaElement[] elements = packageAux.getChildren();
			for(IJavaElement e: elements){
				if(e.getElementName().startsWith("refactoring_copy")){
					ICompilationUnit c = (ICompilationUnit)e;
					c.delete(true, pm);
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
	}
	
	public static void actionDo(CodeSmell codeS){
		codeSmell = codeS;
		ArrayList<MethodDeclaration> originalMethod = new ArrayList<MethodDeclaration>();
		originalMethod.add((MethodDeclaration) codeSmell.getElement());
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("bandago.views.CodeSmellsSolutions");
		} catch (PartInitException e1) {
			e1.printStackTrace();
		}
		CodeSmellsSolutions.emptyTable();
		CodeSmellsSolutions.addSolutionItem("Original", new MetricAnalyzer(originalMethod), null);
		RefactoringAlgorithm algorithm = new SimulatedAnnealing(CodeSmellsSolutions.getInstance(),Parameters.getMSTE(),Parameters.getTMP(),Parameters.getNOS(), Parameters.getMDI(),Parameters.getCDF(),Parameters.getTPS());
		algorithm.setMethodName(((MethodDeclaration)codeSmell.getElement()).getName());
		algorithm.setCompilationUnit(getCompilationUnitFromCodeSmell(codeSmell));
		brmd = new BrainMethodSolver(algorithm);
		thread = new Thread(){
		    public void run(){
				try {
					brmd.solve();
				} catch (PartInitException e) {
					e.printStackTrace();
				}
		    }
		  };
		thread.start();
	}
	
	private static ICompilationUnit getCompilationUnitFromCodeSmell(CodeSmell c){
		MethodDeclaration element = (MethodDeclaration) c.getElement(); 
		IJavaElement javaElement=((TypeDeclaration)(element.getParent())).resolveBinding().getJavaElement();
		ICompilationUnit cunit= (ICompilationUnit)javaElement.getAncestor(IJavaElement.COMPILATION_UNIT);
		packageAux = (IPackageFragment) cunit.getAncestor(IJavaElement.PACKAGE_FRAGMENT);
		return cunit;
	}

}
