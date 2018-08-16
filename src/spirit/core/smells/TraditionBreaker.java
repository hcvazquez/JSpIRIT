package spirit.core.smells;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import spirit.metrics.constants.MetricNames;
import spirit.metrics.storage.ClassMetrics;

public class TraditionBreaker extends CodeSmell{
	public static String NAME="Tradition Breaker";
	public TraditionBreaker(ClassMetrics node) {
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
	
	/**All the super classes of  the main class of the smell
	 * 
	 */
	@Override
	public Set<String> getAffectedClasses() {
		return new TreeSet<String>((List<String>)(node.getAttribute(MetricNames.ListOfClassInvoked)));
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
