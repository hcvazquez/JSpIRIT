package spirit.metrics.calculate;

import java.util.List;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import spirit.metrics.constants.MetricNames;
import spirit.metrics.storage.ClassMetrics;
import spirit.metrics.storage.MethodMetrics;

public class NumberOfPublicFields implements IAttribute{
	@Override
	public void calculate(ClassMetrics node) {
		TypeDeclaration declaration = (TypeDeclaration) node.getDeclaration();
		
		FieldDeclaration[] fields = declaration.getFields();
		int publicAtt = 0;
		if(fields!=null){
			for(FieldDeclaration field:fields){
				List<IExtendedModifier> modifiers = field.modifiers();
				for(IExtendedModifier modifier:modifiers){
					if(modifier.isModifier()){
						if(((Modifier)modifier).isPublic()){
							publicAtt++;
						}
					}
				}
			}
		}
		
		node.setMetric(getName(), publicAtt);
	}

	@Override
	public String getName() {
		return MetricNames.NOPA;
	}

	@Override
	public void calculate(MethodMetrics node) {

	}

}
