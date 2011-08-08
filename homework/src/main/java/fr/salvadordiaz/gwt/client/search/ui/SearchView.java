package fr.salvadordiaz.gwt.client.search.ui;

import static com.google.common.collect.Iterables.*;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import fr.salvadordiaz.gwt.client.model.Repository;
import fr.salvadordiaz.gwt.client.resources.Resources;
import fr.salvadordiaz.gwt.client.search.SearchDisplay;

public class SearchView extends Composite implements SearchDisplay {

	private static final SearchBinder binder = GWT.create(SearchBinder.class);

	interface SearchBinder extends UiBinder<Widget, SearchView> {
	}

	interface ListTemplates extends SafeHtmlTemplates {
		@Template("<div class=\"{2}\"><span class=\"{3}\"></span><a href=\"#repo:owner={0}&name={1}\"><span>{0}</span>&nbsp;/&nbsp;<span class=\"{4}\">{1}</span></a></div>")
		SafeHtml repositoryResult(String owner, String reponame, String repoIconStyle, String repoStyle, String repoNameStyle);
	}

	@UiField
	protected Resources res;
	@UiField
	protected Element resultCount;
	@UiField
	protected TextBox searchBox;
	@UiField
	protected InlineLabel searchButton;
	@UiField
	protected HTML resultsPanel;
	@UiField
	protected ListTemplates templates;

	private final Splitter queryTermSplitter = Splitter.on(" ").omitEmptyStrings().trimResults();
	private final Joiner queryTermJoiner = Joiner.on("+").skipNulls();
	private final Joiner htmlJoiner = Joiner.on("");

	public SearchView() {
		initWidget(binder.createAndBindUi(this));
		res.styles().ensureInjected();
	}

	@UiHandler("searchBox")
	void handleEnter(KeyDownEvent event) {
		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER && !searchBox.getValue().isEmpty()) {
			triggerSearch(searchBox.getValue());
		}
	}

	@UiHandler("searchButton")
	void handleSearchButton(ClickEvent event) {
		if (!searchBox.getValue().isEmpty()) {
			triggerSearch(searchBox.getValue());
		}
	}

	private void triggerSearch(String input) {
		resultsPanel.setHTML("");
		resultsPanel.addStyleName(res.styles().transparent());
		History.newItem(new StringBuilder("search:").append(queryTermJoiner.join(queryTermSplitter.split(input))).toString());
		ValueChangeEvent.fire(this, input);
	}

	private final Function<Repository, String> createResultHtml = new Function<Repository, String>() {
		@Override
		public String apply(Repository repository) {
			return templates.repositoryResult(repository.getOwner(), repository.getName(), res.styles().repo(), res.styles().repoIcon(), res.styles().name())
					.asString();
		}
	};

	@Override
	public void setResults(Iterable<Repository> results) {
		resultsPanel.setHTML(htmlJoiner.join(transform(results, createResultHtml)));
		resultCount.setInnerHTML("( " + resultsPanel.getElement().getChildCount() + " result(s) )");
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				revealResults();
			}
		});
	}

	private void revealResults() {
		resultsPanel.removeStyleName(res.styles().transparent());
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		return addHandler(handler, ValueChangeEvent.getType());
	}
}
