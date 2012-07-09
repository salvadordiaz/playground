package fr.salvadordiaz.gwt.schedule.client.speaker;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import fr.salvadordiaz.gwt.schedule.shared.Presentation;
import fr.salvadordiaz.gwt.schedule.shared.SpeakerDetails;

public class SpeakerView extends Composite implements SpeakerActivity.Display, HasSelectionHandlers<Presentation> {
	private static SpeakerViewUiBinder uiBinder = GWT.create(SpeakerViewUiBinder.class);

	interface SpeakerViewUiBinder extends UiBinder<Widget, SpeakerView> {
	}

	@UiField
	ImageElement image;
	@UiField
	Label bio;
	@UiField
	Label company;
	@UiField
	FlowPanel talks;

	public SpeakerView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setValue(SpeakerDetails speaker) {
		talks.clear();
		image.setSrc(speaker.getImageUri());
		bio.setText(speaker.getBio());
		company.setText(speaker.getFirstName() + " " + speaker.getLastName() + " works at " + speaker.getCompany());
		for (Presentation talk : speaker.getTalks()) {
			talks.add(getTalkWidget(talk));
		}
	}

	private Widget getTalkWidget(final Presentation talk) {
		Label result = new Label(talk.getTitle());
		result.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				SelectionEvent.fire(SpeakerView.this, talk);
			}
		});
		return result;
	}

	@Override
	public HandlerRegistration addSelectionHandler(SelectionHandler<Presentation> handler) {
		return addHandler(handler, SelectionEvent.getType());
	}
}
