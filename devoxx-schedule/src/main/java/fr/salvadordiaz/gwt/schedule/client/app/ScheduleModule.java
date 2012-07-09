package fr.salvadordiaz.gwt.schedule.client.app;

import javax.inject.Singleton;

import com.google.common.collect.ImmutableMap;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Provides;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

import fr.salvadordiaz.gwt.schedule.client.day.DayActivity;
import fr.salvadordiaz.gwt.schedule.client.day.DayPlace;
import fr.salvadordiaz.gwt.schedule.client.day.DayView;
import fr.salvadordiaz.gwt.schedule.client.presentation.PresentationActivity;
import fr.salvadordiaz.gwt.schedule.client.presentation.PresentationPlace;
import fr.salvadordiaz.gwt.schedule.client.presentation.PresentationView;
import fr.salvadordiaz.gwt.schedule.client.service.ScheduleServiceAsync;
import fr.salvadordiaz.gwt.schedule.client.speaker.SpeakerActivity;
import fr.salvadordiaz.gwt.schedule.client.speaker.SpeakerPlace;
import fr.salvadordiaz.gwt.schedule.client.speaker.SpeakerView;
import fr.salvadordiaz.gwt.schedule.client.xs.CachedScheduleServiceImpl;

public class ScheduleModule extends AbstractGinModule {

	@WithTokenizers({ DayPlace.Tokenizer.class, PresentationPlace.Tokenizer.class, SpeakerPlace.Tokenizer.class })
	interface ScheduleHistoryMapper extends PlaceHistoryMapper {
	}

	@Override
	protected void configure() {
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
		bind(PlaceHistoryMapper.class).to(ScheduleHistoryMapper.class).in(Singleton.class);
		bind(ScheduleServiceAsync.class).to(CachedScheduleServiceImpl.class).in(Singleton.class);
		bind(DayActivity.class).in(Singleton.class);
		bind(DayActivity.Display.class).to(DayView.class).in(Singleton.class);
		bind(PresentationActivity.class).in(Singleton.class);
		bind(PresentationActivity.Display.class).to(PresentationView.class).in(Singleton.class);
		bind(SpeakerActivity.class).in(Singleton.class);
		bind(SpeakerActivity.Display.class).to(SpeakerView.class).in(Singleton.class);
		bind(ApplicationLayout.class).in(Singleton.class);
	}

	@Provides
	@Singleton
	Place provideDefaultPlace() {
		return new DayPlace(6, 1);
	}

	@Provides
	@Singleton
	IsWidget displayForInjector(ApplicationLayout display) {
		return display;
	}

	@Provides
	@Singleton
	ActivityMapper provideActivityMapper(final DayActivity dayActivity, final PresentationActivity presentationActivity, final SpeakerActivity speakerActivity) {
		return new ActivityMapper() {
			ImmutableMap<Class<? extends Place>, AbstractPlaceAwareActivity> mappings = ImmutableMap.<Class<? extends Place>, AbstractPlaceAwareActivity> of(//
					DayPlace.class, dayActivity,//
					PresentationPlace.class, presentationActivity,//
					SpeakerPlace.class, speakerActivity);

			@Override
			public Activity getActivity(Place place) {
				final AbstractPlaceAwareActivity activity = mappings.get(place.getClass());
				if (activity != null) {
					activity.setPlace(place);
				}
				return activity;
			}
		};
	}

	@Provides
	@Singleton
	PlaceController providePlaceController(EventBus eventBus) {
		return new PlaceController(eventBus);
	}

	@Provides
	@Singleton
	PlaceHistoryHandler provideHistoryHandler(EventBus eventBus, PlaceHistoryMapper historyMapper, ActivityMapper activityMapper,
			PlaceController placeController, Place defaultPlace, ApplicationLayout display) {
		ActivityManager manager = new ActivityManager(activityMapper, eventBus);
		manager.setDisplay(display);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(placeController, eventBus, defaultPlace);
		return historyHandler;
	}
}
