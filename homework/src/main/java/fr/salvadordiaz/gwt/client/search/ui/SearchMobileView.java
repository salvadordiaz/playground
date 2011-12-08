package fr.salvadordiaz.gwt.client.search.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import fr.salvadordiaz.gwt.client.model.Repository;
import fr.salvadordiaz.gwt.client.search.SearchDisplay;

public class SearchMobileView extends Composite implements SearchDisplay {

	private static SearchMobileViewUiBinder uiBinder = GWT.create(SearchMobileViewUiBinder.class);

	interface SearchMobileViewUiBinder extends UiBinder<Widget, SearchMobileView> {
	}

	@UiField
	Anchor label;
	
	public SearchMobileView() {
		initWidget(uiBinder.createAndBindUi(this));
		label.setHref("#repo:owner=test&name=test");
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		return addHandler(handler, ValueChangeEvent.getType());
	}

	@Override
	public void setResults(Iterable<Repository> results) {
		Window.alert("You set the results");
	}

}
