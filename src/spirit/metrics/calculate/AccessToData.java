package spirit.metrics.calculate;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.QualifiedName;

import spirit.core.design.CodeSmellsManagerFactory;
import spirit.metrics.constants.MetricNames;
import spirit.metrics.storage.ClassMetrics;
import spirit.metrics.storage.InvokingCache;
import spirit.metrics.storage.MethodMetrics;

public class AccessToData extends ASTVisitor implements IAttribute{

	private static final String GET = "get";
	private static final String SET = "set";
	
	private List<String> ListOfATFD = new ArrayList<String>();
	private List<String> ListOfATLD = new ArrayList<String>();
	private List<String> ListOfClassInvoked = new ArrayList<String>();
	private List<MethodInvocation> ListOfForeignMethodInvoked = new ArrayList<MethodInvocation>();
	private List<MethodInvocation> ListOfLocalMethodInvoked = new ArrayList<MethodInvocation>();
	private List<FieldAccess> ListOfLocalFieldUsed = new ArrayList<FieldAccess>();
	private String nameOfParentClass;
	private List<String> namesOfSuperClasses;
	
	private String keyOfMethod;
	
	@Override
	public void calculate(ClassMetrics node) {
		float sumOfATFD = 0;
		for(MethodMetrics methodMetrics:node.getMethodsMetrics()){
			sumOfATFD = sumOfATFD + methodMetrics.getMetric(getName());
		}
		node.setMetric(getName(), sumOfATFD);
	}

	@Override
	public String getName() {
		return MetricNames.ATFD;
	}

	@Override
	public void calculate(MethodMetrics node) {
		ListOfATFD = new ArrayList<String>();
		ListOfATLD = new ArrayList<String>();
		ListOfClassInvoked = new ArrayList<String>();
		ListOfForeignMethodInvoked = new ArrayList<MethodInvocation>();
		ListOfForeignMethodInvoked = new ArrayList<MethodInvocation>();
		ListOfLocalMethodInvoked = new ArrayList<MethodInvocation>();
		ListOfLocalFieldUsed = new ArrayList<FieldAccess>();
		
		nameOfParentClass  = node.getDeclaration().resolveBinding().getDeclaringClass().getBinaryName();
		namesOfSuperClasses = (List<String>) node.getClassMetrics().getAttribute(MetricNames.SC);
		keyOfMethod = node.getDeclaration().resolveBinding().getKey();
		node.getDeclaration().accept(this);
		node.setMetric(getName(),ListOfATFD.size());
		node.setAttribute(MetricNames.ListOfATFD, ListOfATFD);
		node.setMetric(MetricNames.ATLD,ListOfATLD.size());
		node.setAttribute(MetricNames.ListOfATLD, ListOfATLD);
		node.setAttribute(MetricNames.ListOfClassInvoked, ListOfClassInvoked);//Se completa en esta clase por cuestiones de performance
		node.setAttribute(MetricNames.ListOfMethodInvoked, ListOfForeignMethodInvoked);//Se completa en esta clase por cuestiones de performance
		
		node.setAttribute(MetricNames.ListOfLocalFieldUsed, ListOfLocalFieldUsed);//Se completa en esta clase por cuestiones de performance
		node.setAttribute(MetricNames.ListOfLocalMethodInvoked, ListOfLocalMethodInvoked);//Se completa en esta clase por cuestiones de performance
	}
	
	public boolean visit(MethodInvocation node) {
		if(node.resolveMethodBinding()!=null){
			String nameOfClass = node.resolveMethodBinding().getDeclaringClass().getBinaryName();
			String nameOfMethod = node.getName().getFullyQualifiedName();
			if(!nameOfClass.equals(nameOfParentClass)){
				if (CodeSmellsManagerFactory.getInstance().getCurrentProjectManager().getClassesList().contains(nameOfClass)){
					if(!ListOfClassInvoked.contains(nameOfClass)){
						ListOfClassInvoked.add(nameOfClass);
					}
					if(!ListOfForeignMethodInvoked.contains(node)){
						ListOfForeignMethodInvoked.add(node);
						InvokingCache.getInstance().saveInvocation(node, keyOfMethod, nameOfParentClass);
					}
					if((nameOfMethod.startsWith(GET)||nameOfMethod.startsWith(SET))){
						ListOfATFD.add(nameOfClass);
					}
				}
			}else{
				//access to local data
				if(!ListOfLocalMethodInvoked.contains(node)){
					ListOfLocalMethodInvoked.add(node);
				}
				if((nameOfMethod.startsWith(GET)||nameOfMethod.startsWith(SET))){
					ListOfATLD.add(nameOfClass);
				}
			}
		}
		return true;
	}
	
	
	public boolean visit(QualifiedName node) {
		if(node.getQualifier().resolveTypeBinding()!=null){
			String nameOfClass = node.getQualifier().resolveTypeBinding().getBinaryName();
			if(isForeignClass(nameOfClass)){
				if (CodeSmellsManagerFactory.getInstance().getCurrentProjectManager().getClassesList().contains(nameOfClass)){
					if(!ListOfClassInvoked.contains(nameOfClass)){
						ListOfClassInvoked.add(nameOfClass);
					}
					ListOfATFD.add(nameOfClass);
				}
			}else{
				//access to local field
				ListOfATLD.add(nameOfClass);
			}
		}
		return true;
	}
	
	public boolean visit(FieldAccess node) {
		if(node.resolveFieldBinding()!=null && node.resolveFieldBinding().getDeclaringClass()!=null){
			String nameOfClass = node.resolveFieldBinding().getDeclaringClass().getBinaryName();
			if(isForeignClass(nameOfClass)){
				if (CodeSmellsManagerFactory.getInstance().getCurrentProjectManager().getClassesList().contains(nameOfClass)){
					if(!ListOfClassInvoked.contains(nameOfClass)){
						ListOfClassInvoked.add(nameOfClass);
					}
					ListOfATFD.add(nameOfClass);
				}
			}else{
				//access to local field
				ListOfATLD.add(nameOfClass);
				if(!ListOfLocalFieldUsed.contains(node)){
					ListOfLocalFieldUsed.add(node);
				}
			}
		}
		return true;
	}
	
	private boolean isForeignClass(String nameOfClass){
		if(!nameOfClass.equals(nameOfParentClass) && !namesOfSuperClasses.contains(nameOfClass)){
			return true;
		}
		return false;
	}

}
