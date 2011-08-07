package fr.salvadordiaz.gwt.client.app.homework;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

import fr.salvadordiaz.gwt.client.repo.RepositoryPlace;
import fr.salvadordiaz.gwt.client.search.SearchPlace;

@WithTokenizers({ SearchPlace.Tokenizer.class, RepositoryPlace.Tokenizer.class })
public interface HomeworkHistoryMapper extends PlaceHistoryMapper {

}
