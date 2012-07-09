package fr.salvadordiaz.gwt.schedule.client.speaker;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;

import fr.salvadordiaz.gwt.schedule.client.app.AbstractPlaceAwareActivity;
import fr.salvadordiaz.gwt.schedule.client.presentation.PresentationPlace;
import fr.salvadordiaz.gwt.schedule.client.service.ScheduleServiceAsync;
import fr.salvadordiaz.gwt.schedule.client.service.SuccessCallback;
import fr.salvadordiaz.gwt.schedule.shared.Presentation;
import fr.salvadordiaz.gwt.schedule.shared.SpeakerDetails;

public class SpeakerActivity extends AbstractPlaceAwareActivity {

	public interface Display extends IsWidget {
		void setValue(SpeakerDetails speaker);

		HandlerRegistration addSelectionHandler(SelectionHandler<Presentation> handler);
	}

	private final Display speakerView;
	private final PlaceController placeController;
	private final ScheduleServiceAsync service;

	private SpeakerPlace place;

	@Inject
	public SpeakerActivity(Display speakerView, PlaceController placeController, ScheduleServiceAsync service) {
		this.speakerView = speakerView;
		this.placeController = placeController;
		this.service = service;
		bind();
	}

	private void bind(){
		speakerView.addSelectionHandler(new SelectionHandler<Presentation>() {
			@Override
			public void onSelection(SelectionEvent<Presentation> event) {
				placeController.goTo(new PresentationPlace(event.getSelectedItem()));
			}
		});
	}
	
	@Override
	public void start(final AcceptsOneWidget panel, EventBus eventBus) {
		service.getSpeakerDetails(place.getSpeakerId(), new SuccessCallback<SpeakerDetails>() {
			@Override
			public void onSuccess(SpeakerDetails result) {
				speakerView.setValue(result);
				panel.setWidget(speakerView);
			}
		});
	}

	@Override
	public void setPlace(Place place) {
		this.place = (SpeakerPlace) place;
	}
}
