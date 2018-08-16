package bandago.filters;

public class AllStatementsFilter extends StatementFilter{

	
	public AllStatementsFilter(int minimunStatements) {
		super(minimunStatements);
		name = "All Statement Getter";
	}

	@Override
	protected void getter() {
		//Entrega los statements sin ninguna modificacion 
	}

	
}
