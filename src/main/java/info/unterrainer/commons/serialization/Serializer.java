package info.unterrainer.commons.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

public class Serializer {

	ObjectMapper objectMapper;

	public static Serializer create() {
		Serializer s = new Serializer();
		s.objectMapper = new ObjectMapper();
		return s;
	}

	@SneakyThrows(JsonProcessingException.class)
	public <T> String toJsonFrom(final T sourceObject) {
		return objectMapper.writeValueAsString(sourceObject);
	}

	@SneakyThrows({ JsonMappingException.class, JsonProcessingException.class })
	public <T> T fromJsonTo(final Class<T> targetClass, final String sourceJson) {
		return objectMapper.readValue(sourceJson, targetClass);
	}
}
