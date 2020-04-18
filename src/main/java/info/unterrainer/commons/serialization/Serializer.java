package info.unterrainer.commons.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Serializer {

	ObjectMapper objectMapper;

	public static Serializer create() {
		Serializer s = new Serializer();
		s.objectMapper = new ObjectMapper();
		return s;
	}

	/***
	 * @throws JsonProcessingException
	 */
	public <T> String toJsonFrom(final T sourceObject) {
		try {
			return objectMapper.writeValueAsString(sourceObject);
		} catch (JsonProcessingException e) {
			throw new info.unterrainer.commons.serialization.exceptions.JsonProcessingException(e.getMessage(), e);
		}
	}

	/***
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public <T> T fromJsonTo(final Class<T> targetClass, final String sourceJson) {
		try {
			return objectMapper.readValue(sourceJson, targetClass);
		} catch (JsonMappingException e) {
			throw new info.unterrainer.commons.serialization.exceptions.JsonMappingException(e.getMessage(), e);
		} catch (JsonProcessingException e) {
			throw new info.unterrainer.commons.serialization.exceptions.JsonProcessingException(e.getMessage(), e);
		}
	}

	/***
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public <T> T fromTreeTo(final Class<T> targetClass, final TreeNode treeNode) {
		try {
			return objectMapper.treeToValue(treeNode, targetClass);
		} catch (JsonMappingException e) {
			throw new info.unterrainer.commons.serialization.exceptions.JsonMappingException(e.getMessage(), e);
		} catch (JsonProcessingException e) {
			throw new info.unterrainer.commons.serialization.exceptions.JsonProcessingException(e.getMessage(), e);
		}
	}

	/***
	 * @throws JsonProcessingException
	 */
	public JsonNode readTree(final String json) {
		try {
			return objectMapper.readTree(json);
		} catch (JsonProcessingException e) {
			throw new info.unterrainer.commons.serialization.exceptions.JsonProcessingException(e.getMessage(), e);
		}
	}
}
