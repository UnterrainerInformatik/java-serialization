package info.unterrainer.commons.serialization.jsonmapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonMapperManipulatingTests {

	public static final JsonMapper mapper = JsonMapper.create();

	@Test
	public void manipulatingTreeAtRootLevelWorks() throws JsonMappingException, JsonProcessingException {
		String s = "{\"array\": [{\"name\":\"Gerald\"},{\"name\":\"Günter\"}]}";
		JsonNode r = mapper.toTreeFrom(s);
		((ObjectNode) r).put("test", "testval");
		String str = mapper.toStringFrom(r);

		assertThat(str).isEqualTo("{\"array\":[{\"name\":\"Gerald\"},{\"name\":\"Günter\"}],\"test\":\"testval\"}");
	}

	@Test
	public void manipulatingTreeAtOtherLevelWorks() throws JsonMappingException, JsonProcessingException {
		String s = "{\"array\": [{\"name\":\"Gerald\"},{\"name\":\"Günter\"}],\"sub\":{\"inner\":5}}";
		JsonNode r = mapper.toTreeFrom(s);
		((ObjectNode) r.at("/sub")).put("test", "testval");
		String str = mapper.toStringFrom(r);

		assertThat(str).isEqualTo(
				"{\"array\":[{\"name\":\"Gerald\"},{\"name\":\"Günter\"}],\"sub\":{\"inner\":5,\"test\":\"testval\"}}");
	}

	@Test
	public void readingTreeAtNonExistingNodeReturnsNullNode() throws JsonMappingException, JsonProcessingException {
		String s = "{\"array\": [{\"name\":\"Gerald\"},{\"name\":\"Günter\"}]}";
		JsonNode r = mapper.toTreeFrom(s);
		JsonNode n = r.at("/test");

		assertThat(n.isMissingNode()).isTrue();
	}
}
