package info.unterrainer.commons.serialization.jsons;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(fluent = true, chain = true)
@JsonDeserialize(builder = ChildJson.ChildJsonBuilderImpl.class)
public class ChildJson extends BasicJson {

	@JsonProperty
	private String string;
}
