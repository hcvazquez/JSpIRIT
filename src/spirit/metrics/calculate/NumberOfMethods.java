package spirit.metrics.calculate;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import spirit.metrics.constants.MetricNames;
import spirit.metrics.storage.ClassMetrics;
import spirit.metrics.storage.MethodMetrics;

public class NumberOfMethods implements IAttribute{

	@Override
	public void calculate(ClassMetrics node) {
		TypeDeclaration declaration = (TypeDeclaration) node.getDeclaration();
		node.setMetric(getName(), declaration.getMethods().length);
	}

	@Override
	public String getName() {
		return MetricNames.NOM;
	}

	@Override
	public void calculate(MethodMetrics node) {
		// TODO Auto-generated method stub
		
	}

}
