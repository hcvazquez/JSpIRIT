package bandago.operators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import bandago.utils.StatementSet;

public class NofOperator extends Operator {
	
	public NofOperator(){
		super();
		name = "NOF Operator";
	}

	public StatementSet getSelectedStatement(ArrayList<StatementSet> statements) {
		//Ordeno todos los statements de mayor a menor segun su NOF
		Collections.sort(statements,new Comparator<StatementSet>(){
		public int compare(StatementSet s1,StatementSet s2){
			int a = s1.getNofCount();
			int b = s2.getNofCount(); 
			return a > b ? -1 : a == b ? 0 : 1;
		}});
		return checkError(statements);
	}

}
