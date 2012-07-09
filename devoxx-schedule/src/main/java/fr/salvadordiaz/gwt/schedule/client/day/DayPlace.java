package fr.salvadordiaz.gwt.schedule.client.day;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

import fr.salvadordiaz.gwt.schedule.client.app.SchedulePlaceTokenizer;

public class DayPlace extends Place {

	private Integer eventId;
	private Integer dayNumber;

	public DayPlace(Integer eventId, Integer dayIndex) {
		this.eventId = eventId;
		this.dayNumber = dayIndex;
	}

	public Integer getDayIndex() {
		return dayNumber;
	}

	public Integer getEventId() {
		return eventId;
	}

	@Override
	public String toString() {
		return "Day schedule";
	}
	
	public static class Tokenizer implements PlaceTokenizer<DayPlace> {
		private SchedulePlaceTokenizer delegate = new SchedulePlaceTokenizer();
		
		@Override
		public DayPlace getPlace(String token) {
			return delegate.parseDayPlace(token);
		}

		@Override
		public String getToken(DayPlace place) {
			return delegate.formatDayPlace(place);
		}
	}
}
