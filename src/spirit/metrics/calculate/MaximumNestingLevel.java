package spirit.metrics.calculate;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

import spirit.metrics.constants.MetricNames;
import spirit.metrics.storage.ClassMetrics;
import spirit.metrics.storage.MethodMetrics;

public class MaximumNestingLevel extends ASTVisitor implements IAttribute{
	
	private int MNL = 0;
	private int tempMNL = 0;
	
	public boolean visit(CatchClause node) {
		tempMNL++;
		if(node.getBody()!=null){
			node.getBody().accept(this);
			updateMNL();
		}
		tempMNL--;
		return false;
	}
	
	public boolean visit(ConditionalExpression node) {	
		tempMNL++;
		if(node.getThenExpression()!=null){
			node.getThenExpression().accept(this);
			updateMNL();
		}
		if(node.getElseExpression()!=null){
			node.getElseExpression().accept(this);
			updateMNL();
		}
		tempMNL--;
		return false;
	}
	
	public boolean visit(DoStatement node) {
		tempMNL++;
		if(node.getBody()!=null){
			node.getBody().accept(this);
			updateMNL();
		}
		tempMNL--;
		return false;
	}
	
	public boolean visit(ForStatement node) {
		tempMNL++;
		if(node.getBody()!=null){
			node.getBody().accept(this);
			updateMNL();
		}
		tempMNL--;
		return false;
	}
	
	public boolean visit(EnhancedForStatement node) {
		tempMNL++;
		if(node.getBody()!=null){
			node.getBody().accept(this);
			updateMNL();
		}
		tempMNL--;
		return false;
	}
	
	public boolean visit(IfStatement node) {
		tempMNL++;
		if(node.getThenStatement()!=null){
			node.getThenStatement().accept(this);
			updateMNL();
		}
		if(node.getElseStatement()!=null){
			node.getElseStatement().accept(this);
			updateMNL();
		}
		tempMNL--;
		return false;
	}

	public boolean visit(WhileStatement node) {
		tempMNL++;
		if(node.getBody()!=null){
			node.getBody().accept(this);
			updateMNL();
		}
		tempMNL--;
		return false;
	}
	
	public int getMNL(){
		return MNL;
	}
	
	private void updateMNL(){
		if(tempMNL>MNL){
			MNL=tempMNL;
		}		
	}
	
	@Override
	public void calculate(ClassMetrics node){
		float sumOfMNL = 0;
		for(MethodMetrics methodMetrics:node.getMethodsMetrics()){
			sumOfMNL = sumOfMNL + methodMetrics.getMetric(getName());
		}
		node.setMetric(getName(), (float)sumOfMNL);
	}
	
	@Override
	public String getName() {
		return MetricNames.MNL;
	}
	
	@Override
	public void calculate(MethodMetrics node) {
		MNL = 0;
		node.getDeclaration().accept(this);
		node.setMetric(getName(), MNL);
	}
	
	
}