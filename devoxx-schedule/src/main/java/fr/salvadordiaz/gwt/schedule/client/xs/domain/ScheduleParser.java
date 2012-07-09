package fr.salvadordiaz.gwt.schedule.client.xs.domain;

import java.util.ArrayList;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

import fr.salvadordiaz.gwt.schedule.shared.DaySchedule;
import fr.salvadordiaz.gwt.schedule.shared.Presentation;
import fr.salvadordiaz.gwt.schedule.shared.PresentationDetails;
import fr.salvadordiaz.gwt.schedule.shared.PresentationImpl;
import fr.salvadordiaz.gwt.schedule.shared.Speaker;
import fr.salvadordiaz.gwt.schedule.shared.SpeakerDetails;

/**
 * Using {@link JSONObject} instead of {@link JavaScriptObject} for accessing the results from a cross-site request is more verbose but
 * {@link JSONObject} instances can be created from Strings, as opposed to {@link JavaScriptObject} instances. We need this if we want to be
 * able to use localStorage for offline mode.
 */
public class ScheduleParser {

	public static DaySchedule parseDaySchedule(JSONArray jsonData) {
		DaySchedule result = new DaySchedule();
		result.setPresentations(new ArrayList<Presentation>());
		for (int i = 0; i < jsonData.size(); i++) {
			result.getPresentations().add(new PresentationJsonWrapper(jsonData.get(i).isObject()));
		}
		result.setDay(result.getPresentations().get(0).getFromTime());
		return result;
	}

	public static PresentationDetails parsePresentationDetails(JSONValue value) {
		JSONObject json = value.isObject();
		PresentationDetails result = new PresentationDetails();
		result.setExperience(getString("experience", json));
		result.setSummary(getString("summary", json));
		result.setTitle(getString("title", json));
		result.setTrack(getString("track", json));
		result.setType(getString("type", json));
		result.setSpeakers(new ArrayList<Speaker>());
		JSONArray jsonSpeakers = json.get("speakers").isArray();
		for (int i = 0; i < jsonSpeakers.size(); i++) {
			JSONObject speaker = jsonSpeakers.get(i).isObject();
			String id = getString("speakerUri", speaker);
			String fullName = getString("speaker", speaker);
			result.getSpeakers().add(new Speaker(new Integer(id.substring(id.lastIndexOf('/') + 1)), fullName));
		}
		return result;
	}

	public static SpeakerDetails parseSpeakerDetails(JSONValue value) {
		JSONObject json = value.isObject();
		SpeakerDetails result = new SpeakerDetails();
		result.setBio(getString("bio", json));
		result.setCompany(getString("company", json));
		result.setFirstName(getString("firstName", json));
		result.setImageUri(getString("imageUri", json));
		result.setLastName(getString("lastName", json));
		result.setTalks(new ArrayList<Presentation>());
		JSONArray talks = json.get("talks").isArray();
		for (int i = 0; i < talks.size(); i++) {
			JSONObject jsonTalk = talks.get(i).isObject();
			PresentationImpl talk = new PresentationImpl();
			talk.setTitle(getString("title", jsonTalk));
			String id = getString("presentationUri", jsonTalk);
			talk.setId(new Integer(id.substring(id.lastIndexOf('/') + 1)));
			result.getTalks().add(talk);

		}
		return result;
	}

	public static String getString(String key, JSONObject json) {
		return json.containsKey(key) ? json.get(key).isString().stringValue() : null;
	}
}
