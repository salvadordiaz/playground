package fr.salvadordiaz.gwt.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface Resources extends ClientBundle {

	public interface Styles extends CssResource {
		String container();

		String header();

		String withAction();
		
		String searchBar();

		String button();

		String repoList();

		String repo();
		
		String transparent();

		String repoIcon();

		String name();
		
		String hidden();
		
		String visible();
		
		String commit();
		
		String human();
		
		String message();
		
		String actor();
		
		String gravatar();
		
		String date();
		
		String machine();
	}

	@Source("GithubTemplate.css")
	Styles styles();

	@Source("arrow-80.png")
	ImageResource arrowIcon();

	@Source("public.png")
	ImageResource publicIcon();
}
