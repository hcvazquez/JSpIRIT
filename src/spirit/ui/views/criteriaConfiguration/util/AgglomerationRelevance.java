package spirit.ui.views.criteriaConfiguration.util;

public class AgglomerationRelevance {
	private Long id;
	private String agglomerationName;
	private Double relevance;
	public AgglomerationRelevance() {
		// TODO Auto-generated constructor stub
	}
	public AgglomerationRelevance(String agglomerationName, Double relevance) {
		super();
		this.agglomerationName = agglomerationName;
		this.relevance = relevance;
	}
	public String getAgglomerationName() {
		return agglomerationName;
	}
	public void setAgglomerationName(String agglomerationName) {
		this.agglomerationName = agglomerationName;
	}
	public Double getRelevance() {
		return relevance;
	}
	public void setRelevance(Double srelevance) {
		this.relevance = srelevance;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
}
