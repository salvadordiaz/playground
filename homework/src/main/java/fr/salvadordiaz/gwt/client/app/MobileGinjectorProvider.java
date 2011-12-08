package fr.salvadordiaz.gwt.client.app;

import com.google.gwt.core.client.GWT;

public class MobileGinjectorProvider implements GinjectorProvider {

	private final MobileApplicationGinjector ginjector = GWT.create(MobileApplicationGinjector.class);

	@Override
	public ApplicationGinjector getGinjector() {
		return ginjector;
	}

}
