package fr.salvadordiaz.gwt.client.repo.ui;

import static com.google.common.base.Objects.*;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.visualizations.AnnotatedTimeLine;
import com.google.gwt.visualization.client.visualizations.corechart.BarChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;

import fr.salvadordiaz.gwt.client.model.CommitDetails;
import fr.salvadordiaz.gwt.client.model.Contributor;
import fr.salvadordiaz.gwt.client.repo.RepositoryDisplay;
import fr.salvadordiaz.gwt.client.resources.Resources;

public class RepositoryView extends Composite implements RepositoryDisplay {

	private static RepositoryViewUiBinder uiBinder = GWT.create(RepositoryViewUiBinder.class);

	interface RepositoryViewUiBinder extends UiBinder<Widget, RepositoryView> {
	}

	@UiField
	protected Resources res;
	@UiField
	protected Element repoName;
	@UiField
	protected FlowPanel pieContainer;
	@UiField
	protected FlowPanel commitsContainer;
	@UiField
	protected FlowPanel barsContainer;
	@UiField
	protected FlowPanel timelineContainer;

	@UiField
	protected Element showCommitsButton;

	public RepositoryView() {
		initWidget(uiBinder.createAndBindUi(this));
		res.styles().ensureInjected();
		com.google.gwt.user.client.Element el = showCommitsButton.cast();
		DOM.sinkEvents(el, Event.ONCLICK);
		DOM.setEventListener(el, new EventListener() {
			@Override
			public void onBrowserEvent(Event event) {
				if (commitsContainer.getStyleName().contains(res.styles().hidden())) {
					commitsContainer.setStyleName(res.styles().visible());
				} else {
					commitsContainer.setStyleName(res.styles().hidden());
				}
			}
		});
	}

	@Override
	public void setName(String name) {
		repoName.setInnerText(name);
	}

	@Override
	public void setContributors(Iterable<Contributor> contributors) {
		pieContainer.clear();
		displayContributorsStats(contributors);
	}

	private void displayContributorsStats(final Iterable<Contributor> contributors) {
		pieContainer.add(new PieChart(getPieData(contributors), getPieOptions()));
	}

	private AbstractDataTable getPieData(Iterable<Contributor> contributors) {
		DataTable data = DataTable.create();
		data.addColumn(ColumnType.STRING, "Collaborator");
		data.addColumn(ColumnType.NUMBER, "Commits");
		int rowIndex = 0;
		for (Contributor contributor : contributors) {
			data.addRow();
			data.setValue(rowIndex, 0, firstNonNull(contributor.getLogin(), contributor.getName()));
			data.setValue(rowIndex, 1, contributor.getContributions());
			rowIndex++;
		}
		return data;
	}

	private Options getPieOptions() {
		Options result = Options.create();
		result.setTitle("Percentage of total reported contributions");
		result.setWidth(pieContainer.getOffsetWidth());
		result.setHeight(pieContainer.getOffsetHeight());
		return result;
	}

	@Override
	public void setCommitDetails(Iterable<CommitDetails> commitDetails) {
		commitsContainer.clear();
		barsContainer.clear();
		timelineContainer.clear();
		displayCommitStats(commitDetails);
		final Iterator<CommitDetails> it = commitDetails.iterator();
		Scheduler.get().scheduleIncremental(new RepeatingCommand() {
			@Override
			public boolean execute() {
				if (!it.hasNext()) {
					return false;
				}
				commitsContainer.add(new CommitWidget(it.next()));
				return it.hasNext();
			}
		});
	}

	private static final Function<CommitDetails, String> getAuthorId = new Function<CommitDetails, String>() {
		@Override
		public String apply(CommitDetails input) {
			return input.getCommit().getAuthor().getName();
		}
	};
	private static final Function<CommitDetails, Date> getDate = new Function<CommitDetails, Date>() {
		@Override
		@SuppressWarnings("deprecation")
		public Date apply(CommitDetails input) {
			final JsDate jsDate = JsDate.create(input.getCommit().getAuthor().getDate());
			return new Date(jsDate.getFullYear() - 1900, jsDate.getMonth(), jsDate.getDate());
		}
	};

	private void displayCommitStats(final Iterable<CommitDetails> details) {
		barsContainer.add(new BarChart(getBarsData(Multimaps.index(details, getAuthorId)), createBarsOptions()));
		timelineContainer.add(new AnnotatedTimeLine(getTimelineData(Multimaps.index(details, getDate)), createTimelineOptions(), timelineContainer
				.getOffsetWidth() + "px", timelineContainer.getOffsetHeight() + "px"));
	}

	private AbstractDataTable getTimelineData(Multimap<Date, CommitDetails> commitsPerDate) {
		DataTable result = DataTable.create();
		result.addColumn(ColumnType.DATE, "Date");
		result.addColumn(ColumnType.NUMBER, "Commits");
		result.addColumn(ColumnType.STRING);
		result.addColumn(ColumnType.STRING);

		int row = 0;
		for (Entry<Date, Collection<CommitDetails>> entry : commitsPerDate.asMap().entrySet()) {
			result.addRow();
			result.setValue(row, 0, entry.getKey());
			result.setValue(row, 1, entry.getValue().size());
			row++;
		}
		return result;
	}

	private AbstractDataTable getBarsData(ImmutableListMultimap<String, CommitDetails> commitsPerAuthor) {
		DataTable result = DataTable.create();
		result.addColumn(ColumnType.STRING, "Name");
		result.addColumn(ColumnType.NUMBER, "Commits");

		int row = 0;
		for (Entry<String, Collection<CommitDetails>> entry : commitsPerAuthor.asMap().entrySet()) {
			result.addRow();
			result.setValue(row, 0, entry.getKey());
			result.setValue(row, 1, entry.getValue().size());
			row++;
		}
		return result;
	}

	private AnnotatedTimeLine.Options createTimelineOptions() {
		AnnotatedTimeLine.Options result = AnnotatedTimeLine.Options.create();
		result.setDisplayAnnotations(true);
		return result;
	}

	private Options createBarsOptions() {
		Options options = Options.create();
		options.setWidth(barsContainer.getOffsetWidth());
		options.setHeight(barsContainer.getOffsetHeight());
		options.setTitle("Commits per author");
		return options;
	}
}
