package fr.salvadordiaz.gwt.schedule.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import fr.salvadordiaz.gwt.schedule.shared.DaySchedule;
import fr.salvadordiaz.gwt.schedule.shared.PresentationDetails;
import fr.salvadordiaz.gwt.schedule.shared.SpeakerDetails;

@RemoteServiceRelativePath("schedule")
public interface ScheduleService extends RemoteService {
	DaySchedule getDaySchedule(Integer eventId, Integer dayNumber);

	PresentationDetails getPresentationDetails(Integer presentationId);

	SpeakerDetails getSpeakerDetails(Integer speakerDetails);

	void clearCache();
}
