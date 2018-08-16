package spirit.changes.manager;

import java.util.Vector;

import spirit.core.smells.CodeSmell;

public class PreviousScanInfo {

	private Vector<CodeSmell> previousSmells;
	private static PreviousScanInfo instance;

	public static PreviousScanInfo getInstance() {
		if (instance == null) { // first time lock
			synchronized (PreviousScanInfo.class) {
				if (instance == null) { // second time lock
					instance = new PreviousScanInfo();
				}
			}
		}
		return instance;
	}

	private PreviousScanInfo() {
		setPreviousSmells(new Vector<CodeSmell>());
	}

	public Vector<CodeSmell> getPreviousSmells() {
		return previousSmells;
	}

	public void setPreviousSmells(Vector<CodeSmell> previousSmells) {
		this.previousSmells = previousSmells;
	}

}
