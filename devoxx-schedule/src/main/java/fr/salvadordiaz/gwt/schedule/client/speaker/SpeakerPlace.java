package fr.salvadordiaz.gwt.schedule.client.speaker;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

import fr.salvadordiaz.gwt.schedule.client.app.SchedulePlaceTokenizer;
import fr.salvadordiaz.gwt.schedule.shared.Speaker;

public class SpeakerPlace extends Place {

	private Speaker speaker;
	private Integer speakerId;

	public SpeakerPlace(Integer speakerId) {
		this.speakerId = speakerId;
	}

	public SpeakerPlace(Speaker speaker) {
		this.speakerId = speaker.getId();
		this.speaker = speaker;
	}

	public Integer getSpeakerId() {
		return speakerId;
	}

	@Override
	public String toString() {
		return speaker != null ? speaker.toString() : "Speaker details";
	}

	public static class Tokenizer implements PlaceTokenizer<SpeakerPlace> {

		private SchedulePlaceTokenizer delegate = new SchedulePlaceTokenizer();

		@Override
		public SpeakerPlace getPlace(String token) {
			return delegate.parseSpeakerPlace(token);
		}

		@Override
		public String getToken(SpeakerPlace place) {
			return delegate.formatSpeakerPlace(place);
		}

	}
}
