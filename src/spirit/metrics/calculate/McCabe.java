package spirit.metrics.calculate;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.WhileStatement;

import spirit.metrics.constants.MetricNames;
import spirit.metrics.storage.ClassMetrics;
import spirit.metrics.storage.MethodMetrics;

public class McCabe extends ASTVisitor implements IAttribute{
	
	private int cyclomatic = 1;
	
	public boolean visit(CatchClause node) {
		cyclomatic++;
		return true;
	}
	public boolean visit(ConditionalExpression node) {
		cyclomatic++;
		inspectExpression(node.getExpression());
		return true;
	}
	public boolean visit(DoStatement node) {
		cyclomatic++;
		inspectExpression(node.getExpression());
		return true;
	}
	public boolean visit(ForStatement node) {
		cyclomatic++;
		inspectExpression(node.getExpression());
		return true;
	}
	public boolean visit(IfStatement node) {
		cyclomatic++;
		inspectExpression(node.getExpression());
		return true;
	}
	public boolean visit(SwitchCase node) {
		if (!node.isDefault()) cyclomatic++;			
		return true;
	}
	public boolean visit(WhileStatement node) {
		cyclomatic++;
		inspectExpression(node.getExpression());
		return true;
	}
	public boolean visit(ExpressionStatement exs) {
		inspectExpression(exs.getExpression());
		return false;
	}
	/**
	 * Count occurrences of && and || (conditional and or)
	 * @param ex
	 */
	private void inspectExpression(Expression ex) {
		if ((ex != null)) {
			String expression = ex.toString();
			char[] chars = expression.toCharArray();
			for (int i = 0; i < chars.length-1; i++) {
				char next = chars[i];
				if ((next == '&' || next == '|')&&(next == chars[i+1])) {
					cyclomatic++;
				}
			}
		}
	}
	
	public int getCyclomatic(){
		return cyclomatic;
	}
	
	@Override
	public void calculate(ClassMetrics node){
		float sumOfATFD = 0;
		for(MethodMetrics methodMetrics:node.getMethodsMetrics()){
			sumOfATFD = sumOfATFD + methodMetrics.getMetric(getName());
		}
		node.setMetric(getName(), (float)sumOfATFD);
	}
	
	@Override
	public String getName() {
		return MetricNames.WMC;
	}
	@Override
	public void calculate(MethodMetrics node) {
		this.cyclomatic = 1;
		node.getDeclaration().accept(this);
		node.setMetric(getName(),this.cyclomatic);
	}
	
	
}