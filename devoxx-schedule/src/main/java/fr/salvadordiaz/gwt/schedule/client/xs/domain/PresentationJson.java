package fr.salvadordiaz.gwt.schedule.client.xs.domain;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

/**
 * @deprecated Replaced by {@link JSONObject} for parseability and instantiability reasons (a {@link JavaScriptObject} cannot be
 *             parsed/serialized in GWT code)
 */
public final class PresentationJson extends JavaScriptObject {

	protected PresentationJson() {
	}

	public native String getId() /*-{
		return this.id;
	}-*/;

	public native String getPresentationUri()/*-{
		return this.presentationUri;
	}-*/;

	public native String getFromTime() /*-{
		return this.fromTime;
	}-*/;

	public native String getToTime()/*-{
		return this.toTime;
	}-*/;

	public native String getTitle() /*-{
		return this.title;
	}-*/;

	public native String getRoom() /*-{
		return this.room;
	}-*/;
}
