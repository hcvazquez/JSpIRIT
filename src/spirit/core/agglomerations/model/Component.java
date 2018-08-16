package spirit.core.agglomerations.model;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
 * Each instance of this class represents an architectural component.
 * @author Willian
 *
 */
public class Component {
	
	private final String name;
	private final Set<TypeDeclaration> classes = new HashSet<TypeDeclaration>();	
	
	public Component(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public Set<TypeDeclaration> getClasses() {
		return classes;
	}
	
	public void addClass(TypeDeclaration class_) {
		classes.add(class_);
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
