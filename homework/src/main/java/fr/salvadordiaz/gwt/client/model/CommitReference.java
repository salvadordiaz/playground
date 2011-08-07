package fr.salvadordiaz.gwt.client.model;

import com.google.gwt.core.client.JavaScriptObject;

public class CommitReference extends JavaScriptObject {

	protected CommitReference() {
	}

	public final native String getSha()/*-{
		return this.sha;
	}-*/;

	public final native String getUrl()/*-{
		return this.url;
	}-*/;

}
