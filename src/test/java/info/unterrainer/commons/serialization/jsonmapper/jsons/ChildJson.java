package info.unterrainer.commons.serialization.jsonmapper.jsons;

import com.fasterxml.jackson.annotation.JsonProperty;

import info.unterrainer.commons.serialization.jsons.BasicJson;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class ChildJson extends BasicJson {

	@JsonProperty
	private String string;
}
