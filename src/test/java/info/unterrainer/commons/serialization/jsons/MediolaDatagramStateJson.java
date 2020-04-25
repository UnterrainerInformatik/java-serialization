package info.unterrainer.commons.serialization.jsons;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class MediolaDatagramStateJson {
	@JsonProperty("AI")
	private String aOn;
	@JsonProperty("AO")
	private String aOff;
	@JsonProperty("BI")
	private String bOn;
	@JsonProperty("BO")
	private String bOff;
}
