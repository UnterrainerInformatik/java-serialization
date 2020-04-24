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
public class MediolaDatagramJson {
	private String type;
	@JsonProperty("adr")
	private String adress;
	private String data;
	private String vendor;
	private MediolaDatagramStateJson state;
}
