package fr.salvadordiaz.gwt.schedule.shared;

import java.io.Serializable;
import java.util.ArrayList;

public class SpeakerDetails implements Serializable {
	private static final long serialVersionUID = -2786430235361394035L;

	private String firstName;
	private String lastName;
	private String bio;
	private String company;
	private String imageUri;
	private ArrayList<Presentation> talks;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getImageUri() {
		return imageUri;
	}

	public void setImageUri(String imageUri) {
		this.imageUri = imageUri;
	}

	public ArrayList<Presentation> getTalks() {
		return talks;
	}

	public void setTalks(ArrayList<Presentation> talks) {
		this.talks = talks;
	}

}
