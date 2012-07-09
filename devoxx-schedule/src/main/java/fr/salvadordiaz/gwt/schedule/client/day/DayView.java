package fr.salvadordiaz.gwt.schedule.client.day;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import fr.salvadordiaz.gwt.schedule.shared.DaySchedule;
import fr.salvadordiaz.gwt.schedule.shared.Presentation;

public class DayView extends Composite implements DayActivity.Display, HasSelectionHandlers<Presentation> {

	private static final int HOUR_HEIGHT = 110;
	private static final int HEIGHT_PADDING = 4;
	private static final DateTimeFormat TITLE_DATE_FORMAT = DateTimeFormat.getFormat(PredefinedFormat.DATE_MEDIUM);
	private static final double MINUTES_IN_HOUR = 60;

	private static AgendaDayViewUiBinder uiBinder = GWT.create(AgendaDayViewUiBinder.class);

	interface AgendaDayViewUiBinder extends UiBinder<Widget, DayView> {
	}

	interface Style extends CssResource {
		String hoursId();

		String sessionsId();

		String hour();

		String hourSeparator();

		String session();
	}

	@UiField
	Style style;
	@UiField
	DivElement spinner;
	@UiField
	Label titleLabel;
	@UiField
	HTML previousButton;
	@UiField
	HTML nextButton;
	@UiField
	HTMLPanel container;

	private Map<Date, List<Presentation>> presentationsByStartTime;
	private Map<String, String> colorsForRooms; 
	private int firstHourInDay;
	private int lastHourInDay;

	public DayView() {
		initWidget(uiBinder.createAndBindUi(this));
		colorsForRooms = new HashMap<String, String>();
	}

	@UiHandler({ "nextButton", "previousButton" })
	void handleNavigationClick(ClickEvent event) {
		spinner.getStyle().setDisplay(Display.BLOCK);
	}

	@Override
	public void displayPresentations(DaySchedule presentations) {
		container.getElementById(style.sessionsId()).setInnerHTML("");
		container.getElementById(style.hoursId()).setInnerHTML("");
		titleLabel.setText(TITLE_DATE_FORMAT.format(presentations.getDay()));
		groupByTime(presentations.getPresentations());
		ArrayList<Date> startTimes = new ArrayList<Date>(presentationsByStartTime.keySet());
		Collections.sort(startTimes);
		for (Date slotStartTime : startTimes) {
			List<Presentation> slotPresentations = presentationsByStartTime.get(slotStartTime);
			int countForSlot = slotPresentations.size();
			int presentationWidth = 90 / countForSlot;
			int slotWidth = 100/countForSlot;
			int slotPadding = 10/countForSlot;
			int topPosition = getPositionForTime(slotStartTime);
			for (int slotIndex = 0; slotIndex < countForSlot; slotIndex++) {
				Presentation presentation = slotPresentations.get(slotIndex);
				Widget presentationWidget = getPresentationWidget(presentation);
				presentationWidget.getElement().getStyle().setTop(topPosition + HEIGHT_PADDING/2, Unit.PX);
				presentationWidget.getElement().getStyle().setLeft(slotIndex * slotWidth + slotPadding/2, Unit.PCT);
				presentationWidget.setSize(presentationWidth + "%", getHeightForPeriod(slotStartTime, presentation.getToTime()) + "px");
				container.add(presentationWidget, style.sessionsId());
			}
		}
		Element sessionContainer = container.getElementById(style.sessionsId());
		for (int hourIndex = firstHourInDay; hourIndex < lastHourInDay; hourIndex++) {
			Label hour = new Label(hourIndex + "h");
			hour.getElement().getStyle().setHeight(HOUR_HEIGHT, Unit.PX);
			hour.setStyleName(style.hour());
			container.add(hour, style.hoursId());
			DivElement hourSeparator = DOM.createDiv().cast();
			hourSeparator.addClassName(style.hourSeparator());
			hourSeparator.getStyle().setTop((hourIndex - firstHourInDay) * HOUR_HEIGHT, Unit.PX);
			sessionContainer.appendChild(hourSeparator);
		}
		spinner.getStyle().setDisplay(Display.NONE);
	}

	private int getPositionForTime(Date slotStartTime) {
		@SuppressWarnings("deprecation")
		double slotHours = slotStartTime.getHours() + (slotStartTime.getMinutes() / MINUTES_IN_HOUR);
		return (int) ((slotHours - firstHourInDay) * HOUR_HEIGHT);
	}

	private int getHeightForPeriod(Date startTime, Date endTime) {
		@SuppressWarnings("deprecation")
		double start = startTime.getHours() + (startTime.getMinutes() / MINUTES_IN_HOUR);
		@SuppressWarnings("deprecation")
		double end = endTime.getHours() + (endTime.getMinutes() / MINUTES_IN_HOUR);
		return (int) ((end - start) * HOUR_HEIGHT) - HEIGHT_PADDING;
	}

	private void groupByTime(List<Presentation> presentations) {
		Date min = null;
		Date max = null;
		presentationsByStartTime = new HashMap<Date, List<Presentation>>();
		for (Presentation presentation : presentations) {
			Date prezoStart = presentation.getFromTime();
			Date prezoEnd = presentation.getToTime();
			min = min != null && prezoStart.compareTo(min) > 0 ?  min : prezoStart;
			max= max != null && prezoEnd.compareTo(max) < 0 ? max : prezoEnd;
			List<Presentation> group = presentationsByStartTime.get(prezoStart);
			if (group == null) {
				group = new ArrayList<Presentation>();
				presentationsByStartTime.put(prezoStart, group);
			}
			group.add(presentation);
		}
		@SuppressWarnings("deprecation")
		int minHour = min.getHours();
		@SuppressWarnings("deprecation")
		int maxHour = max.getHours();
		this.firstHourInDay = minHour;
		this.lastHourInDay = maxHour;
	}

	private String getColor(String room) {
		String color = colorsForRooms.get(room);
		if(color == null){
			int b= Random.nextInt(255);
			int g = Random.nextInt(128);
			int r = 4 * Random.nextInt(63);
			color = "rgb(" + r + "," + g + "," + b + ")";
			colorsForRooms.put(room, color);
		}
		return color;
	}

	private Widget getPresentationWidget(final Presentation presentation) {
		String roomColor = getColor(presentation.getRoom());
		DivElement span = DOM.createDiv().cast();
		span.setInnerText(presentation.getRoom());
		span.getStyle().setColor(roomColor);
		HTML result = new HTML(presentation.getTitle() + span.getString());
		result.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				SelectionEvent.fire(DayView.this, presentation);
			}
		});
		result.setStyleName(style.session());
		result.getElement().getStyle().setBorderColor(roomColor);
		return result;
	}

	@Override
	public HandlerRegistration addSelectionHandler(SelectionHandler<Presentation> handler) {
		return addHandler(handler, SelectionEvent.getType());
	}

	@Override
	public HasClickHandlers getPreviousButton() {
		return previousButton;
	}

	@Override
	public HasClickHandlers getNextButton() {
		return nextButton;
	}

	@Override
	public void onFailure() {
		spinner.getStyle().setDisplay(Display.NONE);
	}
}
