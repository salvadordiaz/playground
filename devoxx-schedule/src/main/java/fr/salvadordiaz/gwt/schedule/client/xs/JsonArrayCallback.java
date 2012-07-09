package fr.salvadordiaz.gwt.schedule.client.xs;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This typed callback works around some limitations of GWT's JSNI (ie: inability to call generic-typed methods on generic-typed java
 * classes from javascript)
 */
public abstract class JsonArrayCallback implements AsyncCallback<JavaScriptObject> {

	private final String url;
	private final AsyncCallback<?> callback;

	public JsonArrayCallback(String url, AsyncCallback<?> callback) {
		this.callback = callback;
		this.url = url;
	}

	@Override
	public void onFailure(Throwable caught) {
		GWT.log("Failure while retrieving json data from " + url + ". Abandoning.");
		callback.onFailure(caught);
	}

	@Override
	public void onSuccess(JavaScriptObject result){
		GWT.log("Successfully retrieved json data from " + url + ". Returning data to caller.");
		onSuccess(new JSONArray(result));
	}

	public abstract void onSuccess(JSONArray result);

}
