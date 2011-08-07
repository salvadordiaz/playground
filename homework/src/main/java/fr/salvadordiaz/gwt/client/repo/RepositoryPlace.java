package fr.salvadordiaz.gwt.client.repo;

import java.util.Map;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

import fr.salvadordiaz.gwt.client.activity.AbstractTokenizer;

public class RepositoryPlace extends Place {

	private final String owner;
	private final String name;

	public RepositoryPlace(final String owner, final String name) {
		this.owner = owner;
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public String getName() {
		return name;
	}

	@Prefix("repo")
	public static final class Tokenizer extends AbstractTokenizer implements PlaceTokenizer<RepositoryPlace> {
		@Override
		public RepositoryPlace getPlace(String token) {
			final Map<String, String> map = asMap(token);
			return new RepositoryPlace(map.get("owner"), map.get("name"));
		}

		@Override
		public String getToken(RepositoryPlace place) {
			return "owner=" + place.getOwner() + "&name=" + place.getName();
		}
	}
}
