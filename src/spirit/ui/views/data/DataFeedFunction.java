package spirit.ui.views.data;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;

//Called by JavaScript
public class DataFeedFunction extends BrowserFunction {

	private Browser browser;
	private String functionName;

	public DataFeedFunction(Browser browser, String name) {
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
			data = DataFeed.getInstance().getSmellsByPackage();
			break;
		case 1:
			data = DataFeed.getInstance().getSmellsByPackage2();
			break;
		case 2:
			data = DataFeed.getInstance().getColors().toString();
			break;
		case 3:
			data = DataFeed.getInstance().getColors2().toString();
			break;
		case 4:
			data = DataFeed.getInstance().getRelationships();
			break;
		
		}
		return data;
	}

}