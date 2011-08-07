package fr.salvadordiaz.gwt.client.search;

import static com.google.common.base.Strings.*;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import fr.salvadordiaz.gwt.client.activity.PlaceAwareActivity;
import fr.salvadordiaz.gwt.client.model.SearchResult;
import fr.salvadordiaz.gwt.client.rpc.Callback;

public class SearchActivity extends AbstractActivity implements PlaceAwareActivity {

	private final Splitter queryTermSplitter = Splitter.on(" ").omitEmptyStrings().trimResults();
	private final Joiner queryTermJoiner = Joiner.on("+").skipNulls();

	private final SearchDisplay display;
	private final JsonpRequestBuilder requestBuilder;

	private SearchPlace place;

	@Inject
	public SearchActivity(final SearchDisplay display, final JsonpRequestBuilder requestBuilder) {
		this.display = display;
		this.requestBuilder = requestBuilder;
		bind();
	}

	private void bind() {
		display.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				doSearch(queryTermJoiner.join(queryTermSplitter.split(event.getValue())));
			}
		});
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(display.asWidget());
		if (!nullToEmpty(place.getQuery()).isEmpty()) {
			doSearch(place.getQuery());
		}
	}

	private void doSearch(String query) {
		requestBuilder.requestObject(getUrl(query), new Callback<SearchResult>() {
			@Override
			public void onSuccess(SearchResult result) {
				display.setResults(result.getRepositories().asIterable());
			}
		});
	}

	private String getUrl(String query) {
		return new StringBuilder("http://github.com/api/v2/json/repos/search/")//
				.append(query)//
				.toString();
	}

	@Override
	public void setPlace(Place place) {
		this.place = (SearchPlace) place;
	}
}
