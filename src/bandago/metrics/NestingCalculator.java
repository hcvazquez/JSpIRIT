package bandago.metrics;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.WhileStatement;

public class NestingCalculator extends ASTVisitor {
	
	private int nesting;
	private int maxNesting;
	
	public NestingCalculator(Statement s){
		nesting = 0;
		maxNesting = 0;
		s.accept(this);
	}
	
	public boolean visit(CatchClause node) {
		nesting++;
		if(node.getBody()!=null){
			node.getBody().accept(this);
			updateMaxNesting();
		}
		nesting--;
		return false;
	}
	
	public boolean visit(ConditionalExpression node) {	
		nesting++;
		if(node.getThenExpression()!=null){
			node.getThenExpression().accept(this);
			updateMaxNesting();
		}
		if(node.getElseExpression()!=null){
			node.getElseExpression().accept(this);
			updateMaxNesting();
		}
		nesting--;
		return false;
	}
	
	public boolean visit(DoStatement node) {
		nesting++;
		if(node.getBody()!=null){
			node.getBody().accept(this);
			updateMaxNesting();
		}
		nesting--;
		return false;
	}
	
	public boolean visit(ForStatement node) {
		nesting++;
		if(node.getBody()!=null){
			node.getBody().accept(this);
			updateMaxNesting();
		}
		nesting--;
		return false;
	}
	
	public boolean visit(EnhancedForStatement node) {
		nesting++;
		if(node.getBody()!=null){
			node.getBody().accept(this);
			updateMaxNesting();
		}
		nesting--;
		return false;
	}
	
	public boolean visit(IfStatement node) {
		nesting++;
		if(node.getThenStatement()!=null){
			node.getThenStatement().accept(this);
			updateMaxNesting();
		}
		if(node.getElseStatement()!=null){
			node.getElseStatement().accept(this);
			updateMaxNesting();
		}
		nesting--;
		return false;
	}

	public boolean visit(WhileStatement node) {
		nesting++;
		if(node.getBody()!=null){
			node.getBody().accept(this);
			updateMaxNesting();
		}
		nesting--;
		return false;
	}
	
	public int getNestingCount(){
		return maxNesting;
	}
	
	private void updateMaxNesting(){
		if(maxNesting < nesting){
			maxNesting = nesting;
		}
	}
	
}
