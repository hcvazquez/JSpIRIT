package spirit.core.agglomerations.model.projectBuilders;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import spirit.core.agglomerations.model.Component;


/**
 * Visitor responsible for decorating TypeDeclarations with the component in which it belongs.
 * @author Willian
 *
 */
public class ComponentVisitor extends ASTVisitor {

	private static final String ARCHITECTURAL_COMPONENT = "Architectural_Component";
	private Component component;

	public ComponentVisitor(Component component) {
		this.component = component;
	}
	
	@Override
	public boolean visit(TypeDeclaration node) {
		component.addClass(node);
		node.setProperty(ARCHITECTURAL_COMPONENT, component);
		return super.visit(node);
	}

}
