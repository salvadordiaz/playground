package fr.salvadordiaz.gwt.client.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import fr.salvadordiaz.gwt.client.rpc.Callback;

public class AsyncActivity<A extends PlaceAwareActivity> implements PlaceAwareActivity {

	private final AsyncProvider<A> activityProvider;
	private PlaceAwareActivity activity;
	private Place place;

	public AsyncActivity(AsyncProvider<A> asyncProvider) {
		this.activityProvider = asyncProvider;
	}

	@Override
	public String mayStop() {
		if (activity == null) {
			//activity may stop if it hasn't even been retrieved
			return null;
		}
		return activity.mayStop();
	}

	@Override
	public void onCancel() {
		if (activity != null) {
			activity.onCancel();
		}
	}

	@Override
	public void onStop() {
		if (activity != null) {
			activity.onStop();
		}
	}

	@Override
	public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
		if (activity == null) {
			activityProvider.get(new Callback<A>() {
				@Override
				public void onSuccess(A result) {
					activity = result;
					//TODO: stop or cancel if any of those happened in the meantime
					activity.setPlace(place);
					activity.start(panel, eventBus);
				}
			});
		} else {
			activity.start(panel, eventBus);
		}
	}

	@Override
	public void setPlace(Place place) {
		if (activity != null) {
			activity.setPlace(place);
		} else {
			this.place = place;
		}
	}

}
