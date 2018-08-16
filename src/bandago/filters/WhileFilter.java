package bandago.filters;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTNode;

import bandago.utils.StatementSet;

public class WhileFilter  extends StatementFilter{

	public WhileFilter(int minimunStatements) {
		super(minimunStatements);
		name = "While Getter";
	}

	protected void getter() {
		ArrayList<StatementSet> auxstatements = new ArrayList<StatementSet>();
		for(StatementSet s:statements){
			if ((s.getType() == ASTNode.WHILE_STATEMENT))
				auxstatements.add(s);
		}
		statements = auxstatements;
	}
}
