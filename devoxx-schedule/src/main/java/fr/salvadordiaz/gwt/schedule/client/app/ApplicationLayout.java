package fr.salvadordiaz.gwt.schedule.client.app;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.place.shared.PlaceChangeRequestEvent;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import fr.salvadordiaz.gwt.schedule.client.day.DayPlace;
import fr.salvadordiaz.gwt.schedule.client.service.ScheduleServiceAsync;
import fr.salvadordiaz.gwt.schedule.client.service.SuccessCallback;

public class ApplicationLayout extends Composite implements AcceptsOneWidget, PlaceChangeRequestEvent.Handler {

	private static ApplicationLayoutUiBinder uiBinder = GWT.create(ApplicationLayoutUiBinder.class);

	interface ApplicationLayoutUiBinder extends UiBinder<Widget, ApplicationLayout> {
	}

	@UiField
	SimplePanel container;
	@UiField
	Label header;
	@UiField
	Image backButton;
	@UiField
	Image moreButton;

	
	private final PlaceController placeController;
	private final ScheduleServiceAsync service;

	@Inject
	public ApplicationLayout(PlaceController placeController, ScheduleServiceAsync service) {
		initWidget(uiBinder.createAndBindUi(this));
		this.placeController = placeController;
		this.service = service;
	}

	@UiHandler({ "backButton", "moreButton" })
	void goToHome(ClickEvent event) {
		if (event.getSource().equals(backButton)) {
			History.back();
		} else if (event.getSource().equals(moreButton)) {
			service.clearCache(new SuccessCallback<Void>() {
				@Override
				public void onSuccess(Void result) {
					placeController.goTo(new DayPlace(1, 1));
				}
			});
		}
	}

	@Override
	public void setWidget(IsWidget w) {
		container.setWidget(w);
	}

	@Override
	public void onPlaceChangeRequest(PlaceChangeRequestEvent event) {
		header.setText(event.getNewPlace().toString());
	}

}
