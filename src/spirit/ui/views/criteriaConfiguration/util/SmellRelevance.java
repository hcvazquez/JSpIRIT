package spirit.ui.views.criteriaConfiguration.util;

public class SmellRelevance {
	private Long id;
	private String smellName;
	private Integer relevance;
	public SmellRelevance() {
		// TODO Auto-generated constructor stub
	}
	public SmellRelevance(String smellName, Integer relevance) {
		super();
		this.smellName = smellName;
		this.relevance = relevance;
	}
	public String getSmellName() {
		return smellName;
	}
	public void setSmellName(String smellName) {
		this.smellName = smellName;
	}
	public Integer getRelevance() {
		return relevance;
	}
	public void setRelevance(Integer srelevance) {
		this.relevance = srelevance;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
}
