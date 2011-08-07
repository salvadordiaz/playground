package fr.salvadordiaz.gwt.client.model;

import com.google.gwt.core.client.JavaScriptObject;

public class Repository extends JavaScriptObject {

	protected Repository() {
	}

	public final native String getOwner()/*-{
		return this.owner;
	}-*/;

	public final native String getDescription()/*-{
		return this.description;
	}-*/;

	public final native String getName()/*-{
		return this.name;
	}-*/;

}
