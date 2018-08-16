package bandago.metrics;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import spirit.metrics.calculate.MaximumNestingLevel;
import spirit.metrics.calculate.McCabe;
import spirit.metrics.calculate.NameOfFields;
import spirit.metrics.calculate.NumberOfFields;
import spirit.metrics.calculate.NumberOfLinesOfCode;
import spirit.metrics.constants.MetricThresholds;
import spirit.metrics.storage.MethodMetrics;

@SuppressWarnings("rawtypes")
public class MetricAnalyzer implements Comparable{
	private ArrayList<MethodDeclaration> methods = new ArrayList<MethodDeclaration>();
	private float NOF = 0;
	private float MNL = 0;
	private float LOC = 0;
	private float WMC = 0;
	
	public MetricAnalyzer(ArrayList<MethodDeclaration> methods){
		this.methods = methods;
		
		calculateMetric();
	}
	
	public String getMethodName(){
		if(methods.size() > 1){
			return "";
		}
		else{
			if(methods.size() == 1)
				return methods.get(0).getName().getFullyQualifiedName();
			else
				return "";
		}
	}
	
	private float calculateLOC(MethodMetrics metrics) {
		NumberOfLinesOfCode lines = new NumberOfLinesOfCode();
		lines.calculate(metrics);
		return metrics.getMetric(lines.getName());
	}
	
	private float calculateMNL(MethodMetrics metrics) {
		MaximumNestingLevel nesting = new MaximumNestingLevel();
		nesting.calculate(metrics);
		return metrics.getMetric(nesting.getName());
	}
	
	private float calculateWMC(MethodMetrics metrics) {
		NameOfFields nameoffield = new NameOfFields();
		nameoffield.calculate(metrics);
		McCabe cabe = new McCabe();
		cabe.calculate(metrics);
		return metrics.getMetric(cabe.getName());
	}
	
	private float calculateNOF(MethodMetrics metrics) {
		NumberOfFields numberoffields = new NumberOfFields();
		numberoffields.calculate(metrics);
		return metrics.getMetric(numberoffields.getName());
	}
	
	private void calculateMetric(){
		float auxsumLOC = 0;
		float auxsumMNL = 0;
		float auxsumWMC = 0;
		float auxsumNOF = 0;
		for(MethodDeclaration method: methods){
			MethodMetrics n = new MethodMetrics(method, null);
			auxsumLOC += calculateLOC(n);
			auxsumMNL += calculateMNL(n);
			auxsumWMC += calculateWMC(n);
			auxsumNOF += calculateNOF(n);
		}
		NOF = auxsumNOF / methods.size();
		MNL = auxsumMNL / methods.size();
		LOC = auxsumLOC / methods.size();
		WMC = auxsumWMC / methods.size();	
		
	}
	
	public float numberOfLinesOfCode(){
		return LOC;
	}
	
	public float maximumNestingLevel(){
		return MNL;
	}
	
	public float ciclomaticComplexity(){
		return WMC;
	}
	
	public float numberOfFields(){
		return NOF;
	}
	
	public boolean stillBrainMethod(){
		for(MethodDeclaration method: methods){
			MethodMetrics n = new MethodMetrics(method, null);
		//	System.out.println("LOC: "+calculateLOC(n)+" Nesting: "+calculateMNL(n)+ "CYCLO: "+calculateWMC(n)+ "NOAV: "+calculateNOF(n));
			if(	calculateLOC(n) > MetricThresholds.LOCVeryHigh &&
				calculateMNL(n) >= MetricThresholds.DEEP &&
				calculateWMC(n) >= MetricThresholds.MANY &&
				calculateNOF(n) >= MetricThresholds.SMemCap ){
				return true;
			}
		}
		return false;
	}
	
	public int quantityOfExtractedMethods(){
		return methods.size() -1;
	}
	
	public boolean equals(Object o ){
		MetricAnalyzer m = (MetricAnalyzer)o;
		if(this.LOC == m.numberOfLinesOfCode() &&
			this.MNL == m.maximumNestingLevel() &&
			this.NOF == m.numberOfFields() &&
			this.WMC == m.ciclomaticComplexity())
			return true;
		return false;
	}

	@Override
	public int compareTo(Object o) {
		MetricAnalyzer m = (MetricAnalyzer) o;
		
		if(!this.getMethodName().startsWith("extracted_"))
			return -1;
		if(!m.getMethodName().startsWith("extracted_"))
			return 1;
		int a = getNumberFromName(m.getMethodName());
		int b = getNumberFromName(this.getMethodName());
		
		if(a == b)
			return 0;
		if(a>b)
			return -1;
		if(b>a)
			return 1;
		return 0;
	}
	
	private int getNumberFromName(String s){
		String[] aux = s.split("_");
		return Integer.valueOf(aux[1]);
	}

}
