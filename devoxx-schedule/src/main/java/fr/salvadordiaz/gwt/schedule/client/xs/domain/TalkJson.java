package fr.salvadordiaz.gwt.schedule.client.xs.domain;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

import fr.salvadordiaz.gwt.schedule.shared.Presentation;
import fr.salvadordiaz.gwt.schedule.shared.PresentationImpl;

/**
 * @deprecated Replaced by {@link JSONObject} for parseability and instantiability reasons (a {@link JavaScriptObject} cannot be
 *             parsed/serialized in GWT code)
 */
public final class TalkJson extends JavaScriptObject {

	protected TalkJson() {
	}

	public Presentation convert() {
		PresentationImpl result = new PresentationImpl();
		result.setTitle(getTitle());
		result.setId(Integer.valueOf(getPresentationUri().substring(getPresentationUri().lastIndexOf('/') + 1)));
		return result;
	}

	public native String getTitle()/*-{
		return this.title;
	}-*/;

	public native String getEvent()/*-{
		return this.event;
	}-*/;

	public native String getPresentationUri()/*-{
		return this.presentationUri;
	}-*/;

}
