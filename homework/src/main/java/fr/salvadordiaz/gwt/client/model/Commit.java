package fr.salvadordiaz.gwt.client.model;

import com.google.gwt.core.client.JavaScriptObject;

public class Commit extends JavaScriptObject {

	protected Commit() {
	}

	public final native String getMessage()/*-{
		return this.message;
	}-*/;

	/**
	 * @return objet Contributor où seul la propriété name est disponible
	 */
	public final native Contributor getAuthor()/*-{
		return this.author;
	}-*/;

	public final native CommitReference getTree()/*-{
		return this.tree;
	}-*/;

}
