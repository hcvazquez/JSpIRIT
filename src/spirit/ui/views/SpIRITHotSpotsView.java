package spirit.ui.views;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import spirit.ui.views.data.DataFeedFunction;

public class SpIRITHotSpotsView extends ViewPart {	

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "spirit.views.SpIRITHotSpotsView";

	public SpIRITHotSpotsView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		URL resource = this.getClass().getClassLoader().getResource("/spirit/view/index.html");		
		try {
			resource = FileLocator.resolve(resource);
			Browser browser = new Browser(parent, SWT.NONE);
			new DataFeedFunction(browser, "getDataFeed");
			browser.setUrl(resource.toURI().toString());
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void setFocus() {
	}

}
