package spirit.core.smells;

import java.util.Set;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import spirit.core.design.DesignFlaw;
import spirit.metrics.storage.NodeMetrics;

public abstract class CodeSmell extends DesignFlaw {
	private String kindOfSmellName;
	protected ASTNode element;
	protected NodeMetrics node;
	
	
	public CodeSmell(String kindOfSmellName) {
		this.kindOfSmellName=kindOfSmellName;
	}
	
	public ASTNode getElement() {
		return element;
	}
	public String getKindOfSmellName() {
		return kindOfSmellName;
	}
	public String getMainClassName(){
		//La tengo que devolver con puntos en lugar de / como esta en la hashtable
		//Un caso especial es si es inner class ya que el location es dentro de su padre
		//System.out.println(getMainClass().resolveBinding().getQualifiedName());
		
		//TEMPORAL FIX
		String main_class = getMainClass().resolveBinding().getQualifiedName();
		if(main_class.length()<1){
			return "UNKNOWN.";
		}
		else{
			return main_class;
		}
		
		
		//OLD SOLUTION
		//return getMainClass().resolveBinding().getQualifiedName();
		
	}
	public abstract TypeDeclaration getMainClass();
	
	/**Get all the affected classes by the code smell. This method depends on the kind of smell
	 * 
	 * @return a set of strings that contains the relative paths to the classes. e.g. "spirit.core.smells.CodeSmell"
	 */
	public abstract Set<String> getAffectedClasses();
	
	public String getElementName(){
		if(element instanceof TypeDeclaration){
	    	return ((TypeDeclaration)(element)).getName().toString();
	    }else if(element instanceof MethodDeclaration){
	    	return ( (TypeDeclaration)  ((MethodDeclaration)(element)).getParent()).getName()+"."+((MethodDeclaration)(element)).getName().toString();
	    }
	    return element.toString();
	}
	/**
	 * get the line of a method in a java file
	 */
	public int getLine(){
		IJavaElement javaElement=this.getMainClass().resolveBinding().getJavaElement();
		ICompilationUnit cu= (ICompilationUnit)javaElement.getAncestor(IJavaElement.COMPILATION_UNIT);
		
		
		return(this.getLineNumFromOffset(cu, element.getStartPosition()));//this.getiMethod().getSourceRange().getOffset()));
		
	}
	/**
	 * get the line of a method in a java file, given the offset
	 */
	private int getLineNumFromOffset(ICompilationUnit cUnit, int offSet){
        try {
                String source = cUnit.getSource();
                IType type = cUnit.findPrimaryType();
                if(type != null) {
                        String sourcetodeclaration = source.substring(0, offSet);
                        int lines = 0;
                        char[] chars = new char[sourcetodeclaration.length()];
                        sourcetodeclaration.getChars(
                                        0,
                                        sourcetodeclaration.length(),
                                        chars,
                                        0);
                        for (int i = 0; i < chars.length; i++) {
                                if (chars[i] == '\n') {
                                        lines++;
                                }
                        }
                        return lines + 1;
                }
        } catch (JavaModelException jme) {
        }
        return 0;      
	}
	public abstract boolean isMethodSmell();
	public abstract boolean isClassSmell();
}
