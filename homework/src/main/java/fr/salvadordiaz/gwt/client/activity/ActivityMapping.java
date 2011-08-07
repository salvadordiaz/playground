package fr.salvadordiaz.gwt.client.activity;

import com.google.gwt.place.shared.Place;

public interface ActivityMapping {

	Class<? extends Place> getPlaceType();

	PlaceAwareActivity getActivity();

}
