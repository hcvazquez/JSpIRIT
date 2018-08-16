package bandago.filters;

import java.util.ArrayList;

import bandago.utils.StatementSet;

public class GroupStatementFilter extends StatementFilter{
	
	private int realMinimumStatements =0;
	
	public GroupStatementFilter(int minimunStatements){
		super(1);
		realMinimumStatements = minimunStatements;
		name = "Group Statement Getter";
	}

	@Override
	protected void getter() {
		ArrayList<StatementSet> auxstatements = new ArrayList<StatementSet>();
		int	posNextStatement = 0;
		int	posActualStatement = 2;
		
		//Creo un StatementSet para acumular los statements
		StatementSet acumulado = new StatementSet(StatementSet.GROUP);
		
		for(int i =0; i<(statements.size()-1) ;i++ ){
			
			//Agrego el primero del grupo
			acumulado.addStatement(statements.get(i).getStatement(0));
			
			//Obtengo Los dos numeros de linea de los statements y me fijo si estan seguidos
			posActualStatement = icuManager.getCompilationUnit().getLineNumber(statements.get(i).getStartPosition() + statements.get(i).getLength());
			posNextStatement = icuManager.getCompilationUnit().getLineNumber(statements.get(i+1).getStartPosition());
			
			//Avanzo al siguiente
			if(checkDistance(posNextStatement, posActualStatement)){
				i++;
				//Acumulo Statements si estan en la misma linea no ni si estan mas separado que una linea
				while((i<statements.size()) && (checkDistance(posNextStatement, posActualStatement))){
					acumulado.addStatement(statements.get(i).getStatement(0));
					
					posActualStatement = icuManager.getCompilationUnit().getLineNumber(statements.get(i).getStartPosition() + statements.get(i).getLength());
					if(i+1<statements.size())
						posNextStatement = icuManager.getCompilationUnit().getLineNumber(statements.get(i+1).getStartPosition());
					//Avanzo el i solo si es posible seguir agregando
					if(checkDistance(posNextStatement, posActualStatement))
						i++;
				}	
			}
			
			//Agrego el acumulado de statements
			if(acumulado.getAllLengthInLines() >= this.realMinimumStatements)
				auxstatements.add(acumulado);
			
			//Vacio el acumulado
			acumulado = new StatementSet(StatementSet.GROUP);
		}
		statements = auxstatements;
	}
	
	private boolean checkDistance(int pos1, int pos2){
		int aux = pos1-pos2;
		if(aux == 1)
			return true;
		return false;
	}
}
