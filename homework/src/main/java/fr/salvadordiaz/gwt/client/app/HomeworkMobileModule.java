package fr.salvadordiaz.gwt.client.app;

import javax.inject.Singleton;

import com.google.gwt.inject.client.AbstractGinModule;

import fr.salvadordiaz.gwt.client.repo.RepositoryDisplay;
import fr.salvadordiaz.gwt.client.repo.ui.RepositoryMobileView;
import fr.salvadordiaz.gwt.client.search.SearchDisplay;
import fr.salvadordiaz.gwt.client.search.ui.SearchMobileView;

public class HomeworkMobileModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(SearchDisplay.class).to(SearchMobileView.class).in(Singleton.class);
		bind(RepositoryDisplay.class).to(RepositoryMobileView.class).in(Singleton.class);
	}

}
