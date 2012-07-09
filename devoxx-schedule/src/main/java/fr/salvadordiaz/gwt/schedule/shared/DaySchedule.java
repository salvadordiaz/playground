package fr.salvadordiaz.gwt.schedule.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class DaySchedule implements Serializable {
	private static final long serialVersionUID = -4847033379683913480L;

	private Date day;
	private ArrayList<Presentation> presentations;

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public ArrayList<Presentation> getPresentations() {
		return presentations;
	}

	public void setPresentations(ArrayList<Presentation> presentations) {
		this.presentations = presentations;
	}

}
