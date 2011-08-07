package fr.salvadordiaz.gwt.client.search;

import static com.google.common.base.Strings.*;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class SearchPlace extends Place {

	private final String query;

	public SearchPlace() {
		this.query = null;
	}

	public SearchPlace(final String query) {
		this.query = query;
	}

	public String getQuery() {
		return query;
	}

	@Prefix("search")
	public static final class Tokenizer implements PlaceTokenizer<SearchPlace> {
		@Override
		public SearchPlace getPlace(String token) {
			return new SearchPlace(token);
		}

		@Override
		public String getToken(SearchPlace place) {
			return nullToEmpty(place.getQuery());
		}
	}

}
