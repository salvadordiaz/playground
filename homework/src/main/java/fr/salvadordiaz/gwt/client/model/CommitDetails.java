package fr.salvadordiaz.gwt.client.model;


public class CommitDetails extends CommitReference {

	protected CommitDetails() {
	}

	public final native Collaborator getCommitter()/*-{
		return this.committer;
	}-*/;

	public final native Collaborator getAuthor()/*-{
		return this.author;
	}-*/;

	public final native Commit getCommit()/*-{
		return this.commit;
	}-*/;

	public final native JsIterable<CommitReference> getParents()/*-{
		return this.parents;
	}-*/;
}
