package fr.salvadordiaz.gwt.client.app;

import javax.inject.Singleton;

import com.google.gwt.inject.client.AbstractGinModule;

import fr.salvadordiaz.gwt.client.repo.RepositoryDisplay;
import fr.salvadordiaz.gwt.client.repo.ui.RepositoryView;
import fr.salvadordiaz.gwt.client.search.SearchDisplay;
import fr.salvadordiaz.gwt.client.search.ui.SearchView;

public class HomeworkDesktopModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(SearchDisplay.class).to(SearchView.class).in(Singleton.class);
		bind(RepositoryDisplay.class).to(RepositoryView.class).in(Singleton.class);
	}

}
