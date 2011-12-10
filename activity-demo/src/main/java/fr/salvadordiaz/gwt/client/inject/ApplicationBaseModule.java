package fr.salvadordiaz.gwt.client.inject;

import javax.inject.Singleton;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Provides;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

/**
 * gin module that wires the objects used in the Activity framework that are not application-specific. In order to make the {@link ApplicationGinjector} work,
 * one must provide another module with the application-specific bindings (see {@link ApplicationSpecificModule} for an example):
 * <ul>
 * <li>The implementation of IsWidget needed by the {@link ApplicationGinjector}</li>
 * <li>The implementation of {@link AcceptsOneWidget} needed by the {@link ActivityManager}</li>
 * <li>The implementation of {@link ActivityMapper} needed by the {@link ActivityManager}</li>
 * <li>The default place needed by the {@link PlaceHistoryHandler}</li>
 * <li>The implementation of {@link PlaceHistoryMapper} needed by {@link PlaceHistoryHandler}</li>
 * </ul>
 */
public class ApplicationBaseModule extends AbstractGinModule {

	@Override
	protected void configure() {
		// bind the application EventBus
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
	}

	/**
	 * @param eventBus
	 *            binding provided in this module (see {@link #configure()})
	 * @return the {@link PlaceController} through which all place changes should be requested
	 */
	@Provides
	@Singleton
	PlaceController providePlaceController(EventBus eventBus) {
		return new PlaceController(eventBus);
	}

	/**
	 * Wires up the {@link PlaceHistoryHandler} and {@link ActivityManager}. The application can then be started with a call to
	 * {@link PlaceHistoryHandler#handleCurrentHistory()}
	 * 
	 * @param eventBus
	 *            binding provided in this module (see {@link #configure()})
	 * @param placeController
	 *            binding provided in this module (see {@link #providePlaceController(EventBus)})
	 * @param defaultPlace
	 *            binding provided by the app-specific module
	 * @param placeHistoryMapper
	 *            binding provided by the app-specific module
	 * @param activityMapper
	 *            binding provided by the app-specific module
	 * @param display
	 *            binding provided by the app-specific module
	 * @return the {@link PlaceHistoryHandler} ready to start the application through a call to its handleCurrentHistory() method
	 */
	@Provides
	@Singleton
	PlaceHistoryHandler provideHistoryHandler(EventBus eventBus,//
			PlaceController placeController,//
			Place defaultPlace,//
			PlaceHistoryMapper placeHistoryMapper,//
			ActivityMapper activityMapper,//
			AcceptsOneWidget display) {
		ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
		activityManager.setDisplay(display);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(placeHistoryMapper);
		historyHandler.register(placeController, eventBus, defaultPlace);
		return historyHandler;
	}
}
