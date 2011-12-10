package fr.salvadordiaz.gwt.client.activity;

import java.util.logging.Logger;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class LoggingActivity implements Activity {

	private final Logger log;

	public LoggingActivity(String id) {
		log = Logger.getLogger("DemoActivity" + id);
	}

	@Override
	public String mayStop() {
		log.info("mayStop");
		return null;
	}

	@Override
	public void onCancel() {
		log.info("onCancel");
	}

	@Override
	public void onStop() {
		log.info("onStop");
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		log.info("start");
	}

}
