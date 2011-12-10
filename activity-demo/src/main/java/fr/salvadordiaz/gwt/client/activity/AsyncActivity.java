package fr.salvadordiaz.gwt.client.activity;

import javax.inject.Inject;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import fr.salvadordiaz.gwt.client.rpc.Callback;

public class AsyncActivity implements Activity {

	private final AsyncProvider<Activity> provider;
	private Activity activity;

	private boolean canceled;

	@Inject
	public AsyncActivity(AsyncProvider<Activity> provider) {
		this.provider = provider;
	}

	@Override
	public String mayStop() {
		if (activity == null) {
			return null;
		}
		return activity.mayStop();
	}

	@Override
	public void onCancel() {
		if (activity != null) {
			activity.onCancel();
		}
		canceled = true;
	}

	@Override
	public void onStop() {
		if (activity != null) {
			activity.onStop();
		}
	}

	@Override
	public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
		if (activity != null) {
			activity.start(panel, eventBus);
			return;
		}
		provider.get(new Callback<Activity>() {
			@Override
			public void onSuccess(Activity result) {
				activity = result;
				if (canceled) {
					return;
				}
				activity.start(panel, eventBus);
			}
		});
	}

}
