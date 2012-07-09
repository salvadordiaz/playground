package fr.salvadordiaz.gwt.schedule.client.xs;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.salvadordiaz.gwt.schedule.client.service.ScheduleServiceAsync;
import fr.salvadordiaz.gwt.schedule.client.service.SuccessCallback;
import fr.salvadordiaz.gwt.schedule.client.xs.domain.ScheduleParser;
import fr.salvadordiaz.gwt.schedule.shared.DaySchedule;
import fr.salvadordiaz.gwt.schedule.shared.PresentationDetails;
import fr.salvadordiaz.gwt.schedule.shared.ScheduleUrls;
import fr.salvadordiaz.gwt.schedule.shared.SpeakerDetails;

public class CachedScheduleServiceImpl implements ScheduleServiceAsync {

	private ScheduleRequestBuilder requestBuilder = new ScheduleRequestBuilder();

	@Override
	public void getDaySchedule(final Integer eventId, Integer dayNumber, final AsyncCallback<DaySchedule> callback) {
		final String url = ScheduleUrls.forDaySchedule(eventId, dayNumber);
		requestBuilder.requestObject(url, new SuccessCallback<JavaScriptObject>() {
			@Override
			public void onSuccess(JavaScriptObject result) {
				DaySchedule daySchedule = ScheduleParser.parseDaySchedule(new JSONArray(result));
				callback.onSuccess(daySchedule);
			}
		});
	}

	@Override
	public void getPresentationDetails(Integer presentationId, final AsyncCallback<PresentationDetails> callback) {
		final String url = ScheduleUrls.forPresentation(presentationId);
		requestBuilder.requestObject(url,  new SuccessCallback<JavaScriptObject>() {
			@Override
			public void onSuccess(JavaScriptObject result) {
				JSONObject jsonData = new JSONArray(result).get(0).isObject();
				callback.onSuccess(ScheduleParser.parsePresentationDetails(jsonData));
			}
		});
	}

	@Override
	public void getSpeakerDetails(Integer speakerId, final AsyncCallback<SpeakerDetails> callback) {
		final String url = ScheduleUrls.forSpeaker(speakerId);
		requestBuilder.requestObject(url, new SuccessCallback<JavaScriptObject>() {
			@Override
			public void onSuccess(JavaScriptObject result) {
				JSONObject jsonData = new JSONArray(result).get(0).isObject();
				callback.onSuccess(ScheduleParser.parseSpeakerDetails(jsonData));

			}
		});
	}

	public void clearCache(AsyncCallback<Void> callback) {
		Storage.getLocalStorageIfSupported().clear();
		callback.onSuccess(null);
	}
}
