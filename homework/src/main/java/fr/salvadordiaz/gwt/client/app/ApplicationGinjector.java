package fr.salvadordiaz.gwt.client.app;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.IsWidget;

import fr.salvadordiaz.gwt.client.app.homework.HomeworkModule;

@GinModules({ ApplicationModule.class, HomeworkModule.class })
public interface ApplicationGinjector extends Ginjector {
	PlaceHistoryHandler getHistoryHandler();

	IsWidget display();
}
