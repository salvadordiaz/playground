package fr.salvadordiaz.gwt.client.inject;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.IsWidget;

@GinModules({ ApplicationBaseModule.class, ApplicationSpecificModule.class })
public interface ApplicationGinjector extends Ginjector {
	PlaceHistoryHandler getHistoryHandler();

	IsWidget getDisplay();
}
