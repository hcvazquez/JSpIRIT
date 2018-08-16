package bandago.filters;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTNode;

import bandago.utils.StatementSet;

public class TryFilter extends StatementFilter {

	public TryFilter(int minimunStatements) {
		super(minimunStatements);
		name = "Try-Catch Getter";
	}

	protected void getter() {
		ArrayList<StatementSet> auxstatements = new ArrayList<StatementSet>();
		for(StatementSet s:statements){
			if ((s.getType() == ASTNode.TRY_STATEMENT))
				auxstatements.add(s);
		}
		statements = auxstatements;
	}
}
