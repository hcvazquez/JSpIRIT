package spirit.priotization.criteria.util;

import java.util.Iterator;
import java.util.List;

public class NOMHistoryOfAVersion {
	private List<NOMOfAClass> nomValues;
	private Long id;
	private String pathToSource;
	public NOMHistoryOfAVersion(List<NOMOfAClass> nomValues,String pathToSource) {
		super();
		this.nomValues = nomValues;
		this.pathToSource=pathToSource;
	}
	public NOMHistoryOfAVersion() {
		// TODO Auto-generated constructor stub
	}
	public List<NOMOfAClass> getNomValues() {
		return nomValues;
	}

	public void setNomValues(List<NOMOfAClass> nomValues) {
		this.nomValues = nomValues;
	}
	public boolean containsClass(String aClassName){
		for (Iterator<NOMOfAClass> iterator = nomValues.iterator(); iterator.hasNext();) {
			NOMOfAClass type = (NOMOfAClass) iterator.next();
			if(type.getaClassName().equals(aClassName))
				return true;
		}
		return false;
	}
	public NOMOfAClass getNOMForClass(String aClassName){
		for (Iterator<NOMOfAClass> iterator = nomValues.iterator(); iterator.hasNext();) {
			NOMOfAClass type = (NOMOfAClass) iterator.next();
			if(type.getaClassName().equals(aClassName))
				return type;
		}
		return null;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setPathToSource(String pathToSource) {
		this.pathToSource = pathToSource;
	}
	public String getPathToSource() {
		return pathToSource;
	}
}
