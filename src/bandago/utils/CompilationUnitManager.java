package bandago.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.refactoring.CompilationUnitChange;
import org.eclipse.jdt.internal.corext.refactoring.code.ExtractMethodRefactoring;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;


@SuppressWarnings("restriction")
public class CompilationUnitManager {
	private static int numbercompilations = 0;
	private ICompilationUnit icu;
	private CompilationUnit cu;
	private Visitor visitador;
	private String originalMethodName;
	private static int numbermethodsextracted = 0;
	private NullProgressMonitor pm = new NullProgressMonitor();
	@SuppressWarnings("rawtypes")
	private Map options = JavaCore.getOptions();
	
	
	public CompilationUnitManager(ICompilationUnit icuOriginal, boolean makeCopy, String originalMethodName){ 
		this.originalMethodName = originalMethodName;
		JavaCore.setComplianceOptions(JavaCore.VERSION_1_7, options);
  		IPackageFragment pkga = (IPackageFragment) icuOriginal.getAncestor(IJavaElement.PACKAGE_FRAGMENT);
  		if(makeCopy)
        try {
			icu = pkga.createCompilationUnit("refactoring_copy_"+numbercompilations+".java",icuOriginal.getSource() , true, pm);
			numbercompilations++;
        } catch (JavaModelException e) {
			e.printStackTrace();
		}
  		else{
  			icu = icuOriginal;
  		}
		updateCompilationUnitFromICompilationUnit();
	}
	
	public ICompilationUnit getCopyOfICompilationUnit(){
		try {
			IPackageFragment pkga = (IPackageFragment) icu.getAncestor(IJavaElement.PACKAGE_FRAGMENT);
			numbercompilations++;
			return pkga.createCompilationUnit("refactoring_copy_"+numbercompilations+".java",icu.getSource() , true, pm);
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setICompilationUnit(ICompilationUnit icu){
		this.icu = icu;
		updateCompilationUnitFromICompilationUnit();
	}
	/**
	 * 
	 * Elimina el archivo de la ICompilationUnit.
	 */
	public void deleteCopy(){
		if(icu != null){
			try {
				icu.delete(true, pm);
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void write(String content, File file)
	        throws IOException {
	    // create file if it necessary
	    if (!file.exists()) {
	        file.createNewFile();
	    }
	    // write to file
	    // Use printwriter with buffered writer is faster than FileWriter
	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
	    out.write(content);

	    out.close();
	}
	
	private CompilationUnit updateCompilationUnitFromICompilationUnit(){
		@SuppressWarnings("deprecation")
		ASTParser parser = ASTParser.newParser(AST.JLS3);
  		parser.setKind(ASTParser.K_COMPILATION_UNIT);
  		parser.setSource(icu);
  		parser.setCompilerOptions(options);
  		parser.setResolveBindings(true);
  		CompilationUnit cuCopy = (CompilationUnit)parser.createAST(null);
  		this.cu = cuCopy;
  		
  		return cuCopy;
	}
	
	public CompilationUnit updateCompilationUnit(){
		char[] charArrays = null;
		try {
			charArrays = icu.getSource().toCharArray();
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		@SuppressWarnings("deprecation")
		ASTParser parser = ASTParser.newParser(AST.JLS3);
  		parser.setKind(ASTParser.K_COMPILATION_UNIT);
  		parser.setSource(charArrays);
  		parser.setCompilerOptions(options);
  		parser.setResolveBindings(true);
  		CompilationUnit cuCopy = (CompilationUnit)parser.createAST(null);
  		this.cu = cuCopy;
  		return cuCopy;
	}
	
	public ICompilationUnit getICompilationUnit(){
		return icu;
	}
	
	public CompilationUnit getCompilationUnit(){
  		return cu;
	}
	
	
	/**
	 * 
	 * Aplica el refactoring de extraccion y actualiza la CompilationUnit
	 * Es indispensable que se instancia la Clase RefactoringStatus ya que sino el refactoring
	 * no se ejecuta.
	 */
	public String applyExtractRefactoring(StatementSet s){
		return applyRefactor(s.getStartPosition(),s.getLength());
	}
	
	public String applyExtractRefactoring(int start, int length){
		return applyRefactor(start, length);
	}
	
	
	private String applyRefactor(int start, int length){
		String name = "extracted_"+numbermethodsextracted+"_"+originalMethodName;
		ExtractMethodRefactoring refactoring = new ExtractMethodRefactoring(icu, start,length);			
		refactoring.setMethodName(name);
		@SuppressWarnings("unused")
		RefactoringStatus result = checkConditions(refactoring);
		performChange(refactoring);
    	
    	//Actualizo la CompilationUnit
    	updateCompilationUnit();
    	
    	//Aumento el nombre de llos metodos extracted
		numbermethodsextracted++;
    	return name;
	}
	/**
	 * Aplica el cambio a la icompilationUnit
	 * @param r
	 */
	private void performChange(Refactoring r){
		CompilationUnitChange cd = new CompilationUnitChange("nuevaCUChange", icu);
    	try {
			cd = (CompilationUnitChange) r.createChange(pm);
			cd.perform(pm);
    	} catch (Exception e) {    		
			e.printStackTrace();
		}
	}
	
	private RefactoringStatus checkConditions(ExtractMethodRefactoring r){
		try {
			return r.checkAllConditions(pm);
		} catch (OperationCanceledException | CoreException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 
	 * Como el nombre del method viene generalmente en el formato de SimpleName
	 * Se genero un metodo aropiado.
	 */
	public MethodDeclaration getMethod(SimpleName name){
		return getMethod(name.getFullyQualifiedName());
	}
	/**
	 * 
	 * Se Encarga de visitar la CompilationUnit para obtener el MethodDecalration
	 * Que es el codigo del metodo.
	 */
	public MethodDeclaration getMethod(String name){
		MethodDeclaration method; 
		visitador = new Visitor();
		cu.accept(visitador);
		method = visitador.getMethodFromName(name);
		return method;
	}
	
	public static void restartCounter(){
		numbermethodsextracted = 0;
	}
	
	public ArrayList<String> getRefactoredMethods(boolean withOriginal) {
		ArrayList<String> aux = new ArrayList<String>();
		if(withOriginal)
			aux.add(originalMethodName);
		visitador = new Visitor();
		cu.accept(visitador);
		for(MethodDeclaration d : visitador.getAllMethods()) {
			String name = d.getName().getFullyQualifiedName();
			if(name.contains("extracted_") && name.contains("_" + originalMethodName))
				aux.add(name);
		}
		return aux;
	}
	
	public String getPreview(){
		String solutionPreview = "";
		for(String methodName : getRefactoredMethods(true)) { 
			solutionPreview += ((getMethod(methodName).toString()) + "\n");
		}
		return solutionPreview;
	}
	
	public ArrayList<MethodDeclaration> getAllMethods(){
		visitador = new Visitor();
		cu.accept(visitador);
		return visitador.getAllMethods();
	}
}
