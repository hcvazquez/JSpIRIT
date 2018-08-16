package bandago.filters;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTNode;

import bandago.utils.StatementSet;

public class IfFilter extends StatementFilter{

	public IfFilter(int minimunStatements) {
		super(minimunStatements);
		name = "If Getter";
	}

	protected void getter() {
		ArrayList<StatementSet> auxstatements = new ArrayList<StatementSet>();
		for(StatementSet s:statements){
			if ((s.getType() == ASTNode.IF_STATEMENT))
				auxstatements.add(s);
		}
		statements = auxstatements;
	}
}