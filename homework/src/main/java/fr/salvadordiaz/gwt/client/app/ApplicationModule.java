package fr.salvadordiaz.gwt.client.app;

import javax.inject.Singleton;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.Provides;

import fr.salvadordiaz.gwt.client.activity.ApplicationActivityMapper;
import fr.salvadordiaz.gwt.client.activity.ApplicationHistoryMapper;
import fr.salvadordiaz.gwt.client.activity.DefaultPlace;

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

//	@Provides
//	Place defaultPlace(@DefaultPlace Place place) {
//		return place;//dangerous
//	}

	@Provides
	@Singleton
	PlaceController providePlaceController(//
			EventBus eventBus,//
			@ApplicationActivityMapper ActivityMapper activityMapper,//
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
			@ApplicationHistoryMapper PlaceHistoryMapper mapper,//
			@DefaultPlace Place place,//
			@ApplicationActivityMapper ActivityMapper activityMapper,//
			PlaceController placeController) {
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(mapper);
		historyHandler.register(placeController, eventBus, place);
		return historyHandler;
	}
}
