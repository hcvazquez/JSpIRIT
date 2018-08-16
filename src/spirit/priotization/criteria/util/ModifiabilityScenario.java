package spirit.priotization.criteria.util;

import java.util.Vector;

public class ModifiabilityScenario extends MappedInformation{
	
	private String description;
	
	private Double importance;
	
	public ModifiabilityScenario(String name, String description,
			Vector<String> classes, Vector<String> packages, Double importance,String projectName) {
		super(name,classes, packages, projectName);
		
		this.description = description;
		this.importance = importance;
		
	}
	public ModifiabilityScenario() {
		super(null,null,null,null);
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Double getImportance() {
		return importance;
	}
	public void setImportance(Double importance) {
		this.importance = importance;
	}
	

	
}
