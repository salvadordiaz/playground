package fr.salvadordiaz.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

import fr.salvadordiaz.gwt.client.inject.ApplicationGinjector;

public class ActivityDemo implements EntryPoint {
	public void onModuleLoad() {
		ApplicationGinjector injector = GWT.create(ApplicationGinjector.class);
		RootPanel.get().add(injector.getDisplay());
		injector.getHistoryHandler().handleCurrentHistory();
	}
}
