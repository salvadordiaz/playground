package fr.salvadordiaz.gwt.client.search;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.user.client.ui.IsWidget;

import fr.salvadordiaz.gwt.client.model.Repository;

public interface SearchDisplay extends IsWidget, HasValueChangeHandlers<String> {

	void setResults(Iterable<Repository> results);

}
