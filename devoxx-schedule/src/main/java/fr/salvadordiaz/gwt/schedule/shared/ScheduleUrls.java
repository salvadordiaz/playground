package fr.salvadordiaz.gwt.schedule.shared;

public class ScheduleUrls {

	private static final String RESOURCE_BASE_URL = "https://cfp.devoxx.com/rest/v1/events/";

	public static String forDaySchedule(Integer eventId, Integer dayIndex) {
		return RESOURCE_BASE_URL + eventId + "/schedule/day/" + dayIndex;
	}

	public static String forPresentation(Integer presentationId) {
		return RESOURCE_BASE_URL + "presentations/" + presentationId;
	}

	public static String forSpeaker(Integer speakerId) {
		return RESOURCE_BASE_URL + "speakers/" + speakerId;
	}
}
