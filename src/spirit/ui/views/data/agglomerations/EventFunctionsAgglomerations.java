package spirit.ui.views.data.agglomerations;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;

public class EventFunctionsAgglomerations extends BrowserFunction {

	private Browser browser;
	private String functionName;

	public EventFunctionsAgglomerations(Browser browser, String name) {
		super(browser, name);
		this.browser = browser;
		this.functionName = name;
	}

	public Object function(Object[] arguments) {
		String packageName = arguments[0].toString();
		String className = arguments[1].toString();
		String kindOfSmell = arguments[2].toString();
		UIEventMethods.openClassInEditor(packageName, className, kindOfSmell);
		return 0;
	}
	
}