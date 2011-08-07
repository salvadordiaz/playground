package fr.salvadordiaz.gwt.client.model;

import com.google.gwt.core.client.JavaScriptObject;

public class Contributors extends JavaScriptObject {

	protected Contributors() {
	}

	public final native JsIterable<Contributor> getContributors()/*-{
		return this.contributors;
	}-*/;

}
