package bandago.filters;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTNode;

import bandago.utils.StatementSet;

public class ForFilter  extends StatementFilter{

	public ForFilter(int minimunStatements) {
		super(minimunStatements);
		name = "For Getter";
	}

	protected void getter() {
		ArrayList<StatementSet> auxstatements = new ArrayList<StatementSet>();
		for(StatementSet s:statements){
			if ((s.getType() == ASTNode.FOR_STATEMENT))
				auxstatements.add(s);
		}
		statements = auxstatements;
	}
}
