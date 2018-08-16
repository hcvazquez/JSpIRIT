package bandago.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.EmptyStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;

public class Visitor extends ASTVisitor {
  List<MethodDeclaration> methods = new ArrayList<MethodDeclaration>();
  
  @Override
  public boolean visit(MethodDeclaration node) {
    methods.add(node);
    return super.visit(node);
  }
  
  public List<MethodDeclaration> getMethods() {
    return methods;
  }
  
  public MethodDeclaration getMethodFromName(SimpleName s){
	  return getMethodFromName(s.getFullyQualifiedName());
  }

  public boolean visit(EmptyStatement node) {
      System.out.println("Encontre");
      return true;
  }
  
  public MethodDeclaration getMethodFromName(String s){
	  for (MethodDeclaration method : this.getMethods()) {
	       if(method.getName().getFullyQualifiedName().equals(s)){
	    	   return method;
	       }
	  }	
	  return null;
  }
  
  public ArrayList<MethodDeclaration> getAllMethods(){
	  return (ArrayList<MethodDeclaration>) this.getMethods();
  }
  
  
} 