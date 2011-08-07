package fr.salvadordiaz.gwt.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class GithubTemplate extends Composite {

	interface Binder extends UiBinder<Widget, GithubTemplate> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	public GithubTemplate() {
		initWidget(binder.createAndBindUi(this));
	}
}
