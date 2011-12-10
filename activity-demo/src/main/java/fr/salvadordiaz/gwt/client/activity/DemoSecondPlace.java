package fr.salvadordiaz.gwt.client.activity;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class DemoSecondPlace extends Place {
	
	public static final class Tokenizer implements PlaceTokenizer<DemoSecondPlace> {
		@Override
		public DemoSecondPlace getPlace(String token) {
			return new DemoSecondPlace();
		}

		@Override
		public String getToken(DemoSecondPlace place) {
			return "";
		}

	}
}
