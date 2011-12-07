package fr.salvadordiaz.gwt.client.app;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.SimplePanel;


@GinModules({ ApplicationModule.class, HomeworkModule.class })
public interface ApplicationGinjector extends Ginjector {
	PlaceHistoryHandler getHistoryHandler();

	SimplePanel display();
}
