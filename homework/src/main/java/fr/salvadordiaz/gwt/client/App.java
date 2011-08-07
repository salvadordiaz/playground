package fr.salvadordiaz.gwt.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.AnnotatedTimeLine;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;

import fr.salvadordiaz.gwt.client.app.ApplicationGinjector;

public class App implements EntryPoint {

	private static final Logger log = Logger.getLogger("DemoNavigation");

	public void onModuleLoad() {
		GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			public void onUncaughtException(Throwable e) {
				log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			}
		});
		//make sure visualization api is loaded before displaying anything
		VisualizationUtils.loadVisualizationApi(new Runnable() {
			@Override
			public void run() {
				startApp();
			}
		}, CoreChart.PACKAGE, AnnotatedTimeLine.PACKAGE);
	}

	private void startApp() {
		ApplicationGinjector injector = GWT.create(ApplicationGinjector.class);
		RootPanel.get().add(injector.display());
		injector.getHistoryHandler().handleCurrentHistory();
	}

}
