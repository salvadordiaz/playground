package fr.salvadordiaz.gwt.client.inject;

import javax.inject.Singleton;

import com.google.common.collect.ImmutableMap;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.inject.Provides;

import fr.salvadordiaz.gwt.client.activity.DemoFirstPlace;
import fr.salvadordiaz.gwt.client.activity.DemoSecondPlace;
import fr.salvadordiaz.gwt.client.activity.LoggingActivity;

public class ApplicationSpecificModule extends AbstractGinModule {

	@WithTokenizers({ DemoFirstPlace.Tokenizer.class, DemoSecondPlace.Tokenizer.class })
	interface DemoHistoryMapper extends PlaceHistoryMapper {
	}

	@Override
	protected void configure() {
		// bind the default Place
		bind(Place.class).to(DemoFirstPlace.class);
		// bind the PlaceHistoryMapper (gin implicit bindings will call GWT.create(DemoHistoryMapper.class) to create it)
		bind(PlaceHistoryMapper.class).to(DemoHistoryMapper.class);
		//this will be the implementation for IsWidget and AcceptsOneWidget in this simple example
		//but generally the implementation of AcceptsOneWidget will be contained in the implementation of IsWidget
		bind(SimplePanel.class).in(Singleton.class);
	}

	@Provides
	@Singleton
	IsWidget displayForInjector(SimplePanel display) {
		return display;
	}

	@Provides
	@Singleton
	AcceptsOneWidget displayForActivityManager(SimplePanel display) {
		return display;
	}

	@Provides
	@Singleton
	ActivityMapper provideActivityMapper() {
		return new ActivityMapper() {
			final ImmutableMap<Class<? extends Place>, Activity> mappings = ImmutableMap.<Class<? extends Place>, Activity> of(//
					DemoFirstPlace.class, new LoggingActivity("First"),//
					DemoSecondPlace.class, new LoggingActivity("Second"));

			@Override
			public Activity getActivity(Place place) {
				return mappings.get(place.getClass());
			}
		};
	}

}
