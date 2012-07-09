package fr.salvadordiaz.gwt.schedule.client.presentation;

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
import fr.salvadordiaz.gwt.schedule.client.service.ScheduleServiceAsync;
import fr.salvadordiaz.gwt.schedule.client.service.SuccessCallback;
import fr.salvadordiaz.gwt.schedule.client.speaker.SpeakerPlace;
import fr.salvadordiaz.gwt.schedule.shared.PresentationDetails;
import fr.salvadordiaz.gwt.schedule.shared.Speaker;

public class PresentationActivity extends AbstractPlaceAwareActivity {

	public interface Display extends IsWidget {
		void setValue(PresentationDetails presentation);

		HandlerRegistration addSelectionHandler(SelectionHandler<Speaker> handler);
	}

	private final Display presentationView;
	private final PlaceController placeController;
	private final ScheduleServiceAsync service;

	private PresentationPlace place;

	@Inject
	public PresentationActivity(Display presentationView, PlaceController placeController, ScheduleServiceAsync service) {
		this.presentationView = presentationView;
		this.placeController = placeController;
		this.service = service;
		bind();
	}

	private void bind() {
		presentationView.addSelectionHandler(new SelectionHandler<Speaker>() {
			@Override
			public void onSelection(SelectionEvent<Speaker> event) {
				 placeController.goTo(new SpeakerPlace(event.getSelectedItem()));
			}
		});
	}

	@Override
	public void start(final AcceptsOneWidget panel, EventBus eventBus) {
		service.getPresentationDetails(place.getPresentationId(), new SuccessCallback<PresentationDetails>() {
			@Override
			public void onSuccess(PresentationDetails result) {
				presentationView.setValue(result);
				panel.setWidget(presentationView);
			}
		});
	}

	@Override
	public void setPlace(Place place) {
		this.place = (PresentationPlace) place;
	}
}
