package fr.salvadordiaz.gwt.client.repo.ui;

import static com.google.common.base.Strings.*;
import static com.google.common.collect.Iterables.*;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import fr.salvadordiaz.gwt.client.model.CommitDetails;
import fr.salvadordiaz.gwt.client.model.CommitReference;
import fr.salvadordiaz.gwt.client.model.Contributor;

public class CommitWidget extends Composite {

	private static final DateTimeFormat DT_FMT = DateTimeFormat.getFormat(PredefinedFormat.DATE_FULL);

	private static CommitWidgetUiBinder uiBinder = GWT.create(CommitWidgetUiBinder.class);

	interface CommitWidgetUiBinder extends UiBinder<Widget, CommitWidget> {
	}

	@UiField
	protected AnchorElement messageAnchor;
	@UiField
	protected ImageElement gravatar;
	@UiField
	protected AnchorElement nameAnchor;
	@UiField
	protected Element timeElement;

	@UiField
	protected AnchorElement commitIdAnchor, treeIdAnchor, parentIdAnchor;

	public CommitWidget(CommitDetails commitDetails) {
		initWidget(uiBinder.createAndBindUi(this));
		messageAnchor.setInnerText(commitDetails.getCommit().getMessage());
		gravatar.setSrc(commitDetails.getAuthor().getAvatarUrl());
		nameAnchor.setInnerText(commitDetails.getAuthor().getLogin());
		nameAnchor.setHref("https://github.com/" + commitDetails.getAuthor().getLogin());
		timeElement.setInnerText(formatDate(commitDetails.getCommit().getAuthor()));
		timeElement.setTitle(commitDetails.getCommit().getAuthor().getDate());
		
		commitIdAnchor.setInnerText(formatSha(commitDetails.getSha()));
		commitIdAnchor.setHref(commitDetails.getUrl());
		treeIdAnchor.setInnerText(formatSha(commitDetails.getCommit().getTree().getSha()));
		treeIdAnchor.setHref(commitDetails.getCommit().getTree().getUrl());
		final CommitReference firstParent = getFirst(commitDetails.getParents().asIterable(), null);
		parentIdAnchor.setInnerText(formatSha(firstParent.getSha()));
		parentIdAnchor.setHref(firstParent.getUrl());
	}

	private String formatDate(Contributor contributor) {
		final JsDate jsDate = JsDate.create(contributor.getDate());
		@SuppressWarnings("deprecation")
		final Date date = new Date(jsDate.getYear(), jsDate.getMonth(), jsDate.getDate());
		return DT_FMT.format(date);
	}

	private String formatSha(String sha){
		sha = nullToEmpty(sha);
		final int length = sha.length();
		return sha.substring(0, length < 20 ? length : 20);
	}
}
