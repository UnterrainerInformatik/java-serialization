package info.unterrainer.commons.serialization;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonMapper {

	public static final String DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

	ObjectMapper objectMapper;

	public static JsonMapper create() {
		JsonMapper s = new JsonMapper();
		s.objectMapper = new ObjectMapper();

		// DateTime serialization and deserialization.
		s.objectMapper.registerModule(new JavaTimeModule());
		s.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		DateFormat df = new SimpleDateFormat(DATETIME_PATTERN);
		s.objectMapper.setDateFormat(df);

		// Ignore unknown properties when deserializing (field in string but not in
		// object).
		s.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		// Ignore null-values when serializing.
		s.objectMapper.setSerializationInclusion(Include.NON_NULL);

		// Enable Lombok fluent and chained accessors.
		s.objectMapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {

			private static final long serialVersionUID = -5794892113646125154L;

			@Override
			public JsonPOJOBuilder.Value findPOJOBuilderConfig(final AnnotatedClass ac) {
				if (ac.hasAnnotation(JsonPOJOBuilder.class))
					return super.findPOJOBuilderConfig(ac);
				return new JsonPOJOBuilder.Value("build", "");
			}
		});

		return s;
	}

	public TypeFactory getTypeFactory() {
		return objectMapper.getTypeFactory();
	}

	public <T> String toStringFrom(final T sourceObject) {
		try {
			return objectMapper.writeValueAsString(sourceObject);
		} catch (JsonProcessingException e) {
			throw new info.unterrainer.commons.serialization.exceptions.JsonProcessingException(e.getMessage(), e);
		}
	}

	public <T> T fromStringTo(final Class<T> targetClass, final String sourceJson) {
		try {
			return objectMapper.readValue(sourceJson, targetClass);
		} catch (JsonMappingException e) {
			throw new info.unterrainer.commons.serialization.exceptions.JsonMappingException(e.getMessage(), e);
		} catch (JsonProcessingException e) {
			throw new info.unterrainer.commons.serialization.exceptions.JsonProcessingException(e.getMessage(), e);
		}
	}

	public <T> T fromStringTo(final JavaType targetClass, final String sourceJson) {
		try {
			return objectMapper.readValue(sourceJson, targetClass);
		} catch (JsonMappingException e) {
			throw new info.unterrainer.commons.serialization.exceptions.JsonMappingException(e.getMessage(), e);
		} catch (JsonProcessingException e) {
			throw new info.unterrainer.commons.serialization.exceptions.JsonProcessingException(e.getMessage(), e);
		}
	}

	public JsonNode toTreeFrom(final String sourceJson) {
		try {
			return objectMapper.readTree(sourceJson);
		} catch (JsonProcessingException e) {
			throw new info.unterrainer.commons.serialization.exceptions.JsonProcessingException(e.getMessage(), e);
		}
	}

	public <T> JsonNode toTreeFrom(final T sourceObject) {
		return objectMapper.valueToTree(sourceObject);
	}
}
