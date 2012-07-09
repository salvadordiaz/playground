package fr.salvadordiaz.gwt.schedule.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

import fr.salvadordiaz.gwt.schedule.client.app.ApplicationGinjector;

public class Schedule implements EntryPoint {

	@Override
	public void onModuleLoad() {
		ApplicationGinjector injector = GWT.create(ApplicationGinjector.class);
		RootPanel.get().add(injector.getMainDisplay());
		injector.getHistoryHandler().handleCurrentHistory();
	}
}
