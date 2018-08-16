package spirit.ui.views.data.agglomerations;

public class DataFeedAgglomerations {
	
	private String intraComponentData;
	private String hierarchicalData;
	private String packageDependencies;
	private String intraClassData;
	
	private static DataFeedAgglomerations instance;

	private DataFeedAgglomerations() {
		intraComponentData = new String();
		hierarchicalData = new String();
		packageDependencies = new String();
		setIntraClassData(new String());
	}

	public static DataFeedAgglomerations getInstance() {
		if (instance == null) { // first time lock
			synchronized (DataFeedAgglomerations.class) {
				if (instance == null) { // second time lock
					instance = new DataFeedAgglomerations();
				}
			}
		}
		return instance;
	}

	public String getIntraComponentData() {
		return intraComponentData;
	}

	public void setIntraComponentData(String intraComponentData) {
		this.intraComponentData = intraComponentData;
	}

	public String getHierarchicalData() {
		return hierarchicalData;
	}

	public void setHierarchicalData(String hierarchicalData) {
		this.hierarchicalData = hierarchicalData;
	}

	public String getPackageDependencies() {
		return packageDependencies;
	}

	public void setPackageDependencies(String packageDependencies) {
		this.packageDependencies = packageDependencies;
	}

	public String getIntraClassData() {
		return intraClassData;
	}

	public void setIntraClassData(String intraClassData) {
		this.intraClassData = intraClassData;
	}
	

}
