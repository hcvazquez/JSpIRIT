package bandago.metrics;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;


public class NofCalculator extends ASTVisitor{
	
	List<String> methodFieldsNames = new ArrayList<String>();
	List<String> methodAccessFieldsNames = new ArrayList<String>();
	List<String> classAccessWithThis = new ArrayList<String>();
	List<String> methodVariableAccess = new ArrayList<String>();
	private static final String THIS = "this";

	public NofCalculator(Statement s){
		s.accept(this);
		calculate(s);
	}
	
	public int getNofCount(){
		return methodVariableAccess.size();
	}
	
	@SuppressWarnings("unchecked")
	public boolean visit(VariableDeclarationStatement node) {
		List<VariableDeclarationFragment> declarations = node.fragments();
		if(declarations!=null){
			for(VariableDeclarationFragment declaration:declarations){
				methodFieldsNames.add(declaration.getName().getFullyQualifiedName());
			}
		}
		return true;
	}
	
	public boolean visit(SimpleName node) {
		if(!methodAccessFieldsNames.contains((node.getFullyQualifiedName()))){
			methodAccessFieldsNames.add(node.getFullyQualifiedName());
		}
		return true;
	}
	
	public boolean visit(FieldAccess node) {
		if(node.getExpression()!=null){
			String exp = node.getExpression().toString();
			if(THIS.equals(exp)){
				classAccessWithThis.add(node.getName().getFullyQualifiedName());
			}
		}
		return true;
	}
	
	public void calculate(Statement s) {
		methodAccessFieldsNames = new ArrayList<String>();
		methodFieldsNames = new ArrayList<String>();
		classAccessWithThis = new ArrayList<String>();
		methodVariableAccess = new ArrayList<String>();
			
		methodVariableAccess.addAll(methodAccessFieldsNames);
		for(String name:methodFieldsNames){
			if(methodAccessFieldsNames.contains(name)){
				methodAccessFieldsNames.remove(name);
			}
			if(!methodVariableAccess.contains(name)){
				methodVariableAccess.add(name);
			}
		}
		
		for(String name:classAccessWithThis){
			if(!methodAccessFieldsNames.contains(name)){
				methodAccessFieldsNames.add(name);
			}
			if(!methodVariableAccess.contains(name)){
				methodVariableAccess.add(name);
			}
		}
	}
	
}
