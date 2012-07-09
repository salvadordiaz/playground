package fr.salvadordiaz.gwt.schedule.shared;

import java.io.Serializable;
import java.util.ArrayList;

public class PresentationDetails implements Serializable {
	private static final long serialVersionUID = 4845864680425054795L;

	private String title;
	private String summary;
	private String track;
	private String experience;
	private String type;
	private ArrayList<Speaker> speakers;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getTrack() {
		return track;
	}

	public void setTrack(String track) {
		this.track = track;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<Speaker> getSpeakers() {
		return speakers;
	}

	public void setSpeakers(ArrayList<Speaker> speakers) {
		this.speakers = speakers;
	}
}
