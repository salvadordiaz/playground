package fr.salvadordiaz.gwt.schedule.client.app;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

import fr.salvadordiaz.gwt.schedule.client.day.DayPlace;
import fr.salvadordiaz.gwt.schedule.client.presentation.PresentationPlace;
import fr.salvadordiaz.gwt.schedule.client.speaker.SpeakerPlace;

@WithTokenizers({ DayPlace.Tokenizer.class, PresentationPlace.Tokenizer.class, SpeakerPlace.Tokenizer.class })
public interface SchedulePlaceHistoryMapper extends PlaceHistoryMapper {
}
