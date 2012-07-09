package fr.salvadordiaz.gwt.schedule.client.xs.domain;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONObject;

import fr.salvadordiaz.gwt.schedule.shared.Presentation;

public class PresentationJsonWrapper implements Presentation {

	private static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss.S");

	private JSONObject presentationJson;

	public PresentationJsonWrapper(JSONObject presentationJson) {
		this.presentationJson = presentationJson;
	}

	@Override
	public Integer getId() {
		String jsValue = presentationJson.get("presentationUri").isString().stringValue();
		return jsValue != null && !jsValue.isEmpty() ? new Integer(jsValue.substring(jsValue.lastIndexOf('/') + 1)) : null;
	}

	@Override
	public Date getFromTime() {
		return DATE_FORMAT.parse(presentationJson.get("fromTime").isString().stringValue());
	}

	@Override
	public Date getToTime() {
		return DATE_FORMAT.parse(presentationJson.get("toTime").isString().stringValue());
	}

	@Override
	public String getTitle() {
		String title = ScheduleParser.getString("title", presentationJson);
		return title != null && !title.isEmpty() ? title : ScheduleParser.getString("code", presentationJson);
	}

	@Override
	public String getRoom() {
		return presentationJson.get("room").isString().stringValue();
	}

	@Override
	public String asString() {
		return getTitle();
	}

}
