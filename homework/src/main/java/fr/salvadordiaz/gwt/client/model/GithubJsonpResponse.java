package fr.salvadordiaz.gwt.client.model;

import com.google.gwt.core.client.JavaScriptObject;

public class GithubJsonpResponse<T extends JavaScriptObject> extends JavaScriptObject {

	protected GithubJsonpResponse() {
	}

	public final native T getData()/*-{
		return this.data;
	}-*/;

}
