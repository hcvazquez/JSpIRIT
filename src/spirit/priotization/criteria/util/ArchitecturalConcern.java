package spirit.priotization.criteria.util;

import java.util.Vector;

public class ArchitecturalConcern extends MappedInformation{
	//private String kindOfProblem;
	private Double importance;
	public ArchitecturalConcern(String name, /*String kindOfProblem,*/
			Vector<String> classes, Vector<String> packages, String projectName,Double importance) {
		super(name,classes, packages, projectName);
		
		//this.kindOfProblem = kindOfProblem;
		this.importance=importance;
	}
	public ArchitecturalConcern() {
		super(null,null,null,null);
	}
	/*public String getKindOfProblem() {
		return kindOfProblem;
	}
	public void setKindOfProblem(String kindOfProblem) {
		this.kindOfProblem = kindOfProblem;
	}*/
	public Double getImportance() {
		return importance;
	}
	public void setImportance(Double importance) {
		this.importance = importance;
	}
	
}
