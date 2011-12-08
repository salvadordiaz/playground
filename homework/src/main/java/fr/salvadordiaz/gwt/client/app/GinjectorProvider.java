package fr.salvadordiaz.gwt.client.app;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.SimplePanel;

public interface GinjectorProvider {

	public interface ApplicationGinjector extends Ginjector {
		PlaceHistoryHandler getHistoryHandler();

		SimplePanel display();
	}

	@GinModules({ ApplicationModule.class, HomeworkDesktopModule.class })
	public interface DesktopApplicationGinjector extends ApplicationGinjector {
	}

	@GinModules({ ApplicationModule.class, HomeworkMobileModule.class })
	public interface MobileApplicationGinjector extends ApplicationGinjector {
	}

	ApplicationGinjector getGinjector();

}
