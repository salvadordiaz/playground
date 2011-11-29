package fr.salvadordiaz.gwt.client.app.homework;

import java.util.Map;

import javax.inject.Inject;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

import fr.salvadordiaz.gwt.client.activity.PlaceAwareActivity;

public class HomeworkActivityMapper implements ActivityMapper {

	private final Map<Class<? extends Place>, PlaceAwareActivity> activities;

	@Inject
	public HomeworkActivityMapper(final Map<Class<? extends Place>, PlaceAwareActivity> activityMappings) {
		this.activities = activityMappings;
	}

	@Override
	public Activity getActivity(Place place) {
		final PlaceAwareActivity activity = activities.get(place.getClass());
		activity.setPlace(place);
		return activity;
	}

}
