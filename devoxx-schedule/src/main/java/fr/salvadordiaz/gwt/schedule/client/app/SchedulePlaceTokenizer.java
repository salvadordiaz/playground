package fr.salvadordiaz.gwt.schedule.client.app;

import fr.salvadordiaz.gwt.schedule.client.day.DayPlace;
import fr.salvadordiaz.gwt.schedule.client.presentation.PresentationPlace;
import fr.salvadordiaz.gwt.schedule.client.speaker.SpeakerPlace;

public class SchedulePlaceTokenizer {

	protected static final String SEPARATOR = "&";
	protected static final String EVENT_PREFIX = "event=";
	protected static final String DAY_PREFIX = "day=";
	protected static final String PRESENTATION_PREFIX = "presentation=";
	protected static final String SPEAKER_PREFIX = "speaker=";

	public DayPlace parseDayPlace(String token) {
		String eventIdString = token.substring(EVENT_PREFIX.length(), token.indexOf(DAY_PREFIX) - SEPARATOR.length());
		String dayString = token.substring(token.indexOf(DAY_PREFIX) + DAY_PREFIX.length());
		return new DayPlace(Integer.valueOf(eventIdString), Integer.valueOf(dayString));
	}

	public String formatDayPlace(DayPlace place) {
		return EVENT_PREFIX + place.getEventId() + SEPARATOR + DAY_PREFIX + place.getDayIndex();
	}

	public PresentationPlace parsePresentationPlace(String token) {
		String idString = token.substring(PRESENTATION_PREFIX.length());
		return new PresentationPlace(Integer.valueOf(idString));
	}

	public String formatPresentationPlace(PresentationPlace place) {
		return PRESENTATION_PREFIX + place.getPresentationId();
	}

	public SpeakerPlace parseSpeakerPlace(String token) {
		String idSpeaker = token.substring(SPEAKER_PREFIX.length());
		return new SpeakerPlace(Integer.valueOf(idSpeaker));
	}

	public String formatSpeakerPlace(SpeakerPlace place) {
		return SPEAKER_PREFIX + place.getSpeakerId();
	}

}
