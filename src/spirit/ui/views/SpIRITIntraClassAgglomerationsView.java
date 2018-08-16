package spirit.ui.views;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import spirit.ui.views.data.agglomerations.DataFeedFunctionAgglomerations;
import spirit.ui.views.data.agglomerations.EventFunctionsAgglomerations;

public class SpIRITIntraClassAgglomerationsView extends ViewPart {
	
	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "spirit.views.SpIRITIntraClassAgglomerationsView";

	@Override
	public void createPartControl(Composite parent) {
		URL resource = this.getClass().getClassLoader().getResource("/spirit/intraclass/view/index.html");		
		try {
			resource = FileLocator.resolve(resource);
			Browser browser = new Browser(parent, SWT.NONE);
			new DataFeedFunctionAgglomerations(browser, "getIntraClassAgglomerationsDataFeed");
			new EventFunctionsAgglomerations(browser, "agglomerationsEventFunction");
			browser.setUrl(resource.toURI().toString());
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void setFocus() {		
	}

}
