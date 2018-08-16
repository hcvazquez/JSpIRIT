package spirit.metrics.calculate;

import java.util.List;

import spirit.metrics.constants.MetricNames;
import spirit.metrics.storage.ClassMetrics;
import spirit.metrics.storage.MethodMetrics;

public class TightClassCohesion implements IAttribute{
	
	
	@Override
	public void calculate(ClassMetrics node) {
		node.setMetric(getName(), calculateTCC(node,node.getDeclaration().getMethods().length));
	}
	
	public float calculateTCC(ClassMetrics node, int numberOfPublicMethods){
		return ((float)calculateNDC(node)/(float)calculateNP(numberOfPublicMethods));
	}

	@Override
	public String getName() {
		return MetricNames.TCC;
	}

	@Override
	public void calculate(MethodMetrics node) {
	}
	
	public int calculateNDC(ClassMetrics node){
		int countPairMethods = 0;
		List<MethodMetrics> methodsMetrics = node.getMethodsMetrics();
		int index = 0;
		while(index+1 < methodsMetrics.size()){
			List<String> methodFieldsNames1 = (List<String>) methodsMetrics.get(index).getAttribute("MCFA");
			int index2 =  index+1;
			while(index2<methodsMetrics.size()){
				List<String> methodFieldsNames2 = (List<String>) methodsMetrics.get(index2).getAttribute("MCFA");
				for(String m1:methodFieldsNames1){
					if(methodFieldsNames2.contains(m1)){
						countPairMethods++;
						break;
					}
				}
				index2++;
			}
			index++;
		}
		return countPairMethods;
	}
	
	public float calculateNP(int numberOfPublicMethods){
		return (float)(numberOfPublicMethods*(numberOfPublicMethods-1))/(float)2;
	}

}
