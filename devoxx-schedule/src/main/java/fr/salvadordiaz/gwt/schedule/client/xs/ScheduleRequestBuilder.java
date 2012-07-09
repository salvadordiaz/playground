package fr.salvadordiaz.gwt.schedule.client.xs;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.jsonp.client.JsonpRequest;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This should probably be in 2 separate classes : one dealing with caching, and the other dealing with the "piping" of the data through Y!Pipes
 */
public class ScheduleRequestBuilder extends JsonpRequestBuilder {

	private Storage storage = Storage.getLocalStorageIfSupported();
	private JsonpRequestBuilder requestBuilder;

	@Override
	public <T extends JavaScriptObject> JsonpRequest<T> requestObject(String url, AsyncCallback<T> callback) {
		JavaScriptObject cache = get(url);
		if (cache != null) {
			@SuppressWarnings("unchecked")
			T result = (T) cache.cast();
			callback.onSuccess(result);
			return null;
		}
		return getRealRequest(url, getCachingCallback(url, callback));
	}

	private JavaScriptObject get(String key) {
		String item = storage.getItem(key);
		return item != null && !item.isEmpty() ? JSONParser.parseLenient(item).isArray().getJavaScriptObject() : null;
	}

	private void put(String key, JSONArray value) {
		storage.setItem(key, value.toString());
	}

	private <T extends JavaScriptObject> JsonpRequest<T> getRealRequest(String url, AsyncCallback<T> callback) {
		if (requestBuilder == null) {
			requestBuilder = new JsonpRequestBuilder();
			requestBuilder.setCallbackParam("_callback");
			requestBuilder.setTimeout(15000);
		}
		return requestBuilder.requestObject(url, callback);
	}

	private <T extends JavaScriptObject> AsyncCallback<T> getCachingCallback(final String url, final AsyncCallback<T> callback) {
		return new AsyncCallback<T>() {
			@Override
			public void onFailure(Throwable caught) {
				// Should probably wrap this exception
				callback.onFailure(caught);
			}

			@Override
			public void onSuccess(T result) {
				PipeObject pipeResult = result.cast();
				T data = pipeResult.getData().cast();
				callback.onSuccess(data);
				JSONArray value = new JSONArray(data);
				put(url, value);
			}
		};
	}

	static final class PipeObject extends JavaScriptObject {
		protected PipeObject() {
		}

		native JavaScriptObject getData()/*-{
			return this.value.items;
		}-*/;
	}
}
