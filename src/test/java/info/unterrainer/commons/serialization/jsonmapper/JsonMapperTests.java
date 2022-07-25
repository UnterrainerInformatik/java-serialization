package info.unterrainer.commons.serialization.jsonmapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import info.unterrainer.commons.serialization.jsonmapper.jsons.ChildJson;
import info.unterrainer.commons.serialization.jsonmapper.jsons.MediolaDatagramJson;
import info.unterrainer.commons.serialization.jsonmapper.jsons.SimpleJson;

public class JsonMapperTests {

	public static final JsonMapper mapper = JsonMapper.create();

	public static final LocalDateTime localDateTime = LocalDateTime.of(2020, 01, 31, 14, 34, 26, 123456);
	public static final String localDateTimeString = "2020-01-31T14:34:26.000123456";

	@Test
	public void deserializingExtendedClassesWithLombokBuildersWorks() {
		String s = "{\"id\":2,\"createdOn\":\"2020-01-31T14:34:26.000123456\",\"editedOn\":\"2020-01-31T14:34:26.000123456\",\"string\":\"test\"}";
		ChildJson result = mapper.fromStringTo(ChildJson.class, s);
		assertThat(result.getId()).isEqualByComparingTo(2L);
		assertThat(result.getCreatedOn()).isEqualTo(localDateTime);
		assertThat(result.getEditedOn()).isEqualTo(localDateTime);
		assertThat(result.getString()).isEqualTo("test");
	}

	@Test
	public void serializingExtendedClassesWithLombokBuildersWorks() {
		ChildJson j = ChildJson.builder()
				.id(2L)
				.createdOn(localDateTime)
				.editedOn(localDateTime)
				.string("test")
				.build();
		String result = mapper.toStringFrom(j);
		assertThat(result).contains(localDateTimeString);
		assertThat(result).contains("test");
		assertThat(result).contains("2");
	}

	@Test
	public void nullValuesAreNotSerialized() {
		String result = mapper.toStringFrom(SimpleJson.builder().build());
		assertThat(result).isEqualTo("{}");
	}

	@Test
	public void unknownPropertiesAreIgnoredWhenDeserializing() {
		String input = "{\"string\":\"gluppy\",\"blah\":\"platy\"}";
		SimpleJson json = mapper.fromStringTo(SimpleJson.class, input);
		assertThat(json.getString()).isEqualTo("gluppy");
	}

	@Test
	public void deserializingStackedObjectsWorks() throws JsonMappingException, JsonProcessingException {
		String input = "{\"type\":\"ENOCEAN\",\"adr\":\"fef2b30d\",\"data\":\"eltako_button4\",\"vendor\":\"eltako\",\"state\":{\"BI\":\"released\",\"BO\":\"released\",\"AO\":\"pressed\",\"AI\":\"released\"}}";
		MediolaDatagramJson d = mapper.fromStringTo(MediolaDatagramJson.class, input);
		assertThat(d.getAdress()).isEqualTo("fef2b30d");
		assertThat(d.getType()).isEqualTo("ENOCEAN");
		assertThat(d.getData()).isEqualTo("eltako_button4");
		assertThat(d.getState().getBOn()).isEqualTo("released");
		assertThat(d.getState().getAOff()).isEqualTo("pressed");
	}

	@Test
	public void traversingFirstLevelFieldsWorks() throws JsonMappingException, JsonProcessingException {
		String s = "{\"string\":\"test\"}";
		assertThat(mapper.traverse(s, "string")).isEqualTo("test");
	}

	@Test
	public void traversingSecondLevelFieldsWorks() throws JsonMappingException, JsonProcessingException {
		String s = "{\"parent\": {\"name\":\"Gerald\"}}";
		assertThat(mapper.traverse(s, "parent.name")).isEqualTo("Gerald");
	}

	@Test
	public void traversingArrayWorks() throws JsonMappingException, JsonProcessingException {
		String s = "{\"array\": [{\"name\":\"Gerald\"},{\"name\":\"Günter\"}]}";
		assertThat(mapper.traverse(s, "array.#0.name")).isEqualTo("Gerald");
		assertThat(mapper.traverse(s, "array.#1.name")).isEqualTo("Günter");
	}
}
