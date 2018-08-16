package bandago.operators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import bandago.utils.StatementSet;

public class RandomOperator extends Operator{
	
	
	public RandomOperator(){
		super();
		name = "Random Operator";
	}
	
	public StatementSet getSelectedStatement(ArrayList<StatementSet> statements){
		
		//Ordeno Random
		long seed = System.nanoTime();
		Collections.shuffle(statements, new Random(seed));
		
		//Me fijo cuales son los statements que no dan error cuando 
		//los saco y me quedo con el primero
		return checkError(statements);
	}
}
