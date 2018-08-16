package spirit.ui.views.agglomerations;

import spirit.core.agglomerations.model.projectBuilders.impl.ProjectFromPackageStructureBuilder;

public class AgglomerationConfigurationForProject {
	private String structureSource;
	private Integer IntraMethodThreshold;
	private Integer IntraComponentThreshold;
	private Integer IntraClassThreshold;
	private Integer HierarchicalThreshold;
	private Long id;
	private String projectName;
	
	public AgglomerationConfigurationForProject(String projectName) {
		this.projectName=projectName;
		//Preinitialization just in case
		structureSource=ProjectFromPackageStructureBuilder.NAME;
		IntraMethodThreshold=0;
		IntraComponentThreshold=1;
		HierarchicalThreshold=0;
		IntraClassThreshold=1;
	}
	
	public AgglomerationConfigurationForProject() {
		// TODO Auto-generated constructor stub
	}
	public String getStructureSource() {
		return structureSource;
	}
	public void setStructureSource(String structureSource) {
		this.structureSource = structureSource;
	}
	public Integer getIntraMethodThreshold() {
		return IntraMethodThreshold;
	}
	public void setIntraMethodThreshold(Integer intraMethodThreshold) {
		IntraMethodThreshold = intraMethodThreshold;
	}
	public Integer getIntraComponentThreshold() {
		return IntraComponentThreshold;
	}
	public void setIntraComponentThreshold(Integer intraComponentThreshold) {
		IntraComponentThreshold = intraComponentThreshold;
	}
	public Integer getHierarchicalThreshold() {
		return HierarchicalThreshold;
	}
	public void setHierarchicalThreshold(Integer hierarchicalThreshold) {
		HierarchicalThreshold = hierarchicalThreshold;
	}
	public Long getId() {
		return id;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Integer getIntraClassThreshold() {
		return IntraClassThreshold;
	}
	public void setIntraClassThreshold(Integer intraClassThreshold) {
		IntraClassThreshold = intraClassThreshold;
	}
}
