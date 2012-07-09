package fr.salvadordiaz.gwt.schedule.client.app;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.place.shared.Place;

public abstract class AbstractPlaceAwareActivity extends AbstractActivity {

	public abstract void setPlace(Place place);

}
