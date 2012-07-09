package fr.salvadordiaz.gwt.schedule.shared;

import java.util.Date;

public interface Presentation {

	Integer getId();

	Date getFromTime();

	Date getToTime();

	String getTitle();

	String getRoom();

	/**
	 * @return String representation of this presentation
	 */
	String asString();
}