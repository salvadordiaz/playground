package fr.salvadordiaz.gwt.schedule.client.xs.domain;

import java.util.ArrayList;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.json.client.JSONObject;

import fr.salvadordiaz.gwt.schedule.shared.PresentationDetails;
import fr.salvadordiaz.gwt.schedule.shared.Speaker;

/**
 * @deprecated Replaced by {@link JSONObject} for parseability and instantiability reasons (a {@link JavaScriptObject} cannot be
 *             parsed/serialized in GWT code)
 */
public final class PresentationDetailsJson extends JavaScriptObject {

	protected PresentationDetailsJson() {
	}

	public final PresentationDetails convert() {
		PresentationDetails result = new PresentationDetails();
		result.setExperience(getExperience());
		result.setSummary(getSummary());
		result.setTitle(getTitle());
		result.setTrack(getTrack());
		result.setType(getType());
		ArrayList<Speaker> speakers = new ArrayList<Speaker>();
		for (int i = 0; i < getSpeakers().length(); i++) {
			speakers.add(getSpeakers().get(i).convert());
		}
		result.setSpeakers(speakers);
		return result;
	}

	public native String getSummary()/*-{
		return this.summary;
	}-*/;

	public native String getId()/*-{
		return this.id;
	}-*/;

	public native String getSpeakerUri()/*-{
		return this.speakerUri;
	}-*/;

	public native String getTitle()/*-{
		return this.title;
	}-*/;

	public native String getSpeaker()/*-{
		return this.speaker;
	}-*/;

	public native String getTrack()/*-{
		return this.track;
	}-*/;

	public native String getExperience()/*-{
		return this.experience;
	}-*/;

	public native String getType()/*-{
		return this.type;
	}-*/;

	public native JsArray<SpeakerJson> getSpeakers()/*-{
		return this.speakers;
	}-*/;

}
