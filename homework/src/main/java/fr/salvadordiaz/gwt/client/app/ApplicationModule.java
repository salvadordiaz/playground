package fr.salvadordiaz.gwt.client.app;

import javax.inject.Singleton;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.Provides;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class ApplicationModule extends AbstractGinModule {

	@Override
	protected void configure() {
		//think about naming this binding in order to allow other modules to receive other instances or this one specifically
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
		bind(AcceptsOneWidget.class).to(SimplePanel.class).in(Singleton.class);//name it
	}

	@Provides
	@Singleton
	IsWidget display(AcceptsOneWidget display) {
		return (IsWidget) display;
	}

	@Provides
	@Singleton
	PlaceController providePlaceController(//
			EventBus eventBus,//
			ActivityMapper activityMapper,//
			AcceptsOneWidget display) {
		final ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
		activityManager.setDisplay(display);

		final PlaceController placeController = new PlaceController(eventBus);
		return placeController;
	}

	@Provides
	@Singleton
	PlaceHistoryHandler provideHistoryHandler(//
			EventBus eventBus,//
			PlaceHistoryMapper mapper,//
			Place place,//
			ActivityMapper activityMapper,//
			PlaceController placeController) {
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(mapper);
		historyHandler.register(placeController, eventBus, place);
		return historyHandler;
	}
}
