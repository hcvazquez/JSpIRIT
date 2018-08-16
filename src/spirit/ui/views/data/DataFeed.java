package spirit.ui.views.data;

public class DataFeed {

	private String smellsByPackage;
	private String smellsByPackage2;
	private String relationships;
	private String colors;
	private String colors2;
	private static DataFeed instance;

	private DataFeed() {
		smellsByPackage = new String();
		relationships = new String();
		colors = new String();
		colors2 = new String();
	}

	public static DataFeed getInstance() {
		if (instance == null) { // first time lock
			synchronized (DataFeed.class) {
				if (instance == null) { // second time lock
					instance = new DataFeed();
				}
			}
		}
		return instance;
	}

	public String getSmellsByPackage() {
		return smellsByPackage;
	}

	public void setSmellsByPackage(String smellsByPackage) {
		this.smellsByPackage = smellsByPackage;
	}

	public String getRelationships() {
		return relationships;
	}

	public void setRelationships(String relationships) {
		this.relationships = relationships;
	}

	public String getColors() {
		return colors;
	}

	public void setColors(String colors) {
		this.colors = colors;
	}
	
	public String getColors2() {
		return colors2;
	}

	public void setColors2(String colors2) {
		this.colors2 = colors2;
	}

	public String getSmellsByPackage2() {
		return smellsByPackage2;
	}

	public void setSmellsByPackage2(String smellsByPackage2) {
		this.smellsByPackage2 = smellsByPackage2;
	}

}
