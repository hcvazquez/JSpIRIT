package spirit.ui.views.data.agglomerations;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;

public class DataFeedFunctionAgglomerations extends BrowserFunction {

	private Browser browser;
	private String functionName;

	public DataFeedFunctionAgglomerations(Browser browser, String name) {
		super(browser, name);
		this.browser = browser;
		this.functionName = name;
	}

	public Object function(Object[] arguments) {
		Double arg = Double.parseDouble(arguments[0].toString());
		int option = arg.intValue();
		String data = new String();
		switch(option) {
		case 0:
			data = DataFeedAgglomerations.getInstance().getIntraComponentData();
			break;
		case 1:
			data = DataFeedAgglomerations.getInstance().getHierarchicalData();
			break;
		case 2:
			data = DataFeedAgglomerations.getInstance().getPackageDependencies();
			break;
		case 3:
			data = DataFeedAgglomerations.getInstance().getIntraClassData();
			break;
		
		}
		return data;
	}
}
