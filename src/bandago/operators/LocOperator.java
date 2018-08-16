package bandago.operators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import bandago.utils.StatementSet;

public class LocOperator extends Operator{
	
	public LocOperator(){
		super();
		name = "Max Operator";
	}
	
	public StatementSet getSelectedStatement(ArrayList<StatementSet> statements) {
		//Ordeno todos los statements de mayor a menor
		Collections.sort(statements,new Comparator<StatementSet>(){
            public int compare(StatementSet s1,StatementSet s2){
            	int a = s1.getAllLengthInLines();
    			int b = s2.getAllLengthInLines();
    			return a > b ? -1 : a == b ? 0 : 1;
          }});
		
		//Me fijo cuales son los statements que no dan error cuando 
		//los saco y me quedo con el primero
		return checkError(statements);
	}
}