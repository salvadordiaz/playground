package fr.salvadordiaz.gwt.client.repo.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import fr.salvadordiaz.gwt.client.model.CommitDetails;
import fr.salvadordiaz.gwt.client.model.Contributor;
import fr.salvadordiaz.gwt.client.repo.RepositoryDisplay;

public class RepositoryMobileView extends Composite implements RepositoryDisplay {

	private static RepositoryMobileViewUiBinder uiBinder = GWT.create(RepositoryMobileViewUiBinder.class);

	interface RepositoryMobileViewUiBinder extends UiBinder<Widget, RepositoryMobileView> {
	}

	public RepositoryMobileView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setName(String name) {
	}

	@Override
	public void setContributors(Iterable<Contributor> collaborators) {
	}

	@Override
	public void setCommitDetails(Iterable<CommitDetails> commitDetails) {
	}

}
