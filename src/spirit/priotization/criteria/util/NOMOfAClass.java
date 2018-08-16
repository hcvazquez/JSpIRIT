package spirit.priotization.criteria.util;

public class NOMOfAClass {
	private String aClassName;
	private Integer nomValue;
	private Long id;
	public NOMOfAClass(String aClassName, Integer nomValue) {
		super();
		this.aClassName = aClassName;
		this.nomValue = nomValue;
	}
	public NOMOfAClass() {
		// TODO Auto-generated constructor stub
	}
	public String getaClassName() {
		return aClassName;
	}
	public void setaClassName(String aClassName) {
		this.aClassName = aClassName;
	}
	public Integer getNomValue() {
		return nomValue;
	}
	public void setNomValue(Integer nomValue) {
		this.nomValue = nomValue;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
