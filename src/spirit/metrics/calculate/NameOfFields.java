package spirit.metrics.calculate;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import spirit.metrics.constants.MetricNames;
import spirit.metrics.storage.ClassMetrics;
import spirit.metrics.storage.MethodMetrics;

public class NameOfFields extends ASTVisitor implements IAttribute{
	
	List<String> methodFieldsNames = new ArrayList<String>();
	List<String> methodAccessFieldsNames = new ArrayList<String>();
	List<String> parametersNames = new ArrayList<String>();
	List<String> classAccessWithThis = new ArrayList<String>();
	List<String> methodVariableAccess = new ArrayList<String>();
	private static final String THIS = "this";

	@Override
	public void calculate(ClassMetrics node) {
		List<String> classFieldsNames = new ArrayList<String>();
		TypeDeclaration classDeclaration = (TypeDeclaration) node.getDeclaration();
		for(FieldDeclaration field:classDeclaration.getFields()){
			List<VariableDeclarationFragment> declarations = field.fragments();
			for(VariableDeclarationFragment declaration:declarations){
				classFieldsNames.add(declaration.getName().getFullyQualifiedName());
			}
		}
		node.setAttribute(MetricNames.FON,classFieldsNames);
		List<MethodMetrics> methodsMetrics = node.getMethodsMetrics();
		
		for(MethodMetrics methodMetrics:methodsMetrics){
			List<String> methodFieldsNames1 = (List<String>) methodMetrics.getAttribute(MetricNames.MFA);
			for(int i=0;i<methodFieldsNames1.size();i++){
				String name = methodFieldsNames1.get(i);
				if(!classFieldsNames.contains(name)){
					methodFieldsNames1.remove(name);
					i--;
				}
			}
			methodMetrics.setAttribute("MCFA",methodFieldsNames1);
		}
		
	}

	@Override
	public String getName() {
		return MetricNames.NOF;
	}

	@Override
	public void calculate(MethodMetrics node) {
		methodAccessFieldsNames = new ArrayList<String>();
		methodFieldsNames = new ArrayList<String>();
		parametersNames = new ArrayList<String>();
		classAccessWithThis = new ArrayList<String>();
		methodVariableAccess = new ArrayList<String>();
		
		List<VariableDeclaration> parameters = node.getDeclaration().parameters();
		for(VariableDeclaration parameter:parameters){
			parametersNames.add(parameter.getName().getFullyQualifiedName());
		}
		if(node.getDeclaration().getBody()!=null){
			node.getDeclaration().getBody().accept(this);
			
			node.setAttribute(MetricNames.FON,methodFieldsNames);//method local variables
			
			methodVariableAccess.addAll(methodAccessFieldsNames);
			for(String name:methodFieldsNames){
				if(methodAccessFieldsNames.contains(name)){
					methodAccessFieldsNames.remove(name);
				}
				if(!methodVariableAccess.contains(name)){
					methodVariableAccess.add(name);
				}
			}
			for(String name:parametersNames){
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
		
		node.setAttribute(MetricNames.MFA,methodAccessFieldsNames);//access to class fields
		node.setAttribute(MetricNames.NOAV,methodVariableAccess);//total access name to variables
		
	}
	
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

}
