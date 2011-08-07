package fr.salvadordiaz.gwt.client.model;

import com.google.gwt.core.client.JavaScriptObject;

public class Collaborator extends JavaScriptObject {

	protected Collaborator() {
	}

	public final native String getUrl()/*-{
		return this.url;
	}-*/;

	public final native String getLogin()/*-{
		return this.login;
	}-*/;

	public final native String getAvatarUrl()/*-{
		return this.avatar_url;
	}-*/;

	public final native String getId()/*-{
		return this.id;
	}-*/;

}
