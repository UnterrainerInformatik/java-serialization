package info.unterrainer.commons.serialization;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

import info.unterrainer.commons.serialization.jsons.DateTimeJson;

public class DateTimeTests {

	public static final JsonMapper mapper = JsonMapper.create();

	public static final LocalDate localDate = LocalDate.of(2020, 01, 31);
	public static final String localDateString = "2020-01-31";

	public static final LocalDateTime localDateTime = LocalDateTime.of(2020, 01, 31, 14, 34, 26, 123456);
	public static final String localDateTimeString = "2020-01-31T14:34:26.000123456";

	public static final ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.of("+0100"));
	public static final String zonedDateTimeString = "2020-01-31T14:34:26.000123456+01:00";

	@Test
	public void serializingLocalDateTimeWorks() {
		DateTimeJson j = DateTimeJson.builder().localDateTime(localDateTime).build();
		String r = mapper.toStringFrom(j);
		assertThat(r).contains(localDateTimeString);
	}

	@Test
	public void deserializingLocalDateTimeWorks() {
		String s = "{\"localDateTime\":\"" + localDateTimeString + "\"}";
		DateTimeJson j = mapper.fromStringTo(DateTimeJson.class, s);
		assertThat(j.getLocalDateTime()).isEqualTo(localDateTime);
	}

	@Test
	public void serializingZonedDateTimeWorks() {
		DateTimeJson j = DateTimeJson.builder().zonedDateTime(zonedDateTime).build();
		String r = mapper.toStringFrom(j);
		assertThat(r).contains(zonedDateTimeString);
	}

	@Test
	public void deserializingZonedDateTimeWorks() {
		String s = "{\"zonedDateTime\":\"" + zonedDateTimeString + "\"}";
		DateTimeJson j = mapper.fromStringTo(DateTimeJson.class, s);
		assertThat(j.getZonedDateTime()).isEqualTo(zonedDateTime);
	}

	@Test
	public void serializingLocalDateWorks() {
		DateTimeJson j = DateTimeJson.builder().localDate(localDate).build();
		String r = mapper.toStringFrom(j);
		assertThat(r).contains(localDateString);
	}

	@Test
	public void deserializingLocalDateWorks() {
		String s = "{\"localDate\":\"" + localDateString + "\"}";
		DateTimeJson j = mapper.fromStringTo(DateTimeJson.class, s);
		assertThat(j.getLocalDate()).isEqualTo(localDate);
	}
}
