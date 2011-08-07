package fr.salvadordiaz.gwt.client.app.homework;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

import fr.salvadordiaz.gwt.client.activity.ActivityMapping;
import fr.salvadordiaz.gwt.client.activity.PlaceAwareActivity;
import fr.salvadordiaz.gwt.client.repo.Repo;
import fr.salvadordiaz.gwt.client.search.Search;

public class HomeworkActivityMapper implements ActivityMapper {

	private final Map<Class<? extends Place>, PlaceAwareActivity> activities = new HashMap<Class<? extends Place>, PlaceAwareActivity>();

	@Inject
	public HomeworkActivityMapper(//
			@Search ActivityMapping firstActivity //
			, @Repo ActivityMapping secondActivity//
	) {
		activities.put(firstActivity.getPlaceType(), firstActivity.getActivity());
		activities.put(secondActivity.getPlaceType(), secondActivity.getActivity());
	}

	@Override
	public Activity getActivity(Place place) {
		final PlaceAwareActivity activity = activities.get(place.getClass());
		activity.setPlace(place);
		return activity;
	}

}
