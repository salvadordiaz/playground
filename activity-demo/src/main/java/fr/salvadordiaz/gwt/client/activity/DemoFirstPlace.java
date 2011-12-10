package fr.salvadordiaz.gwt.client.activity;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class DemoFirstPlace extends Place {

	public static final class Tokenizer implements PlaceTokenizer<DemoFirstPlace> {
		@Override
		public DemoFirstPlace getPlace(String token) {
			return new DemoFirstPlace();
		}

		@Override
		public String getToken(DemoFirstPlace place) {
			return "";
		}

	}
}
