package fr.salvadordiaz.gwt.schedule.client.presentation;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

import fr.salvadordiaz.gwt.schedule.shared.PresentationDetails;
import fr.salvadordiaz.gwt.schedule.shared.Speaker;

public class PresentationView extends Composite implements PresentationActivity.Display, HasSelectionHandlers<Speaker> {

	private static PresentationViewUiBinder uiBinder = GWT.create(PresentationViewUiBinder.class);

	interface PresentationViewUiBinder extends UiBinder<Widget, PresentationView> {
	}

	@UiField
	HTMLPanel container;
	@UiField
	Element type;
	@UiField
	LIElement title;
	@UiField
	InlineLabel speakersSpan;
	@UiField
	LIElement timeLocation;
	@UiField
	LIElement track;
	@UiField
	LIElement summary;

	private Map<Element, Speaker> speakersForSpans = new HashMap<Element, Speaker>();

	public PresentationView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("speakersSpan")
	void handleSpeakerSelection(ClickEvent event) {
		Element speakerSpan = Element.as(event.getNativeEvent().getEventTarget());
		Speaker speaker = speakersForSpans.get(speakerSpan);
		if (speaker != null) {
			SelectionEvent.fire(this, speaker);
		}
	}

	@Override
	public void setValue(PresentationDetails presentationDetails) {
		speakersForSpans.clear();
		type.setInnerText(presentationDetails.getType());
		title.setInnerText(presentationDetails.getTitle());
		for (Speaker speaker : presentationDetails.getSpeakers()) {
			DivElement speakerLink = DOM.createDiv().cast();
			speakerLink.setInnerText(speaker.getFullName());
			speakersSpan.getElement().appendChild(speakerLink);
			// ugly hack (is there any way to add handlers to Element instances ?)
			speakersForSpans.put(speakerLink, speaker);
		}
		// api doesn't give times in the presentation details (what's up with that ?)
		// stateless activities can't depend on previous activities for data so we remove the time/location placeholder
		timeLocation.removeFromParent();
		track.setInnerText(presentationDetails.getTrack() + " / " + presentationDetails.getExperience());
		summary.setInnerText(presentationDetails.getSummary());
	}

	@Override
	public HandlerRegistration addSelectionHandler(SelectionHandler<Speaker> handler) {
		return addHandler(handler, SelectionEvent.getType());
	}
	
	@Override
	protected void onDetach() {
		super.onDetach();
		for (Element speakerElement : speakersForSpans.keySet()) {
			speakerElement.removeFromParent();
		}
		speakersForSpans.clear();
	}
}
