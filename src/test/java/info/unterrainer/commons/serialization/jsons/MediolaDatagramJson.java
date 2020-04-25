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
public class MediolaDatagramJson {
	private String type;
	@JsonProperty("adr")
	private String adress;
	private String data;
	private String vendor;
	private MediolaDatagramStateJson state;
}
