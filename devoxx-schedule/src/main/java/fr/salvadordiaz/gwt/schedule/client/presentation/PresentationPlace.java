package fr.salvadordiaz.gwt.schedule.client.presentation;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

import fr.salvadordiaz.gwt.schedule.client.app.SchedulePlaceTokenizer;
import fr.salvadordiaz.gwt.schedule.shared.Presentation;

public class PresentationPlace extends Place {

	private Integer presentationId;
	private Presentation presentation;

	public PresentationPlace(Presentation presentation) {
		this.presentationId = presentation.getId();
		this.presentation = presentation;
	}

	public PresentationPlace(Integer presentationId) {
		this.presentationId = presentationId;
	}

	public Integer getPresentationId() {
		return presentationId;
	}

	@Override
	public String toString() {
		return presentation != null ? presentation.asString() : "Presentation details";
	}

	public static class Tokenizer implements PlaceTokenizer<PresentationPlace> {

		private SchedulePlaceTokenizer delegate = new SchedulePlaceTokenizer();

		@Override
		public PresentationPlace getPlace(String token) {
			return delegate.parsePresentationPlace(token);
		}

		@Override
		public String getToken(PresentationPlace place) {
			return delegate.formatPresentationPlace(place);
		}

	}

}
