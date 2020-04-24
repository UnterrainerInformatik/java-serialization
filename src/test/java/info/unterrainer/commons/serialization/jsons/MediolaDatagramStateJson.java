package info.unterrainer.commons.serialization.jsons;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
