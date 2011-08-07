package fr.salvadordiaz.gwt.client.repo;

import com.google.gwt.user.client.ui.IsWidget;

import fr.salvadordiaz.gwt.client.model.CommitDetails;
import fr.salvadordiaz.gwt.client.model.Contributor;

public interface RepositoryDisplay extends IsWidget {

	void setName(String name);
	
	void setContributors(Iterable<Contributor> collaborators);

	void setCommitDetails(Iterable<CommitDetails> commitDetails);
}
