package fr.salvadordiaz.gwt.client.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.google.common.collect.UnmodifiableIterator;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class JsIterable<T extends JavaScriptObject> extends JsArray<T> {

	protected JsIterable() {
	}

	public final Iterable<T> asIterable() {
		return new Iterable<T>() {
			@Override
			public Iterator<T> iterator() {
				return new UnmodifiableIterator<T>() {
					private int position = 0;

					@Override
					public boolean hasNext() {
						return position < length();
					}

					@Override
					public T next() {
						if (!hasNext()) {
							throw new NoSuchElementException();
						}
						return get(position++);
					}
				};
			}
		};
	}
}
