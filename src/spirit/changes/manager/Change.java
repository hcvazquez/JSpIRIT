package spirit.changes.manager;

import org.eclipse.core.resources.IResourceDelta;

public class Change {
	
	private Long id;
	private String clazz;
	private Integer type;
	
	public Change(){}
	//
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Change other = (Change) obj;
		if (clazz == null) {
			if (other.clazz != null)
				return false;
		} else if (!clazz.equals(other.clazz))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Change [id=" + id + ", clazz=" + clazz + ", type=" + type + "]";
	}

	public Change(String clazz, Integer type) {
		super();
		this.clazz = clazz;
		this.type = type;
	}

	public String getClassChanged() {
		return clazz;
	}

	public void setClassChanged(String clazz) {
		this.clazz = clazz;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public interface Type {
		public static final int ADDED = IResourceDelta.ADDED;
		public static final int CHANGED = IResourceDelta.CHANGED;
		public static final int REMOVED = IResourceDelta.REMOVED;
	}


}
