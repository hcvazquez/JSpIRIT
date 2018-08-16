package spirit.core.smells;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import spirit.metrics.constants.MetricNames;
import spirit.metrics.storage.ClassMetrics;

public class BrainClass extends CodeSmell{
	public static String NAME="Brain Class";
	public BrainClass(ClassMetrics node) {
		super(NAME);
		this.element=node.getDeclaration();
		this.node=node;
	}
	@Override
	public TypeDeclaration getMainClass() {
		if(this.getElement() instanceof TypeDeclaration){
	    	return ((TypeDeclaration)(this.getElement()));
	    }
		return null;
	}
	
	/**All the classes invoked and invoking the main class of the smell
	 * 
	 */
	@Override
	public Set<String> getAffectedClasses() {
		List<String> list = (List<String>)(node.getAttribute(MetricNames.ListOfClassInvoked));
		for(String name:(List<String>)(node.getAttribute(MetricNames.ListOfClassInvoking))){
			if(!list.contains(name)){
				list.add(name);
			}
		}
		return new TreeSet<String>(list);
	}
	@Override
	public boolean isMethodSmell() {
		return false;
	}
	@Override
	public boolean isClassSmell() {
		return true;
	}
}
