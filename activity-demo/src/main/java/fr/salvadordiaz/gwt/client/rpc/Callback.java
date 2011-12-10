package fr.salvadordiaz.gwt.client.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class Callback<T> implements AsyncCallback<T> {

	@Override
	public void onFailure(Throwable caught) {
		UncaughtExceptionHandler uncaughtExceptionHandler = GWT.getUncaughtExceptionHandler();
		if (uncaughtExceptionHandler != null) {
			uncaughtExceptionHandler.onUncaughtException(caught);
		}
	}

	@Override
	public abstract void onSuccess(T result);

}
