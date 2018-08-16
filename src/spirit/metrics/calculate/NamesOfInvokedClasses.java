package spirit.metrics.calculate;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.MethodInvocation;

import spirit.metrics.constants.MetricNames;
import spirit.metrics.storage.ClassMetrics;
import spirit.metrics.storage.MethodMetrics;

public class NamesOfInvokedClasses implements IAttribute{
	
	List<String> nameOfClasses;
	
	public NamesOfInvokedClasses(List<String> nameOfClasses) {
		this.nameOfClasses = nameOfClasses;
	}
	
	@Override
	public String getName() {
		return MetricNames.ListOfClassInvoked;
	}

	@Override
	public void calculate(ClassMetrics node) {
		List<String> linvoked = new ArrayList<String>();
		String nameOfClass = node.getDeclaration().resolveBinding().getBinaryName();
		List<MethodMetrics> lmethod = node.getMethodsMetrics();
		List<MethodInvocation> lClassMethodInv = new ArrayList<MethodInvocation>();
		List<String> linvokedByMethod = new ArrayList<String>();
		for(MethodMetrics methodMetrics:lmethod){
			linvokedByMethod.addAll((List<String>) methodMetrics.getAttribute(MetricNames.ListOfClassInvoked));
			lClassMethodInv.addAll((List<MethodInvocation>) methodMetrics.getAttribute(MetricNames.ListOfMethodInvoked));
		}
		if(linvokedByMethod!=null){
			linvokedByMethod.remove(nameOfClass);
			for(String invoked:linvokedByMethod){
				if(!linvoked.contains(invoked) && nameOfClasses.contains(invoked)){
					linvoked.add(invoked);
				}
			}
		}
		node.setAttribute(getName(), linvoked);
		node.setAttribute(MetricNames.ListOfMethodInvoked, lClassMethodInv);
	}

	@Override
	public void calculate(MethodMetrics node) {
		List<String> linvokedByMethod = (List<String>) node.getAttribute(MetricNames.ListOfClassInvoked);
		
		//Elimino de la lista de clases aquellas que no pertenecen al proyecto
		for(int i=0;i<linvokedByMethod.size();i++){
			String name = linvokedByMethod.get(i);
			if(!nameOfClasses.contains(name)){
				linvokedByMethod.remove(name);
				i--;
			}
		}
		
	}

}
