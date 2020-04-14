package info.unterrainer.commons.serialization.jsons;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MediolaDatagramState {
	@JsonProperty("AI")
	private String aOn;
	@JsonProperty("AO")
	private String aOff;
	@JsonProperty("BI")
	private String bOn;
	@JsonProperty("BO")
	private String bOff;
}
