package fr.salvadordiaz.gwt.schedule.client.xs.domain;

import java.util.ArrayList;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.json.client.JSONObject;

import fr.salvadordiaz.gwt.schedule.shared.Presentation;
import fr.salvadordiaz.gwt.schedule.shared.SpeakerDetails;

/**
 * @deprecated Replaced by {@link JSONObject} for parseability and instantiability reasons (a {@link JavaScriptObject} cannot be
 *             parsed/serialized in GWT code)
 */
public final class SpeakerDetailsJson extends JavaScriptObject {

	protected SpeakerDetailsJson(){
	}

	public SpeakerDetails convert(){
		SpeakerDetails result = new SpeakerDetails();
		result.setBio(getBio());
		result.setCompany(getCompany());
		result.setFirstName(getFirstName());
		result.setLastName(getLastName());
		result.setImageUri(getImageURI());
		result.setLastName(getLastName());
		ArrayList<Presentation> presentations = new ArrayList<Presentation>();
		for (int i = 0; i < getTalks().length(); i++) {
			presentations.add(getTalks().get(i).convert());
		}
		result.setTalks(presentations);
		return result;
	}
	
	public native String getId()/*-{
		return this.id;
	}-*/;

	public native String getLastName()/*-{
		return this.lastName;
	}-*/;

	public native String getFirstName()/*-{
		return this.firstName;
	}-*/;

	public native String getBio()/*-{
		return this.bio;
	}-*/;

	public native String getCompany()/*-{
		return this.company;
	}-*/;

	public native String getImageURI()/*-{
		return this.imageURI;
	}-*/;

	public native JsArray<TalkJson> getTalks()/*-{
		return this.talks;
	}-*/;

}
