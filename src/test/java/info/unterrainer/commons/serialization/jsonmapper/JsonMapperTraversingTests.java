package info.unterrainer.commons.serialization.jsonmapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class JsonMapperTraversingTests {

	public static final JsonMapper mapper = JsonMapper.create();

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
