package com.toolstore.config;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

import org.springframework.data.auditing.DateTimeProvider;

public class DateTimeProviderImpl implements DateTimeProvider {

	@Override
	public Optional<TemporalAccessor> getNow() {
		ZonedDateTime utc = ZonedDateTime
			    .now() // current date/time
			    .withZoneSameInstant(ZoneOffset.UTC); // convert to UTC
		return Optional.of(utc);
	}

}
