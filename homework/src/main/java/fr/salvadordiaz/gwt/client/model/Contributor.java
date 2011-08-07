package fr.salvadordiaz.gwt.client.model;

import com.google.gwt.core.client.JavaScriptObject;

public class Contributor extends JavaScriptObject {

	protected Contributor() {
	}

	public final native int getContributions()/*-{
		return this.contributions;
	}-*/;

	/** At least one of these 2 identifiers will be non-null */

	public final native String getName()/*-{
		return this.name;
	}-*/;

	public final native String getLogin()/*-{
		return this.login;
	}-*/;

	public final native String getDate()/*-{
		return this.date;
	}-*/;

}
