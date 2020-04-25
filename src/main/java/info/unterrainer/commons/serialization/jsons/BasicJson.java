package info.unterrainer.commons.serialization.jsons;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Accessors(fluent = true, chain = true)
@JsonDeserialize(builder = BasicJson.BasicJsonBuilderImpl.class)
public class BasicJson {

	@JsonProperty
	private Long id;
	@JsonProperty
	private LocalDateTime createdOn;
	@JsonProperty
	private LocalDateTime editedOn;
}
