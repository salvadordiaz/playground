package fr.salvadordiaz.gwt.client.app;

import com.google.gwt.core.client.GWT;

public class DesktopGinjectorProvider implements GinjectorProvider {

	private final DesktopApplicationGinjector ginjector = GWT.create(DesktopApplicationGinjector.class);

	@Override
	public ApplicationGinjector getGinjector() {
		return ginjector;
	}

}
