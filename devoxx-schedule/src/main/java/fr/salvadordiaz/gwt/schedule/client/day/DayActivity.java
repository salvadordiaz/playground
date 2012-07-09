package fr.salvadordiaz.gwt.schedule.client.day;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
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
import fr.salvadordiaz.gwt.schedule.shared.DaySchedule;
import fr.salvadordiaz.gwt.schedule.shared.Presentation;

public class DayActivity extends AbstractPlaceAwareActivity {

	private static final int MAX_DAY_INDEX = 5;

	public interface Display extends IsWidget {
		void displayPresentations(DaySchedule presentations);

		HandlerRegistration addSelectionHandler(SelectionHandler<Presentation> handler);

		HasClickHandlers getPreviousButton();

		HasClickHandlers getNextButton();
		
		void onFailure();
	}

	private final Display dayView;
	private final PlaceController placeController;
	private final ScheduleServiceAsync service;

	private DayPlace place;
	
	@Inject
	public DayActivity(Display dayView, PlaceController placeController,ScheduleServiceAsync service) {
		this.placeController = placeController;
		this.dayView = dayView;
		this.service = service;
		bind();
	}

	private void bind() {
		dayView.addSelectionHandler(new SelectionHandler<Presentation>() {
			@Override
			public void onSelection(SelectionEvent<Presentation> event) {
				if (event.getSelectedItem().getId() != null) {
					placeController.goTo(new PresentationPlace(event.getSelectedItem()));
				}
			}
		});
		dayView.getPreviousButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Integer newDayIndex = place.getDayIndex() - 1 < 1 ? MAX_DAY_INDEX : place.getDayIndex() - 1;
				updatePlace(newDayIndex);
			}
		});
		dayView.getNextButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Integer newDayIndex = place.getDayIndex() + 1 > MAX_DAY_INDEX ? 1 : place.getDayIndex() + 1;
				updatePlace(newDayIndex);
			}
		});
	}

	@Override
	public void start(final AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(dayView);
		getPresentations();
	}

	@Override
	public void setPlace(Place place) {
		this.place = (DayPlace) place;
		getPresentations();
	}
	
	private void updatePlace(Integer dayIndex) {
		placeController.goTo(new DayPlace(place.getEventId(), dayIndex));
	}

	private void getPresentations() {
		service.getDaySchedule(place.getEventId(), place.getDayIndex(), new SuccessCallback<DaySchedule>() {
			@Override
			public void onSuccess(DaySchedule result) {
				dayView.displayPresentations(result);
			}
			@Override
			public void afterFailure(Throwable caught) {
				dayView.onFailure();
			}
		});
	}
}
