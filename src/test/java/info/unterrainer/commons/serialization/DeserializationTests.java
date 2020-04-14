package info.unterrainer.commons.serialization;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import info.unterrainer.commons.serialization.jsons.MediolaDatagram;

public class DeserializationTests {

	@Test
	public void deserializingStackedObjectsWorks() throws JsonMappingException, JsonProcessingException {
		String input = "{\"type\":\"ENOCEAN\",\"adr\":\"fef2b30d\",\"data\":\"eltako_button4\",\"vendor\":\"eltako\",\"state\":{\"BI\":\"released\",\"BO\":\"released\",\"AO\":\"pressed\",\"AI\":\"released\"}}";
		ObjectMapper objectMapper = new ObjectMapper();
		MediolaDatagram d = objectMapper.readValue(input, MediolaDatagram.class);
		assertThat(d.getAdress()).isEqualTo("fef2b30d");
		assertThat(d.getType()).isEqualTo("ENOCEAN");
		assertThat(d.getData()).isEqualTo("eltako_button4");
		assertThat(d.getState().getBOn()).isEqualTo("released");
		assertThat(d.getState().getAOff()).isEqualTo("pressed");
	}
}
