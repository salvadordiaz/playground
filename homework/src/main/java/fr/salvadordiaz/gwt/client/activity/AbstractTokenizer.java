package fr.salvadordiaz.gwt.client.activity;

import static com.google.common.collect.Iterables.*;

import java.util.Map;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

public abstract class AbstractTokenizer {
	private static final Splitter tokenSplitter = Splitter.on("&").omitEmptyStrings().trimResults();
	private static final Splitter keyValueSplitter = Splitter.on("=").omitEmptyStrings().trimResults();

	private static final Function<String, Iterable<String>> splitKeyValues = new Function<String, Iterable<String>>() {
		@Override
		public Iterable<String> apply(String input) {
			return keyValueSplitter.split(input);
		}
	};

	protected Map<String, String> asMap(String token) {
		final Iterable<String> keyValues = tokenSplitter.split(token);
		final Iterable<Iterable<String>> splittedKeyValues = transform(keyValues, splitKeyValues);
		final Builder<String, String> builder = ImmutableMap.builder();
		for (Iterable<String> keyValue : splittedKeyValues) {
			builder.put(get(keyValue, 0), get(keyValue, 1));
		}
		return builder.build();
	}
}
