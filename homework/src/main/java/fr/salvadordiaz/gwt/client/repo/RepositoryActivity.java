package fr.salvadordiaz.gwt.client.repo;

import static com.google.common.base.Preconditions.*;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import fr.salvadordiaz.gwt.client.activity.PlaceAwareActivity;
import fr.salvadordiaz.gwt.client.model.Contributors;
import fr.salvadordiaz.gwt.client.model.CommitDetails;
import fr.salvadordiaz.gwt.client.model.GithubJsonpResponse;
import fr.salvadordiaz.gwt.client.model.JsIterable;
import fr.salvadordiaz.gwt.client.rpc.Callback;

public class RepositoryActivity extends AbstractActivity implements PlaceAwareActivity {

	private final RepositoryDisplay display;
	private final JsonpRequestBuilder requestBuilder;

	private RepositoryPlace place;

	@Inject
	public RepositoryActivity(final RepositoryDisplay display, final JsonpRequestBuilder requestBuilder) {
		this.display = display;
		this.requestBuilder = requestBuilder;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		checkNotNull(place, "No information available for repository activity");
		checkNotNull(place.getOwner(), "Can't analyze a repository without the owner id");
		checkNotNull(place.getName(), "Can't analyze a repository without its name");
		fetchRepositoryInformation(place.getOwner(), place.getName());
		display.setName(place.getOwner() + " / " + place.getName());
		panel.setWidget(display);
	}

	private void fetchRepositoryInformation(final String owner, final String reponame) {
		requestBuilder.requestObject(getContributorsQuery(owner, reponame), new Callback<Contributors>() {
			@Override
			public void onSuccess(Contributors result) {
				display.setContributors(result.getContributors().asIterable());
				fetchCommits(owner, reponame);
			}
		});
	}

	private static String getContributorsQuery(String owner, String reponame) {
		return new StringBuilder("http://github.com/api/v2/json/repos/show/")//
				.append(owner).append("/")//
				.append(reponame)//
				.append("/contributors/anon")//		
				.toString();
	}

	private void fetchCommits(String owner, String reponame) {
		requestBuilder.requestObject(getCommitsQuery(owner, reponame), new Callback<GithubJsonpResponse<JsIterable<CommitDetails>>>() {
			@Override
			public void onSuccess(GithubJsonpResponse<JsIterable<CommitDetails>> result) {
				display.setCommitDetails(result.getData().asIterable());
			}
		});
	}

	private static String getCommitsQuery(String owner, String reponame) {
		return new StringBuilder("https://api.github.com/repos/")//
				.append(owner).append("/")//
				.append(reponame)//
				.append("/commits?per_page=100")//
				.toString();
	}

	@Override
	public void setPlace(Place place) {
		this.place = (RepositoryPlace) place;
	}

}
