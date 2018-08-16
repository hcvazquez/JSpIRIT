package bandago.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Solution {
	private ArrayList<String> operators = new ArrayList<String>();
	private ArrayList<String> getters = new ArrayList<String>();
	private HashMap<String,String> methods = new HashMap<String, String>();
	
	public void addMethod(String name, String code){
		if(!methods.containsKey(name)){
			methods.put(name, code);
		}
		else{
			methods.remove(name);
			methods.put(name, code);
		}
	} 
	
	/**
	 * Devuelve todos los nombres de los metodos involucrados en el refactoring
	 */
	public Set<String> getMethodsExtracted(){
		return methods.keySet();
	}
	
	public String getCode(){
		String aux ="";
		for (String name: methods.keySet()){
            String value = methods.get(name).toString();  
            aux += value + System.getProperty("line.separator");
		}
		return aux;
	}
	
	public void addOperatorAndGetter(String op, String get){
		operators.add(op);
		getters.add(get);
	}
	
	public String getUsedOperatorsAndGetters(){
		String aux ="";
		for(int i = 0; i< operators.size();i++){
			aux += operators.get(i) + ", " + getters.get(i) + System.getProperty("line.separator");
		}
		return aux;
	}
}
