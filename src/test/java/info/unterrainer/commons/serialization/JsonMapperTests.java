package info.unterrainer.commons.serialization;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import info.unterrainer.commons.serialization.jsons.MediolaDatagramJson;
import info.unterrainer.commons.serialization.jsons.SimpleJson;

public class JsonMapperTests {

	private static final JsonMapper mapper = JsonMapper.create();

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
}
