package fr.salvadordiaz.gwt.client.activity;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

public interface PlaceAwareActivity extends Activity {

	void setPlace(Place place);
	
}
