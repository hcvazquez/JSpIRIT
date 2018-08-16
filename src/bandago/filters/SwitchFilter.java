package bandago.filters;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTNode;

import bandago.utils.StatementSet;

public class SwitchFilter extends StatementFilter {

	public SwitchFilter(int minimunStatements) {
		super(minimunStatements);
		name = "Switch-Case Getter";
	}

	protected void getter() {
		ArrayList<StatementSet> auxstatements = new ArrayList<StatementSet>();
		for(StatementSet s:statements){
			if ((s.getType() == ASTNode.SWITCH_STATEMENT))
				auxstatements.add(s);
		}
		statements = auxstatements;
	}
}
