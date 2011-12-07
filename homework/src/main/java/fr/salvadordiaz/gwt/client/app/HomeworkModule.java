package fr.salvadordiaz.gwt.client.app;

import java.util.Map;

import javax.inject.Singleton;

import com.google.common.collect.ImmutableMap;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.google.inject.Provides;

import fr.salvadordiaz.gwt.client.activity.PlaceAwareActivity;
import fr.salvadordiaz.gwt.client.repo.RepositoryActivity;
import fr.salvadordiaz.gwt.client.repo.RepositoryDisplay;
import fr.salvadordiaz.gwt.client.repo.RepositoryPlace;
import fr.salvadordiaz.gwt.client.repo.ui.RepositoryView;
import fr.salvadordiaz.gwt.client.search.SearchActivity;
import fr.salvadordiaz.gwt.client.search.SearchDisplay;
import fr.salvadordiaz.gwt.client.search.SearchPlace;
import fr.salvadordiaz.gwt.client.search.ui.SearchView;

public class HomeworkModule extends AbstractGinModule {

	@WithTokenizers({ SearchPlace.Tokenizer.class, RepositoryPlace.Tokenizer.class })
	public interface HomeworkHistoryMapper extends PlaceHistoryMapper {
	}

	@Override
	protected void configure() {
		bind(Place.class).to(SearchPlace.class).in(Singleton.class);//default place, the only place that needs to be injected
		bind(PlaceHistoryMapper.class).to(HomeworkHistoryMapper.class).in(Singleton.class);

		bind(SearchActivity.class).in(Singleton.class);
		bind(SearchDisplay.class).to(SearchView.class).in(Singleton.class);

		bind(RepositoryActivity.class).in(Singleton.class);
		bind(RepositoryDisplay.class).to(RepositoryView.class).in(Singleton.class);
	}

	@Provides
	@Singleton
	Map<Class<? extends Place>, PlaceAwareActivity> provideActivityMappings(SearchActivity searchActivity, RepositoryActivity repositoryActivity) {
		return ImmutableMap.<Class<? extends Place>, PlaceAwareActivity> builder()//
				.put(SearchPlace.class, searchActivity)//
				.put(RepositoryPlace.class, repositoryActivity)//
				.build();
	}

	@Provides
	@Singleton
	ActivityMapper provideActivityMapper(final Map<Class<? extends Place>, PlaceAwareActivity> activityMappings) {
		return new ActivityMapper() {
			@Override
			public Activity getActivity(Place place) {
				final PlaceAwareActivity placeAwareActivity = activityMappings.get(place.getClass());
				if (placeAwareActivity != null) {
					placeAwareActivity.setPlace(place);
				}
				return placeAwareActivity;
			}
		};
	}

}
