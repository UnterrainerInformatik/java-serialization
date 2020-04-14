package info.unterrainer.commons.udpobserver;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DeserializationTests {

	@Test
	public void test() throws JsonMappingException, JsonProcessingException {
		String input = "{\"type\":\"ENOCEAN\",\"adr\":\"fef2b30d\",\"data\":\"eltako_button4\",\"vendor\":\"eltako\",\"state\":{\"BI\":\"released\",\"BO\":\"released\",\"AO\":\"pressed\",\"AI\":\"released\"}}";
		ObjectMapper objectMapper = new ObjectMapper();
		MediolaDatagram d = objectMapper.readValue(input, MediolaDatagram.class);
		System.out.println("done");
	}

	public void test2() throws JsonMappingException, JsonProcessingException {
		String input = "{\"type\":\"ENOCEAN\",\"adr\":\"fef2b30d\",\"data\":\"eltako_button4\",\"vendor\":\"eltako\",\"state\":{\"BI\":\"released\",\"BO\":\"released\",\"AO\":\"released\",\"AI\":\"released\"}}";
		ObjectMapper objectMapper = new ObjectMapper();
		MediolaDatagram d = objectMapper.readValue(input, MediolaDatagram.class);
		System.out.println("done");
	}
}
