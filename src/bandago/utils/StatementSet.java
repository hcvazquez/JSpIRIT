package bandago.utils;
import java.util.ArrayList;

import org.eclipse.jdt.core.dom.Statement;

import bandago.filters.StatementFilter;
import bandago.metrics.CiclomaticCalculator;
import bandago.metrics.NestingCalculator;
import bandago.metrics.NofCalculator;

public class StatementSet{
	
	public static final int GROUP = 1000;
	public static final int COMMENT = 1001;
	private ArrayList<Statement> statements = new ArrayList<Statement>();
	private int type;
	
	public StatementSet(int t, Statement s){
		this.type = t;
		statements.add(s);
	}
	
	public StatementSet(int t){
		this.type = t;
	}
	
	public int getType(){
		return type;
	}
	
	public void addStatement(Statement s){
		statements.add(s);
	}
	
	public int size(){
		return statements.size();
	}
	
	public Statement getStatement(int index){
		 return statements.get(index);
	}
	
	public void removeStatement(int index){
		 statements.remove(index);
	}
	
	public int getStartPosition(){
		return statements.get(0).getStartPosition();
	}
	
	public int getLength(){
		int l = 0;
		for(Statement statement: statements){
			l = l + statement.getLength();
		}
		return l;
	}
	
	public String print(){
		String s = "Set De Statements" + "\n";
		for(Statement statement: statements){
			s = s + "      " +statement.toString() + "\n";
		}
		return s;
	}
	
	public int getAllLengthInLines(){
		int s = 0;
		for(Statement statement: statements){
			s = s + StatementFilter.countLines(statement.toString());
		}
		return s;
	}
	
	public int getNestingCount(){
		int m = 0;
		for(Statement s : statements){
			int aux = getNesting(s);
			if(aux>m)
				m = aux;
		}
		return m;
	}
	
	public int getNofCount(){
		int m = 0;
		for(Statement s: statements){
			int aux = getNof(s);
			if(aux>m)
				m = aux;
		}
		return m;
	}
	
	public int getCiclomaticCount(){
		int m = 0;
		for(Statement s : statements){
			int aux = getCiclomatic(s);
			if(aux>m)
				m = aux;
		}
		return m;
	}
	
	public static int getCiclomatic(Statement s){
		CiclomaticCalculator c = new CiclomaticCalculator(s);
		return c.getCyclomatic();
	}
	
	public static int getNesting(Statement s){
		NestingCalculator nc = new NestingCalculator(s);
		return nc.getNestingCount();
	}
	
	public static int getNof(Statement s) {
		NofCalculator nof = new NofCalculator(s);
		return nof.getNofCount();
	}
	
	@Override
	public boolean equals(Object o){
		if(o != null){
			StatementSet s = (StatementSet) o;
			if(s.size() == statements.size()){
				int j = 0;
				for(int i = 0; i< s.size();i++){
					String a = s.getStatement(i).toString();
					String b = statements.get(i).toString();
					if(a.equals(b)){
						j++;
					}
				}
				if(j == statements.size())
					return true;
				return false;
			}
			else
				return false;
		}
		else
			return false;
	}
}
