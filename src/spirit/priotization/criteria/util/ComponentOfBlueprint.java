package spirit.priotization.criteria.util;

import java.util.Vector;

public class ComponentOfBlueprint extends MappedInformation{
	
	public ComponentOfBlueprint(String name,
			Vector<String> classes, Vector<String> packages, String projectName) {
		super(name,classes, packages, projectName);
	}
	public ComponentOfBlueprint() {
		super(null,null,null,null);
	}
}
