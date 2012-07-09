package fr.salvadordiaz.gwt.schedule.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class SuccessCallback<T> implements AsyncCallback<T> {

	@Override
	public final void onFailure(Throwable caught) {
		GWT.getUncaughtExceptionHandler().onUncaughtException(caught);
		afterFailure(caught);
	}

	/** Method to override if a specific error handling is needed */
	public void afterFailure(Throwable caught) {
	}

}
