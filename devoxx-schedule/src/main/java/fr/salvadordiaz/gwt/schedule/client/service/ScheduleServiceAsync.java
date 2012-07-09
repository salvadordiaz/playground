package fr.salvadordiaz.gwt.schedule.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.salvadordiaz.gwt.schedule.shared.DaySchedule;
import fr.salvadordiaz.gwt.schedule.shared.PresentationDetails;
import fr.salvadordiaz.gwt.schedule.shared.SpeakerDetails;

public interface ScheduleServiceAsync {

	void getDaySchedule(Integer eventId, Integer dayNumber, AsyncCallback<DaySchedule> callback);

	void getPresentationDetails(Integer presentationId, AsyncCallback<PresentationDetails> callback);

	void getSpeakerDetails(Integer speakerId, AsyncCallback<SpeakerDetails> callback);
	
	void clearCache(AsyncCallback<Void> callback);

}
