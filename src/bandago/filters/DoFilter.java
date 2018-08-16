package bandago.filters;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTNode;

import bandago.utils.StatementSet;

public class DoFilter extends StatementFilter {

	public DoFilter(int minimunStatements) {
		super(minimunStatements);
		name = "Do-While Getter";
	}

	protected void getter() {
		ArrayList<StatementSet> auxstatements = new ArrayList<StatementSet>();
		for(StatementSet s:statements){
			if ((s.getType() == ASTNode.DO_STATEMENT))
				auxstatements.add(s);
		}
		statements = auxstatements;
	}
}
