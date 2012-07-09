package fr.salvadordiaz.gwt.schedule.client.xs.domain;

import com.google.gwt.core.client.JavaScriptObject;

import fr.salvadordiaz.gwt.schedule.shared.Speaker;

public final class SpeakerJson extends JavaScriptObject {

	protected SpeakerJson() {
	}

	public Speaker convert() {
		Speaker result = new Speaker();
		result.setFullName(getFullName());
		result.setId(Integer.valueOf(getSpeakerUri().substring(getSpeakerUri().lastIndexOf('/') + 1)));
		return result;
	}

	public native String getSpeakerUri()/*-{
		return this.speakerUri;
	}-*/;

	public native String getFullName()/*-{
		return this.speaker;
	}-*/;

}
