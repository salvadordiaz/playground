package fr.salvadordiaz.gwt.client.model;

import com.google.gwt.core.client.JavaScriptObject;


public class SearchResult extends JavaScriptObject {

	protected SearchResult() {
	}

	public final native JsIterable<Repository> getRepositories()/*-{
		return this.repositories;
	}-*/;

}
